//
//  KappenballAppDelegate.h
//  Kappenball
//
//  Created by Matthew Woodruff on 03/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballGamesHistory.h"

@interface KappenballAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) NSString *databaseName;
@property (strong, nonatomic) NSString *databasePath;
@property (strong, nonatomic) NSMutableArray *games;
@property (strong, nonatomic) KappenballGamesHistory *gamesHistory;

-(void)initializeDatabase;

@end
