//
//  KappenballPauseGameDelegate.h
//  Kappenball
//
//  Created by Matthew Woodruff on 05/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol KappenballPauseGameDelegate <NSObject>
-(void)dismiss;
-(void)dismissAndSave;
-(void)dismissAndDontSave;
-(void)dismissAndResumeGame;
-(void)dismissAndRestartGame;
-(void)dismissAndReturnToMainMenu;
@end
