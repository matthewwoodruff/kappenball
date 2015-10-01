//
//  AboutViewController.h
//  Kappenball
//
//  Created by Matthew Woodruff on 11/04/2013.
//  Copyright (c) 2013 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballViewDelegate.h"

@interface AboutViewController : UIViewController

@property (nonatomic, weak) id <KappenballViewDelegate> delegate;
@property (nonatomic, weak) IBOutlet UIWebView* webView;

-(IBAction)menuButtonPressed;
-(IBAction)buttonDown:(UIButton *)sender;
-(IBAction)buttonUp:(UIButton *)sender;

-(IBAction)browserBack;

@end
