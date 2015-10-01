//
//  PlayerNameViewController.h
//  Kappenball
//
//  Created by Matthew Woodruff on 11/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballViewDelegate.h"
#import "KappenballPauseGameDelegate.h"
#import "KappenballGame.h"

@interface PlayerNameViewController : UIViewController <UITextFieldDelegate>

@property(nonatomic, weak) KappenballGame *game;
@property(nonatomic, weak) IBOutlet UITextField *playerNameInput;
@property(nonatomic, weak) IBOutlet UILabel *enterNameMessage;
@property (nonatomic, weak) id <KappenballPauseGameDelegate> delegate;

-(IBAction)savePressed;
-(IBAction)cancelPressed;
-(IBAction)dontSavePressed;

-(IBAction)buttonDown:(UIButton *)sender;
-(IBAction)buttonUp:(UIButton *)sender;

@end
