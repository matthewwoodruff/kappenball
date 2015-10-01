//
//  BallStates.c
//  Kappenball
//
//  Created by Matthew Woodruff on 08/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#include <stdio.h>

// The states of the kappenball
typedef enum ballStateTypes
{
    BALL_ALIVE,
    BALL_DEAD,
    BALL_SUCCEEDED
} BallState;