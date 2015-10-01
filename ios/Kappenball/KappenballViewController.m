//
//  KappenballViewController.m
//  Kappenball
//
//  Created by Matthew Woodruff on 03/11/2012.
//  Copyright (c) 2012 Matthew Woodruff. All rights reserved.
//

#import "KappenballViewController.h"
#import "Ball.h"
#import "KappenballObstacles.h"
#import <QuartzCore/QuartzCore.h>
#import "PauseGameViewController.h"
#import "Directions.c"
#import "BallStates.c"
#import "PlayerNameViewController.h"
#import <AVFoundation/AVFoundation.h>

@interface KappenballViewController () 

@property(nonatomic, strong) NSTimer *gameTimer;
@property(nonatomic, weak) Ball *ball;
@property(nonatomic, strong) KappenballAttempt *currentAttempt;

@property(nonatomic, weak) UIImageView *fingerGlow;

@property(nonatomic, readonly, strong) NSArray *walls;
@property(nonatomic, readonly, strong) NSArray *spikes;

@property(nonatomic, assign) SystemSoundID deadBallSound;
@property(nonatomic, assign) SystemSoundID successBallSound;
@property(nonatomic, assign) SystemSoundID wallHitSound;

@property(nonatomic, assign) BOOL gamePaused;

-(void)newAttempt;
-(void)stopTimer;
-(void)startTimer;

-(void)progressBall;
-(BallState)getBallState;

-(CGPoint)defaultBallOrigin;

-(void)updateAllLabels;
-(void)updateEnergy;

+(float)collisionBoundaryForFrame:(CGRect)frame Direction:(DirectionState)direction;

@end

@implementation KappenballViewController

#define X_BORDER_WIDTH 14
#define TRAP_HEIGHT 12
#define GATE_HEIGHT 25

#define ACCELERATION 1.2
#define COLLISION_DECAY 0.8

#define GATE_1_L 104
#define GATE_1_R 147
#define GATE_2_L 298
#define GATE_2_R 345

#define BALL_WIDTH 31
#define BALL_HEIGHT 31

#define DT 0.1
#define DECAY 0.95

@synthesize gameTimer, delegate, game, gamesHistory, currentAttempt, fingerGlow, deadBallSound, successBallSound, wallHitSound, gamePaused;
@synthesize ball = _ball;
@synthesize walls = _walls;
@synthesize spikes = _spikes;

-(void)viewDidLoad
{
    [super viewDidLoad];
    
    // Set up customisation on the noise slider
    [self.noiseSlider setThumbImage:[UIImage imageNamed:@"thumb.png"] forState:UIControlStateNormal];
    [self.noiseSlider setMaximumTrackImage:[[UIImage imageNamed:@"slider1.png" ] resizableImageWithCapInsets:UIEdgeInsetsMake(0, 0, 0, 5)] forState:UIControlStateNormal];
    [self.noiseSlider setMinimumTrackImage:[[UIImage imageNamed:@"slider2.png"] resizableImageWithCapInsets:UIEdgeInsetsMake(0, 5, 0, 0)]forState:UIControlStateNormal];
    
    // Set up the finger glow UIImageView for when the player touches the display
    UIImageView *tempFG = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"FingerGlow.png" ]];
    tempFG.hidden = YES;
    tempFG.alpha = 0.5;
    [self.view addSubview:tempFG];
    fingerGlow = tempFG;
    tempFG = nil;
    
    // Set up the three sounds used in the game
    // Dead ball sound
    
    CFBundleRef mainBundle = CFBundleGetMainBundle ();
    AudioServicesCreateSystemSoundID (
                  CFBundleCopyResourceURL (
                                           mainBundle,
                                           CFSTR ("DeadBallSound"),
                                           CFSTR ("caf"),
                                           NULL
                                           ),
                  &deadBallSound
                  );
    // Success ball sound
    AudioServicesCreateSystemSoundID (
                  CFBundleCopyResourceURL (
                                           mainBundle,
                                           CFSTR ("SuccessBallSound"),
                                           CFSTR ("caf"),
                                           NULL
                                           ),
                  &successBallSound
                  );
    // Wall bounce/hit sound
    AudioServicesCreateSystemSoundID (
                  CFBundleCopyResourceURL (
                                           mainBundle,
                                           CFSTR ("WallHitSound"),
                                           CFSTR ("caf"),
                                           NULL
                                           ),
                  &wallHitSound
                  );
    
    // Activate the game
    self.game.active = YES;
    
    // Set up the new attempt
    [self newAttempt];
    
    // Start the timer
    [self startTimer];
}

