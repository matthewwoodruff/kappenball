//
//  KappenballObstacles.m
//  Kappenball
//
//  Created by Matthew Woodruff on 04/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "KappenballObstacles.h"

@implementation KappenballObstacles

@synthesize frame = _frame;

// Init the kappenball obstacle with it's frame
-(KappenballObstacles *)initWithFrame:(CGRect)frame
{
    _frame = frame;
    return self;
}

// Determine the boundary of the obstacle given a collision has occurred from a given direction
-(float)collisionBoundaryForOpponentDirection:(DirectionState)opponentDirection
{
    switch (opponentDirection) {
        case UP:
            return self.frame.origin.y;
        case DOWN:
            return self.frame.origin.y + self.frame.size.height;
        case LEFT:
            return self.frame.origin.x + self.frame.size.width;
        case RIGHT:
            return self.frame.origin.x;
    }
}

@end
