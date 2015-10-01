//
//  KappenballGame.m
//  Kappenball
//
//  Created by Matthew Woodruff on 08/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "KappenballGame.h"

@interface KappenballGame()

@property(nonatomic, strong) NSMutableArray *mutableAttempts;

@end

@implementation KappenballGame

@synthesize score, averageEnergy, active, saved, mutableAttempts, playerName;

-(KappenballGame *)init
{
    self = [super init];
    if(self) mutableAttempts = [[NSMutableArray alloc] init];
    return self;
}

-(KappenballGame *)initWithPlayerName:(NSString *)n
{
    self = [super init];
    if(self) {
        playerName = n;
        mutableAttempts = [[NSMutableArray alloc] init];
    }
    return self;
}

-(void)addAttempt:(KappenballAttempt *)attempt
{
    [mutableAttempts addObject:attempt];
}

-(void)clearAttempts
{
    [mutableAttempts removeAllObjects];
}

// Gets the score of the game based on the successes of it's attempts
-(int)score
{
    int s = 0;
    for(KappenballAttempt *ka in mutableAttempts) if(ka.success) s++;
    return s;
}

// Gets the average energy of the game based on the attempts
//-(int)averageEnergy
//{
//    int ae = 0;
//    for(KappenballAttempt *ka in mutableAttempts) ae += ka.energy;
//    return ae > 0 ? ae/mutableAttempts.count : 0;
//}

// Gets the average energy of the game based on the successful attempts
-(int)averageEnergy
{
    int ae = 0;
    for(KappenballAttempt *ka in mutableAttempts) if(ka.success) ae += ka.energy;
    return ae > 0 ? ae/self.score : 0;
}


-(NSArray *)attempts
{
    return [mutableAttempts copy];
}

-(NSString *)description
{
    return [NSString stringWithFormat:@"Playername: %@ - Attempts: %@", playerName, mutableAttempts];
}

@end
