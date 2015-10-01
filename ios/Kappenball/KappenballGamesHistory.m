//
//  KappenballGamesHistory.m
//  Kappenball
//
//  Created by Matthew Woodruff on 10/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "KappenballGamesHistory.h"
#import "KappenballGame.h"

@implementation KappenballGamesHistory

@synthesize databasePath, games;
@synthesize attemptsRankedByEnergy = _attemptsRankedByEnergy;
@synthesize gamesRankedByAverageEnergy = _gamesRankedByAverageEnergy;

// Initialise with a database path
-(id)initWithDatabasePath:(NSString *)path {
    self = [super init];
    if (self) {
        databasePath = path;
        [self readFromDatabase];
    }
    return self;
}

// Reads data from the database
-(void)readFromDatabase {
    
    // Set the games variable to an empty array when a fresh database read happens
    games = [[NSMutableArray alloc]init];
    
    sqlite3 *database;
    
    // Open the database
    if (sqlite3_open_v2([databasePath UTF8String],&database, SQLITE_OPEN_READWRITE, NULL)==SQLITE_OK){
        
        // Statement for selecting the attempts ordered by the game ID
        const char *getAllGamesStatement = "SELECT a.attemptGame, g.playerName, a.energy, a.success FROM attempts a INNER JOIN games g ON a.attemptGame = g.id ORDER BY a.attemptGame";
        sqlite3_stmt *compiledStatement;
        
        if (sqlite3_prepare_v2(database, getAllGamesStatement, -1, &compiledStatement, NULL) == SQLITE_OK) {
            
            KappenballGame *lo;
            int gameID = -1;
            
            // Read each attempt from the database
            while (sqlite3_step(compiledStatement) == SQLITE_ROW) {
                
                NSString *playerName = [NSString stringWithUTF8String:(char*) sqlite3_column_text(compiledStatement,1)];
                
                int tempID = sqlite3_column_int(compiledStatement, 0);
                
                // if the game id has changed then create a new game and add attempts
                // with the same gameID to that game
                if(gameID!=tempID){
                    gameID = tempID;
                    [games addObject:[[KappenballGame alloc] initWithPlayerName:playerName]];
                    lo = [games lastObject];
                }
                
                // Get the attempt information
                int energy = sqlite3_column_int(compiledStatement, 2);
                BOOL success = (BOOL)sqlite3_column_int(compiledStatement, 3);
                
                // Add attempt to game
                [lo addAttempt:[[KappenballAttempt alloc] initWithPlayerName:playerName Energy:energy Success:success]];
            }
            sqlite3_finalize(compiledStatement);
        }
        sqlite3_close(database);
    }
}

// Adds a game to the database
-(void)addGame:(KappenballGame *)game {
    
    NSLog(@"Saving game: %@", game);
    long gameID = 0;
    sqlite3 *database;
    
    // Open the databse
    if (sqlite3_open_v2([self.databasePath UTF8String], &database, SQLITE_OPEN_READWRITE, NULL)==SQLITE_OK) {
        
        // ADD GAME statement
        const char *addGameSqlStatement = "INSERT INTO games (playerName) VALUES (?)";
        sqlite3_stmt *addGameCompiledStatement;
        
        int addGameResult = sqlite3_prepare_v2(database, addGameSqlStatement, -1, &addGameCompiledStatement, NULL);
        if (addGameResult==SQLITE_OK) {
            
            sqlite3_bind_text(addGameCompiledStatement, 1, [game.playerName UTF8String], -1, SQLITE_TRANSIENT);
            sqlite3_step(addGameCompiledStatement);
            
            // find the primary key for the game we just inserted
            gameID = sqlite3_last_insert_rowid(database);
            [games addObject:game];
        } else {
            NSLog(@"could not prepare statement: prepare-error #%i: %s", addGameResult, sqlite3_errmsg(database));
        }
        // release the compiled statement
        sqlite3_finalize(addGameCompiledStatement);
        
        // ADD ATTEMPTS to the game
        for(KappenballAttempt *ka in game.attempts) {
            ka.playerName = game.playerName;
            const char *addAttemptSqlStatement = "INSERT INTO attempts (attemptGame, energy, success) VALUES (?, ?, ?)";
            sqlite3_stmt *addAttemptCompiledStatement;
            int addAttemptResult = sqlite3_prepare_v2(database, addAttemptSqlStatement, -1, &addAttemptCompiledStatement, NULL);
            if(addAttemptResult==SQLITE_OK) {
                sqlite3_bind_int64(addAttemptCompiledStatement, 1, gameID);
                sqlite3_bind_int(addAttemptCompiledStatement, 2, ka.energy);
                sqlite3_bind_int(addAttemptCompiledStatement, 3, ka.success);
                sqlite3_step(addAttemptCompiledStatement);
            } else {
                NSLog(@"could not prepare statement: prepare-error #%i: %s", addAttemptResult, sqlite3_errmsg(database));
            }
            // release the compiled statement
            sqlite3_finalize(addAttemptCompiledStatement);
        }
        
        // Set these variables to nil so when the assocoiated properties are
        // called then ranked arrays are returned with the new data.
        _attemptsRankedByEnergy = nil;
        _gamesRankedByAverageEnergy = nil;
        
        // close the database
        sqlite3_close(database);
        
    } else {
        NSLog(@"Could not open database");
    }
}

