//
//  LeaderboardViewController.m
//  Kappenball
//
//  Created by Matthew Woodruff on 05/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "LeaderboardViewController.h"
#import "EnergyTableViewController.h"
#import "AverageEnergyTableViewController.h"

@implementation LeaderboardViewController

@synthesize delegate, gamesHistory;

// Return to main menu
-(IBAction)menuButtonPressed:(UIButton *)sender
{
    [delegate dismiss];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Embed segue for energy table
    if ([[segue identifier]isEqualToString:@"ViewEnergyTable"]) {
        EnergyTableViewController *vc = [segue destinationViewController];
        vc.gamesHistory = gamesHistory;
        
    // Embed segue for the average energy table
    } else if ([[segue identifier]isEqualToString:@"ViewAverageEnergyTable"]) {
        AverageEnergyTableViewController *vc = [segue destinationViewController];
        vc.gamesHistory = gamesHistory;
    }
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
