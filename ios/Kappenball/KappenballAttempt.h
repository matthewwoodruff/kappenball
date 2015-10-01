//
//  KappenballAttempt.h
//  Kappenball
//
//  Created by Matthew Woodruff on 08/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface KappenballAttempt : NSObject

@property(nonatomic, strong) NSString *playerName;
@property(nonatomic, assign) int energy;
@property(nonatomic, assign) BOOL success;

-(KappenballAttempt *)initWithPlayerName:(NSString *)playerName;
-(KappenballAttempt *)initWithPlayerName:(NSString *)playerName Energy:(int)energy Success:(BOOL)sucess;

@end
