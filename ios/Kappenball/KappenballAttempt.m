//
//  KappenballAttempt.m
//  Kappenball
//
//  Created by Matthew Woodruff on 08/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "KappenballAttempt.h"

@implementation KappenballAttempt

@synthesize energy, success, playerName;

-(KappenballAttempt *)initWithPlayerName:(NSString *)pn
{
    self = [super init];
    if(self) playerName = pn;
    return self;
}

-(KappenballAttempt *)initWithPlayerName:(NSString *)pn Energy:(int)e Success:(BOOL)s
{
    self = [super init];
    if(self){
        playerName = pn;
        energy = e;
        success = s;
    }
    return self;
}

-(NSString *)description
{
    return [NSString stringWithFormat:@"Energy: %d - Success: %d", energy, success];
}

@end
