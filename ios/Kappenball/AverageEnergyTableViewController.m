//
//  AverageEnergyTableViewController.m
//  Kappenball
//
//  Created by Matthew Woodruff on 11/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "AverageEnergyTableViewController.h"

@interface AverageEnergyTableViewController ()

@end

@implementation AverageEnergyTableViewController

@synthesize table, gamesHistory;

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    table.dataSource = self;
    table.delegate = self;
    [table reloadData];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return gamesHistory.gamesRankedByAverageEnergy.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"CellIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    
    if (cell==nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:CellIdentifier];
    }
    
    KappenballGame *g = [gamesHistory.gamesRankedByAverageEnergy objectAtIndex:indexPath.row];
    
    // Cell customisation
    NSString *spaces = indexPath.row >= 9 ? @"    " : @"      ";
    cell.textLabel.text = [NSString stringWithFormat:@"%ld%@%@", indexPath.row+1, spaces, g.playerName];
    cell.textLabel.textColor = [UIColor whiteColor];
    cell.textLabel.font = [UIFont systemFontOfSize:14];
    cell.detailTextLabel.text = [NSString stringWithFormat:@"%d", g.averageEnergy];
    cell.detailTextLabel.textColor = [UIColor whiteColor];
    cell.detailTextLabel.font = [UIFont boldSystemFontOfSize:14];
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 25;
}

@end