/* METHODS FOR INTERFACE BUILDER OBJECTS */
-(IBAction)startPausePressed
{
    gamePaused = YES;
    [self stopTimer];
    [self performSegueWithIdentifier:@"PauseGame" sender:self];
}

-(IBAction)endPressed
{
    gamePaused = YES;
    [self stopTimer];
    
    // If no attempts have been completed then quit the game without pause or save
    if(game.attempts.count > 0 ) {
        [self performSegueWithIdentifier:@"AddPlayerName" sender:self];
    } else {
        [self dismissAndDontSave];
    }
}

-(void)updateAllLabels
{
    [self.scoreLabel setText:[NSString stringWithFormat:@"Score: %d/%ld", self.game.score, self.game.attempts.count]];
    [self.averageEnergyLabel setText:[NSString stringWithFormat:@"Average: %d", self.game.averageEnergy]];
    [self updateEnergy];
}

-(void)updateEnergy
{
    [self.energyLabel setText:[NSString stringWithFormat:@"Energy: %d", self.currentAttempt.energy]];
}
/* END METHODS FOR INTERFACE BUILDER OBJECTS */


/* SEGUE CONTROLS */
-(void)dismiss
{
    gamePaused = NO;
    [self.view setNeedsDisplay];
    [self dismissViewControllerAnimated:YES completion:nil];
}

-(void)dismissAndResumeGame
{
    [self dismiss];
    [self startTimer];
}

-(void)dismissAndRestartGame
{
    [self dismiss];
    [self.game clearAttempts];
    self.currentAttempt = nil;
    [self newAttempt];
    [self startTimer];
}

-(void)dismissAndReturnToMainMenu
{
    // If no attempts have been completed then quit the game without pause or save
    if(game.attempts.count > 0 ) {
        self.currentAttempt = nil;
        [self.delegate dismiss];
    } else {
        [self dismissAndDontSave];
    }
}

-(void)dismissAndSave
{
    // save the game
    [gamesHistory addGame:game];
    
    // set the save game flag so the home view controller
    // knows to set up a new game
    game.saved = YES;
    
    // deactivate game
    game.active = false;
    [self.delegate dismiss];
}

-(void)dismissAndDontSave
{
    game.active = false;
    [self.delegate dismiss];
}

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Pause game segue
    if ([[segue identifier]isEqualToString:@"PauseGame"]) {
        PauseGameViewController *vc = [segue destinationViewController];
        vc.delegate = self;
        
    // Add player name segue
    } else if ([[segue identifier]isEqualToString:@"AddPlayerName"]) {
        PlayerNameViewController *vc = [segue destinationViewController];
        vc.delegate = self;
        vc.game = game;
    }
}
/* END SEGUE CONTROLS */


/* GAME CONTROLS */
-(void)newAttempt
{
    // Set up current attempt
    self.currentAttempt = [[KappenballAttempt alloc] initWithPlayerName:game.playerName];
    
    // RESET THE BALL
    self.ball.velocity = 0;
    self.ball.acceleration = 0;
    
    CGRect ballFrame = self.ball.frame;
    ballFrame.origin = [self defaultBallOrigin];
    self.ball.frame = ballFrame;
    
    // Update the labels
    [self updateAllLabels];
}

