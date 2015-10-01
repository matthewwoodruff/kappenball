//
//  KappenballObstacles.h
//  Kappenball
//
//  Created by Matthew Woodruff on 04/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Directions.c"

@interface KappenballObstacles : NSObject

@property(nonatomic, readonly, assign) CGRect frame;

-(KappenballObstacles *)initWithFrame:(CGRect)frame;
-(float)collisionBoundaryForOpponentDirection:(DirectionState)opponentDirection;

@end
