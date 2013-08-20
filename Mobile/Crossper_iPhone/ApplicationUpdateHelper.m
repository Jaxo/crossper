//
//  ApplicationUpdateHelper.m
//  FitzForm
//
//  Created by Shyam Gosavi on 29/05/13.
//  Copyright (c) 2013 megha malviya. All rights reserved.
//

#import "ApplicationUpdateHelper.h"

/// NSUserDefault macros to store user's preferences for fitzformAlertTypeSkip
#define kfitzformDefaultShouldSkipVersion @"fitzform Should Skip Version Boolean"
#define kfitzformDefaultSkippedVersion @"fitzform User Decided To Skip Version Update Boolean"

/// i18n/l10n macros
#define kfitzformCurrentVersion [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleShortVersionString"]
#define kfitzformBundle [[NSBundle mainBundle] pathForResource:@"fitzform" ofType:@"bundle"]
#define stringKey) \
[[NSBundle bundleWithPath:kfitzformBundle] localizedStringForKey:stringKey value:stringKey table:@"fitzformLocalizable"]

@interface ApplicationUpdateHelper()
<UIAlertViewDelegate>

@property (strong, nonatomic) NSDate *lastVersionCheckPerformedOnDate;

- (void)launchAppStore;

@end

@implementation ApplicationUpdateHelper
@synthesize delegate = _delegate;
#pragma mark - Initialization Methods
+ (id)sharedInstance
{
    static id sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[self alloc] init];
    });
    return sharedInstance;
}

- (id)init
{
    self = [super init];
    if (self) {
        _alertType = fitzformAlertTypeOption;
    }
    return self;
}

#pragma mark - Public Methods
-(void)getURLfromserver{
    self.applicationURL = [NSString stringWithFormat:@"http://itunes.apple.com/lookup?id=603089146"];
    [[ApplicationUpdateHelper sharedInstance] checkVersion];
}
- (void)checkVersion
{
    // Asynchronously query iTunes AppStore for publically available version
//    NSString *storeString = [NSString stringWithFormat:@"http://itunes.apple.com/lookup?id=%@", self.appID];
//    NSURL *storeURL = [NSURL URLWithString:storeString];
    
    NSURL *storeURL = [NSURL URLWithString:self.applicationURL];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:storeURL];
    [request setHTTPMethod:@"GET"];
    NSOperationQueue *queue = [[NSOperationQueue alloc] init];
    
    [NSURLConnection sendAsynchronousRequest:request queue:queue completionHandler:^(NSURLResponse *response, NSData *data, NSError *error) {
        
        if ( [data length] > 0 && !error ) { // Success
            
            NSDictionary *appData = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:nil];
            
            dispatch_async(dispatch_get_main_queue(), ^{
                
                // Store version comparison date
                self.lastVersionCheckPerformedOnDate = [NSDate date];
                
                // All versions that have been uploaded to the AppStore
                NSArray *versionsInAppStore = [[appData valueForKey:@"results"] valueForKey:@"version"];
                
                if ( ![versionsInAppStore count] ) { // No versions of app in AppStore
                    
                    return;
                    
                } else {
                    
                    NSString *currentAppStoreVersion = [versionsInAppStore objectAtIndex:0];
                    
                    if ( [kfitzformCurrentVersion compare:currentAppStoreVersion options:NSNumericSearch] == NSOrderedAscending ) {
                        
                        [self showAlertIfCurrentAppStoreVersionNotSkipped:currentAppStoreVersion];
                        
                    } else {
                        
                        // Current installed version is the newest public version or newer (e.g., dev version)
                        
                    }
                    
                }
                
            });
        }
        
    }];
}

- (void)checkVersionDaily
{
    /*
     On app's first launch, lastVersionCheckPerformedOnDate isn't set.
     Avoid false-positive fulfilment of second condition in this method.
     Also, performs version check on first launch.
     */
    if ( !self.lastVersionCheckPerformedOnDate ) {
        
        // Set Initial Date
        self.lastVersionCheckPerformedOnDate = [NSDate date];
        
        // Perform First Launch Check
        [self checkVersion];
        
    }
    
    // If daily condition is satisfied, perform version check
    if ( [self numberOfDaysElapsedBetweenILastVersionCheckDate] > 1 ) {
        
        [self checkVersion];
        
    }
}

- (void)checkVersionWeekly
{
    /*
     On app's first launch, lastVersionCheckPerformedOnDate isn't set.
     Avoid false-positive fulfilment of second condition in this method.
     Also, performs version check on first launch.
     */
    if ( !self.lastVersionCheckPerformedOnDate ) {
        
        // Set Initial Date
        self.lastVersionCheckPerformedOnDate = [NSDate date];
        
        // Perform First Launch Check
        [self checkVersion];
        
    }
    
    // If weekly condition is satisfied, perform version check
    if ( [self numberOfDaysElapsedBetweenILastVersionCheckDate] > 7 ) {
        
        [self checkVersion];
        
    }
}