-(void)startTimer
{
    if(!gameTimer.isValid) gameTimer = [NSTimer scheduledTimerWithTimeInterval:0.02 target:self selector:@selector(progressBall) userInfo:nil repeats:YES];
}

-(void)stopTimer
{
    if(gameTimer.isValid) [gameTimer invalidate];
}
/* END GAME CONTROLS */


/* GAME LOGIC */
-(void)progressBall
{
    // Get the ball's current state
    switch ([self getBallState]) {
        
        // if ball is alive then need to determine it's x position
        case BALL_ALIVE:
        {
            // Increment the energy if the ball is under acceleration
            if(self.ball.acceleration!=0) {
                self.currentAttempt.energy++;
                [self updateEnergy];
            }
            
            // Generate the proposed X value and velocity
            int rand = arc4random() % 40;
            self.ball.velocity = (self.ball.velocity*DECAY)+self.ball.acceleration+(self.noiseSlider.value*(-20 + rand));
            float xMovement = self.ball.velocity*DT;
            
            // Apply to change to ball's alias
            CGRect ballAlias = [self.ball alias];
            ballAlias.origin.x += xMovement;
            ballAlias.origin.y ++;
            
            // Get current frame
            CGRect ballFrame = self.ball.frame;
            ballFrame.origin.x += xMovement;
            
            // Test if the ball alias collides with any of the obstacles.
            // if it does - adjust the proposed x value appropriately
            // If acceleration is present then the x value becomes the obstacles
            // boundary as this simulates pressing the ball to the boundary.
            // If there is no acceleration then apply an inelastic collision algorithm.
            for(KappenballObstacles *ko in self.walls) {
                if(CGRectIntersectsRect(ko.frame, ballAlias)) {
                    float wallBoundary = [ko collisionBoundaryForOpponentDirection:self.ball.direction];
                    float ballBoundary = [KappenballViewController collisionBoundaryForFrame:ballAlias Direction:self.ball.direction];
                    if(self.ball.acceleration == 0) {
                        AudioServicesPlaySystemSound (wallHitSound);
                        ballFrame.origin.x += (2*wallBoundary-2*ballBoundary) * COLLISION_DECAY;
                        self.ball.velocity *= -COLLISION_DECAY;
                    } else {
                        ballFrame.origin.x += wallBoundary - ballBoundary;
                        self.ball.velocity = 0;
                    }
                }
            }
            
            // Apply changes to the ball's frame
            ballFrame.origin.y++;
            self.ball.frame = ballFrame;
            
            break;
        }
            
        // If the ball is dead then need to save attempt, show animation, play sound and setup new attempt
        case BALL_DEAD:
        {
            [self stopTimer];
            
            // set the success flag
            self.currentAttempt.success = NO;
            
            // add the completed attempt
            [game addAttempt:currentAttempt];
            
            // Back up the ball's orginal transform
            CGAffineTransform t = self.ball.transform;
            
            // Play the dead ball sound
            AudioServicesPlaySystemSound (deadBallSound);
            
            // vibrate the phone
            AudioServicesPlaySystemSound (kSystemSoundID_Vibrate);
            
            // Spiked ball animation
            [UIView animateWithDuration:1
                                  delay:0
                                options:UIViewAnimationCurveEaseIn
                             animations:^{
                                 CGAffineTransform t1 = CGAffineTransformScale(self.ball.transform, 0, 0);
                                 CGAffineTransform t2 = CGAffineTransformTranslate(self.ball.transform, 0, 10);
                                 self.ball.transform = CGAffineTransformConcat(t1, t2);
                             }
                             completion:^(BOOL finished){
                                 // Apply the old transform
                                 self.ball.transform = t;
                                 
                                 // setup new attempt
                                 [self newAttempt];
                                 
                                 // start timer is the game is not paused
                                 if(!gamePaused)[self startTimer];
                             }
             ];
            break;
        }
            
        // If the ball has succeeded then save attempt, play sound and setup new attempt
        case BALL_SUCCEEDED:
        {
            // play ball succeeded sound
            AudioServicesPlaySystemSound (successBallSound);
            
            // set success flag
            self.currentAttempt.success = YES;
            
            // Add the completed attempt and set up new attempt
            [game addAttempt:currentAttempt];
            [self newAttempt];
            break;
        }
    }
}

