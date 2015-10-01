//
//  Ball.m
//  Kappenball
//
//  Created by Matthew Woodruff on 03/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "Ball.h"

@implementation Ball

@synthesize velocity, acceleration;
@synthesize alias = _alias;

// Init the ball with an image and an alias
-(Ball *)initWithImage:(UIImage*)image Alias:(CGRect)alias
{
    self = [super initWithImage:image];
    if (self) {
        _alias = alias;
    }
    return self;
}

// The ball's alias - used for collision detection
// The alias sits inside the frame of the ball to represent the ball's boundaries
// as the ball's image may be larger than it's boundaries
-(CGRect)alias
{
    return CGRectMake(self.frame.origin.x + _alias.origin.x, self.frame.origin.y + _alias.origin.y, _alias.size.width, _alias.size.height);
}

// The direction the ball is travelling (currently supporting x-axis only)
-(DirectionState)direction
{
    if(self.velocity < 0) return LEFT;
    else if(self.velocity > 0) return RIGHT;
    else return DOWN;
}

@end
