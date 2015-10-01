//
//  AverageEnergyTableViewController.h
//  Kappenball
//
//  Created by Matthew Woodruff on 11/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "KappenballGamesHistory.h"

@interface AverageEnergyTableViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property(nonatomic, weak) IBOutlet UITableView *table;
@property(nonatomic, weak) KappenballGamesHistory *gamesHistory;

@end