#pragma mark - Private Methods
- (NSUInteger)numberOfDaysElapsedBetweenILastVersionCheckDate
{
    NSCalendar *currentCalendar = [NSCalendar currentCalendar];
    NSDateComponents *components = [currentCalendar components:kCFCalendarUnitDay
                                                      fromDate:self.lastVersionCheckPerformedOnDate
                                                        toDate:[NSDate date]
                                                       options:0];
    
    return [components day];
}

- (void)showAlertIfCurrentAppStoreVersionNotSkipped:(NSString *)currentAppStoreVersion
{
    // Check if user decided to skip this version in the past
    BOOL shouldSkipVersionUpdate = [[NSUserDefaults standardUserDefaults] boolForKey:kfitzformDefaultShouldSkipVersion];
    NSString *storedSkippedVersion = [[NSUserDefaults standardUserDefaults] objectForKey:kfitzformDefaultSkippedVersion];
    
    if ( !shouldSkipVersionUpdate ) {
        
        [self showAlertWithAppStoreVersion:currentAppStoreVersion];
        
    } else if ( shouldSkipVersionUpdate && ![storedSkippedVersion isEqualToString:currentAppStoreVersion] ) {
        
        [self showAlertWithAppStoreVersion:currentAppStoreVersion];
        
    } else {
        
        // Don't show alert.
        return;
    }
}

- (void)showAlertWithAppStoreVersion:(NSString *)currentAppStoreVersion
{
    // Reference App's name
    NSString *appName = [[[NSBundle mainBundle] infoDictionary] objectForKey:(NSString*)kCFBundleNameKey];
    
    switch ( self.alertType ) {
            
        case fitzformAlertTypeForce: {
            
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Update Available"
                                                                message:[NSString stringWithFormat:@"A new version of %@ is available. Please update to version %@ now.", appName, currentAppStoreVersion]
                                                               delegate:self
                                                      cancelButtonTitle:@"Update"
                                                      otherButtonTitles:nil, nil];
            
            [alertView show];
            
            
        } break;
            
        case fitzformAlertTypeOption: {
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Update Available"
                                                                message:[NSString stringWithFormat:@"A new version of %@ is available. Please update to version %@ now.", appName, currentAppStoreVersion]
                                                               delegate:self
                                                      cancelButtonTitle:@"Next time"
                                                      otherButtonTitles:@"Update", nil];
            
            [alertView show];
            
        } break;
            
        case fitzformAlertTypeSkip: {
            
            // Store currentAppStoreVersion in case user pushes skip
            [[NSUserDefaults standardUserDefaults] setObject:currentAppStoreVersion forKey:kfitzformDefaultSkippedVersion];
            [[NSUserDefaults standardUserDefaults] synchronize];
            
            UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"Update Available"
                                                                message:[NSString stringWithFormat:@"A new version of %@ is available. Please update to version %@ now.", appName, currentAppStoreVersion]
                                                               delegate:self
                                                      cancelButtonTitle:@"Skip this version"
                                                      otherButtonTitles:@"Update",@"Next time", nil];
            
            [alertView show];
            
        } break;
            
        default:
            break;
    }
    
    if([self.delegate respondsToSelector:@selector(fitzformDidShowUpdateDialog)]){
        [self.delegate fitzformDidShowUpdateDialog];
    }
}

- (void)launchAppStore
{
    NSString *iTunesString = [NSString stringWithFormat:@"https://itunes.apple.com/app/id%@", self.appID];
    NSURL *iTunesURL = [NSURL URLWithString:iTunesString];
    [[UIApplication sharedApplication] openURL:iTunesURL];
    
    if([self.delegate respondsToSelector:@selector(fitzformUserDidLaunchAppStore)]){
        [self.delegate fitzformUserDidLaunchAppStore];
    }
}

#pragma mark - UIAlertViewDelegate Methods
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    switch ( self.alertType ) {
            
        case fitzformAlertTypeForce: { // Launch App Store.app
            
            [self launchAppStore];
            
        } break;
            
        case fitzformAlertTypeOption: {
            
            if ( 1 == buttonIndex ) { // Launch App Store.app
                
                [self launchAppStore];
                
            } else { // Ask user on next launch
                
                if([self.delegate respondsToSelector:@selector(fitzformUserDidCancel)]){
                    [self.delegate fitzformUserDidCancel];
                }
                
            }
            
        } break;
            
        case fitzformAlertTypeSkip: {
            
            if ( 0 == buttonIndex ) { // Skip current version in AppStore
                
                [[NSUserDefaults standardUserDefaults] setBool:YES forKey:kfitzformDefaultShouldSkipVersion];
                [[NSUserDefaults standardUserDefaults] synchronize];
                
                if([self.delegate respondsToSelector:@selector(fitzformUserDidSkipVersion)]){
                    [self.delegate fitzformUserDidSkipVersion];
                }
                
            } else if ( 1 == buttonIndex ) { // Launch App Store.app
                
                [self launchAppStore];
                
            } else if ( 2 == buttonIndex) { // Ask user on next launch
                
                if([self.delegate respondsToSelector:@selector(fitzformUserDidCancel)]){
                    [self.delegate fitzformUserDidCancel];
                }
                
            }
            
        } break;
            
        default:
            break;
    }
}


@end
