//
//  PauseGameViewController.h
//  Kappenball
//
//  Created by Matthew Woodruff on 05/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballPauseGameDelegate.h"

@interface PauseGameViewController : UIViewController

@property (nonatomic, weak) id <KappenballPauseGameDelegate> delegate;

-(IBAction)resumeButtonPressed;
-(IBAction)restartButtonPressed;
-(IBAction)menuButtonPressed;

-(IBAction)buttonDown:(UIButton *)sender;
-(IBAction)buttonUp:(UIButton *)sender;

@end
