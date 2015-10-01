//
//  LeaderboardViewController.h
//  Kappenball
//
//  Created by Matthew Woodruff on 05/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballViewDelegate.h"
#import "KappenballGamesHistory.h"

@interface LeaderboardViewController : UIViewController

@property (nonatomic, weak) id <KappenballViewDelegate> delegate;
@property(nonatomic, weak) KappenballGamesHistory *gamesHistory;

-(IBAction)menuButtonPressed:(UIButton *)sender;
-(IBAction)buttonDown:(UIButton *)sender;
-(IBAction)buttonUp:(UIButton *)sender;

@end