// returns the ball's current state
-(BallState)getBallState
{
    CGRect ballAlias = self.ball.alias;
    
    // If the ball has hit a spike then return ball dead
    for(KappenballObstacles *trap in self.spikes) if(CGRectIntersectsRect(ballAlias, trap.frame)) return BALL_DEAD;
    
    CGRect playSpaceBounds = self.playSpace.bounds;
    
    // If the ball hasn't hit the spike then return whether the ball is
    // still in the game or if it has succeeded
    return ballAlias.origin.y <= playSpaceBounds.size.height ? BALL_ALIVE : BALL_SUCCEEDED;
}

// Returns the collision boundary of a CGRect for a given direction (Only left and right are currently used)
+(float)collisionBoundaryForFrame:(CGRect)frame Direction:(DirectionState)direction
{
    switch (direction) {
        case UP:
            return frame.origin.y;
        case DOWN:
            return frame.origin.y + frame.size.height;
        case LEFT:
            return frame.origin.x;
        case RIGHT:
            return frame.origin.x + frame.size.width;
    }
}
/* END GAME LOGIC */


/* BALL, WALLS AND spikes SETUP */

// Lazily instantiate the ball
-(UIImageView *)ball
{
    if(!_ball && [self.gameTimer isValid]) {
        __strong Ball *tempImageView = [[Ball alloc] initWithImage:[UIImage imageNamed:@"ball.png"] Alias:CGRectMake(7, 7, 17, 17)];
        
        CGRect ballFrame = tempImageView.frame;
        ballFrame.origin = [self defaultBallOrigin];
        tempImageView.frame = ballFrame;
        
        [self.playSpace addSubview:tempImageView];
        _ball = tempImageView;
        tempImageView = nil;
    }
    return _ball;
}

// Returns the balls starting origin
-(CGPoint)defaultBallOrigin
{
    CGRect playBounds = self.playSpace.bounds;
    
    int x = (playBounds.size.width - BALL_WIDTH) * 0.5;
    int y = -BALL_HEIGHT;
    
    return CGPointMake(x, y);
}

// Lazily instantiate the walls
-(NSArray *)walls
{
    if(!_walls) {
        
        CGRect playSpaceBounds = self.playSpace.bounds;
        const int gateY = playSpaceBounds.size.height-GATE_HEIGHT;
        
        _walls = [[NSArray alloc] initWithObjects:
                       // left bound
                       [[KappenballObstacles alloc] initWithFrame:CGRectMake(0, 0, X_BORDER_WIDTH, playSpaceBounds.size.height)],
                       // right bound
                       [[KappenballObstacles alloc] initWithFrame:CGRectMake(playSpaceBounds.size.width-X_BORDER_WIDTH, 0, X_BORDER_WIDTH, playSpaceBounds.size.height)],
                       
                       // gate 1 left
                       [[KappenballObstacles alloc] initWithFrame:CGRectMake(GATE_1_L-X_BORDER_WIDTH, gateY, X_BORDER_WIDTH, GATE_HEIGHT)],
                       // gate 1 right
                       [[KappenballObstacles alloc] initWithFrame:CGRectMake(GATE_1_R, gateY, X_BORDER_WIDTH, GATE_HEIGHT)],
                       
                       // gate 2 left
                       [[KappenballObstacles alloc] initWithFrame:CGRectMake(GATE_2_L-X_BORDER_WIDTH, gateY, X_BORDER_WIDTH, GATE_HEIGHT)],
                       // gate 1 right
                       [[KappenballObstacles alloc] initWithFrame:CGRectMake(GATE_2_R, gateY, X_BORDER_WIDTH, GATE_HEIGHT)],
                       nil];
        
        // Uncomment to see wall placement
       /*
        for(KappenballObstacles *a in _walls) {
            UIView *b = [[UIView alloc] initWithFrame:a.frame];
            b.backgroundColor = [UIColor blueColor];
            b.alpha = 0.5;
            [self.playSpace addSubview:b];
        }
        */
        
    }
    return _walls;
}

