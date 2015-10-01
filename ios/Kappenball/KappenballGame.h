//
//  KappenballGame.h
//  Kappenball
//
//  Created by Matthew Woodruff on 08/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "KappenballAttempt.h"

@interface KappenballGame : NSObject

@property(nonatomic, strong) NSString *playerName;
@property(nonatomic, readonly, strong) NSArray *attempts;
@property(nonatomic, readonly, assign) int score;
@property(nonatomic, readonly, assign) int averageEnergy;
@property(nonatomic, assign) BOOL active;
@property(nonatomic, assign) BOOL saved;

-(KappenballGame *)init;
-(KappenballGame *)initWithPlayerName:(NSString *)name;

-(void)addAttempt:(KappenballAttempt *)attempt;
-(void)clearAttempts;

@end
