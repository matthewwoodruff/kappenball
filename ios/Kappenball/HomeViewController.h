//
//  HomeViewController.h
//  Kappenball
//
//  Created by Matthew Woodruff on 05/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballViewDelegate.h"
#import "KappenballGamesHistory.h"

@interface HomeViewController : UIViewController <KappenballViewDelegate>

@property(nonatomic, weak) IBOutlet UIButton *gameStartButton;
@property(nonatomic, weak) KappenballGamesHistory *gamesHistory;

-(IBAction)buttonDown:(UIButton *)sender;
-(IBAction)buttonUp:(UIButton *)sender;

@end