// Lazily instantiate the spikes
-(NSArray *)spikes
{
    if(!_spikes) {
            
        CGRect playSpaceBounds = self.playSpace.bounds;
        const int trapY = playSpaceBounds.size.height-(GATE_HEIGHT+TRAP_HEIGHT);
        
        _spikes = [[NSArray alloc] initWithObjects:
                  // Left trap
                  [[KappenballObstacles alloc] initWithFrame:CGRectMake(X_BORDER_WIDTH, trapY, GATE_1_L-X_BORDER_WIDTH, TRAP_HEIGHT)],
                  // Middle trap
                  [[KappenballObstacles alloc] initWithFrame:CGRectMake(GATE_1_R, trapY, GATE_2_L-GATE_1_R, TRAP_HEIGHT)],
                  // Right trap
                  [[KappenballObstacles alloc] initWithFrame:CGRectMake(GATE_2_R, trapY, playSpaceBounds.size.width-GATE_2_R-X_BORDER_WIDTH, TRAP_HEIGHT)],
                  nil];
        
        // Uncomment to see trap placement
        /*
        for(KappenballObstacles *a in _spikes) {
            UIView *b = [[UIView alloc] initWithFrame:a.frame];
            b.backgroundColor = [UIColor redColor];
            b.alpha = 0.5;
            [self.playSpace addSubview:b];
        }
         */
         
    }
    return _spikes;
}
/* END BALL, WALLS AND spikes SETUP */


/* TOUCH EVENTS */
-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    UITouch *touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self.playSpace];
    
    // Determine the ball's acceleration based upon the position of the touch
    CGPoint ballCenter = self.ball.center;
    self.ball.acceleration = touchPoint.x >= ballCenter.x ? -ACCELERATION : ACCELERATION;
    
    // Set the fingerglow image to the touch location
    fingerGlow.center = [touch locationInView:self.view];
    fingerGlow.hidden = NO;
    
    // Show the pulsating animation
    [UIView animateWithDuration:0.5
                          delay:0
                        options:(UIViewAnimationOptionAutoreverse | UIViewAnimationOptionRepeat)
                     animations:^{
                         fingerGlow.alpha = 1;
                     }
                     completion:nil
     ];
}

-(void)touchesMoved:(NSSet*)touches withEvent:(UIEvent*)event{
    UITouch *touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self.playSpace];
    
    // Set the fingerglow image to the touch location
    fingerGlow.center=[touch locationInView:self.view];
    
    // Determine thw ball's acceleration based upon the position of the touch
    CGPoint ballCenter = self.ball.center;
    self.ball.acceleration = touchPoint.x >= ballCenter.x ? -ACCELERATION : ACCELERATION;
}

-(void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
    // hide the fingerglow image and set the ball's acceleration to 0
    fingerGlow.hidden = YES;
    fingerGlow.alpha = 0.5;
    self.ball.acceleration = 0;
}

-(void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event
{
    // If the touch is cancelled then pause the game
    [self startPausePressed];
}
/* END TOUCH EVENTS */

-(IBAction)buttonDown:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.1563 green:0.6484 blue:0.1094 alpha:1.0];
}

-(IBAction)buttonUp:(UIButton *)sender
{
    sender.backgroundColor = [UIColor colorWithRed:0.0664 green:0.3320 blue:0.0664 alpha:1.0];
}

@end