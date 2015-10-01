//
//  PauseGameViewController.m
//  Kappenball
//
//  Created by Matthew Woodruff on 05/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "PauseGameViewController.h"

@implementation PauseGameViewController

// Return to game and resume
-(IBAction)resumeButtonPressed
{
    [self.delegate dismissAndResumeGame];
}

// Return to game view and restart
-(IBAction)restartButtonPressed
{
    [self.delegate dismissAndRestartGame];
}

// Return to main menu (the game has been paused)
-(IBAction)menuButtonPressed
{
    [self.delegate dismissAndReturnToMainMenu];
}

-(IBAction)buttonDown:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.1563 green:0.6484 blue:0.1094 alpha:1.0];
}

-(IBAction)buttonUp:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.0664 green:0.3320 blue:0.0664 alpha:1.0];
}

@end
