//
//  KappenballGamesHistory.h
//  Kappenball
//
//  Created by Matthew Woodruff on 10/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <sqlite3.h>
#import "KappenballGame.h"

@interface KappenballGamesHistory : NSObject

@property (nonatomic, strong) NSString* databasePath;
@property (nonatomic, strong) NSMutableArray *games;
@property (nonatomic, strong, readonly) NSArray *attemptsRankedByEnergy;
@property (nonatomic, strong, readonly) NSArray *gamesRankedByAverageEnergy;

-(id)initWithDatabasePath:(NSString*)path;
-(void)addGame:(KappenballGame *)game;

@end
