//
//  KappenballViewController.h
//  Kappenball
//
//  Created by Matthew Woodruff on 03/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballViewDelegate.h"
#import "KappenballPauseGameDelegate.h"
#import "KappenballGame.h"
#import "KappenballGamesHistory.h"
#include <AudioToolbox/AudioToolbox.h>

@interface KappenballViewController : UIViewController <KappenballPauseGameDelegate> 

@property(nonatomic, weak) IBOutlet UIButton *startPauseButton;
@property(nonatomic, weak) IBOutlet UIButton *endButton;
@property(nonatomic, weak) IBOutlet UISlider *noiseSlider;
@property(nonatomic, weak) IBOutlet UIImageView *playSpace;
@property(nonatomic, weak) IBOutlet UILabel *scoreLabel;
@property(nonatomic, weak) IBOutlet UILabel *averageEnergyLabel;
@property(nonatomic, weak) IBOutlet UILabel *energyLabel;
@property(nonatomic, weak) KappenballGame *game;
@property(nonatomic, weak) KappenballGamesHistory *gamesHistory;
@property(nonatomic, weak) id <KappenballViewDelegate> delegate;

-(IBAction)startPausePressed;
-(IBAction)endPressed;

-(IBAction)buttonDown:(UIButton *)sender;
-(IBAction)buttonUp:(UIButton *)sender;

@end
