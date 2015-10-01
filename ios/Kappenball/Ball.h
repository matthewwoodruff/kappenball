//
//  Ball.h
//  Kappenball
//
//  Created by Matthew Woodruff on 03/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Directions.c"

@interface Ball : UIImageView

@property(nonatomic, assign) float velocity;
@property(nonatomic, assign) float acceleration;
@property(nonatomic, readonly, assign) CGRect alias;
@property(nonatomic, readonly, assign) DirectionState direction;

-(Ball *)initWithImage:(UIImage*)image Alias:(CGRect)aAlias;

@end
