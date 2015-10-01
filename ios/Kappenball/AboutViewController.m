//
//  AboutViewController.m
//  Kappenball
//
//  Created by Matthew Woodruff on 11/04/2013.
//  Copyright (c) 2013 Matthew Woodruff. All rights reserved.
//

#import "AboutViewController.h"

@interface AboutViewController()

@property(nonatomic, strong) NSString* htmlString;

@end

@implementation AboutViewController

@synthesize delegate, webView, htmlString;

-(void)viewDidLoad
{
    NSString* htmlFile = [[NSBundle mainBundle] pathForResource:@"about" ofType:@"html"];
    self.htmlString = [NSString stringWithContentsOfFile:htmlFile encoding:NSUTF8StringEncoding error:nil];
    [webView loadHTMLString:self.htmlString baseURL:nil];
}

-(IBAction)menuButtonPressed
{
    [delegate dismiss];
}

-(IBAction)buttonDown:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.1563 green:0.6484 blue:0.1094 alpha:1.0];
}

-(IBAction)buttonUp:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.0664 green:0.3320 blue:0.0664 alpha:1.0];
}

-(IBAction)browserBack
{
    self.webView.canGoBack ? [self.webView goBack] : [self.webView loadHTMLString:self.htmlString baseURL:nil];
}

@end
