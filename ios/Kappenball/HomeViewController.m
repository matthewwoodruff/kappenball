//
//  HomeViewController.m
//  Kappenball
//
//  Created by Matthew Woodruff on 05/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "HomeViewController.h"
#import "KappenballViewController.h"
#import "KappenballGame.h"
#import "LeaderboardViewController.h"
#import "AboutViewController.h"

@interface HomeViewController ()

@property(nonatomic, strong) KappenballGame *game;

@end

@implementation HomeViewController

@synthesize game, gameStartButton, gamesHistory;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    
    // Create a new game object upon loading
    game = [[KappenballGame alloc] init];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // The leaderboard segue
    if ([[segue identifier]isEqualToString:@"ViewLeaderboard"]) {
        LeaderboardViewController *vc = [segue destinationViewController];
        vc.delegate = self;
        vc.gamesHistory = gamesHistory;
        
    // The game segue
    } else if ([[segue identifier]isEqualToString:@"ViewGame"]) {
        KappenballViewController *vc = [segue destinationViewController];
        vc.delegate = self;
        vc.game = game;
        vc.gamesHistory = gamesHistory;
    // Embed segue for the average energy table
    } else if ([[segue identifier]isEqualToString:@"ViewAbout"]) {
        AboutViewController *vc = [segue destinationViewController];
        vc.delegate = self;
    }
}

-(void)dismiss
{
    // Determine if the game is to be resumed or has been saved
    if(!game || game.saved || !game.active) game = [[KappenballGame alloc] init];
    game.active ? [gameStartButton setTitle:@"Resume" forState:UIControlStateNormal] : [self.gameStartButton setTitle:@"New Game" forState:UIControlStateNormal];
    
    [self.view setNeedsDisplay];
    [self dismissViewControllerAnimated:YES completion:nil];
}

/*
 * Changes the colour of the button on touch down
 */
-(IBAction)buttonDown:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.1563 green:0.6484 blue:0.1094 alpha:1.0];
}

-(IBAction)buttonUp:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.0664 green:0.3320 blue:0.0664 alpha:1.0];
}


@end
