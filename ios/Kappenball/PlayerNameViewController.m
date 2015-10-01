//
//  PlayerNameViewController.m
//  Kappenball
//
//  Created by Matthew Woodruff on 11/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "PlayerNameViewController.h"

@interface PlayerNameViewController ()

@end

@implementation PlayerNameViewController

@synthesize delegate, game, playerNameInput, enterNameMessage;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    playerNameInput.delegate = self;
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField {
    [playerNameInput resignFirstResponder];
    game.playerName = playerNameInput.text;
    return NO;
}

// Return back to game and resume
-(IBAction)cancelPressed
{
    [delegate dismissAndResumeGame];
}

// Abort game and return to main menu
-(IBAction)dontSavePressed
{
    [delegate dismissAndDontSave];
}

// Save game and return to main menu
-(IBAction)savePressed
{
    // Simple field validation
    if(game.playerName && game.playerName.length > 0) [delegate dismissAndSave];
    else enterNameMessage.hidden = NO;
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