// Returns the attempts ranked by energy
-(NSArray *)attemptsRankedByEnergy
{
    // Lazily instantiate the array
    if(!_attemptsRankedByEnergy) {
        
        // Filter by only successful attempts
        NSPredicate *filterBySuccess = [NSPredicate predicateWithBlock:^BOOL(id obj, NSDictionary *bindings) {
            KappenballAttempt *ka = (KappenballAttempt*)obj;
            return ka.success;
        }];
        
        // Apply the filter
        NSMutableArray *kas = [[NSMutableArray alloc] init];
        for(KappenballGame *kg in games) {
            [kas addObjectsFromArray:[kg.attempts filteredArrayUsingPredicate:filterBySuccess]];
        }
        
        // Sort by energy level
        NSComparator sortByEnergy = ^(id obj1, id obj2) {
            
            KappenballAttempt *ka1 = (KappenballAttempt*)obj1;
            KappenballAttempt *ka2 = (KappenballAttempt*)obj2;
            
            if (ka1.energy < ka2.energy)
                return (NSComparisonResult)NSOrderedAscending;
            // this will ensure the latest times are at the top
            else return (NSComparisonResult)NSOrderedDescending;
        };
        
        // Apply the sorting
        _attemptsRankedByEnergy = [kas sortedArrayUsingComparator:sortByEnergy];
    }
    return _attemptsRankedByEnergy;
}

// Returns the games ranked by average energy
-(NSArray *)gamesRankedByAverageEnergy
{
    // Lazily instantiate the arry
    if(!_gamesRankedByAverageEnergy) {
        
        // Filter by games that have an 80% success rate
        NSPredicate *filterByAttemptAmount = [NSPredicate predicateWithBlock:^BOOL(id obj, NSDictionary *bindings) {
            KappenballGame *kg = (KappenballGame*)obj;
            int count = 0;
            for(KappenballAttempt *ka in kg.attempts) if(ka.success) count++;
//            return count? count/kg.attempts.count >= 0.8: NO;
            
            return count;
        }];
        
        // Sort by average energy (most recent to top if games with same value)
        NSComparator sortByAverageEnergy = ^(id obj1, id obj2) {
            
            KappenballGame *kg1 = (KappenballGame*)obj1;
            KappenballGame *kg2 = (KappenballGame*)obj2;
            
            if (kg1.averageEnergy < kg2.averageEnergy)
                return (NSComparisonResult)NSOrderedAscending;
            else return (NSComparisonResult)NSOrderedDescending;
        };
        
        // Apply the filter first then sort
        _gamesRankedByAverageEnergy = [[games filteredArrayUsingPredicate:filterByAttemptAmount] sortedArrayUsingComparator:sortByAverageEnergy];

    }
    return _gamesRankedByAverageEnergy;
}

-(NSString *)description
{
    return [games description];
}

@end
