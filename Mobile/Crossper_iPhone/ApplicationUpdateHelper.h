//
//  ApplicationUpdateHelper.h
//  FitzForm
//
//  Created by Shyam Gosavi on 29/05/13.
//  Copyright (c) 2013 Clarice technologies. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol AppUpdateDelegate <NSObject>

@optional
- (void)fitzformDidShowUpdateDialog; // User presented with update dialog
- (void)fitzformUserDidLaunchAppStore; // User did click on button that launched App Store.app
- (void)fitzformUserDidSkipVersion; // User did click on button that skips version update
- (void)fitzformUserDidCancel; // User did click on button that cancels update dialog

@end

typedef NS_ENUM(NSUInteger, fitzformAlertType)
{
    
    fitzformAlertTypeForce, // Forces user to update your app
    fitzformAlertTypeOption, // (DEFAULT) Presents user with option to update app now or at next launch
    fitzformAlertTypeSkip // Presents User with option to update the app now, at next launch, or to skip this version all together
    
};

@interface ApplicationUpdateHelper : NSObject

/**
 The fitzform delegate can be used to know when the update dialog is shown and which action a user took.
 See the @protocol declaration above.
 */
@property (assign, nonatomic) id<AppUpdateDelegate> delegate;

/**
 The app id of your app.
 */
@property (strong, nonatomic) NSString *appID;
/**
 The application URL .
 */
@property (strong, nonatomic) NSString *applicationURL;
/**
 The iTune application URL for donwload updated version.
 */
@property (strong, nonatomic) NSString *iTuneApplicationURL;

/**
 The alert type to present to the user when there is an update. See the `fitzformAlertType` enum above.
 */
@property (assign, nonatomic) fitzformAlertType alertType;

/**
 The shared fitzform instance.
 */
+ (id)sharedInstance;

/**
 Checks the installed version of your application against the version currently available on the iTunes store.
 If a newer version exists in the AppStore, fitzform prompts your user to update their copy of your app.
 */

/**
 NOTE: ONLY USE ONE OF THE METHODS BELOW, AS THEY ALL PERFORM A CHECK ON YOUR APPLICATION'S FIRST LAUNCH
 USING MULTIPLE METHODS WILL CAUSE MULTIPLE UIALERTVIEWS TO POP UP.
 */

/*
 Perform check for new version of your app
 Place in application:didFinishLaunchingWithOptions: AFTER calling makeKeyAndVisible on your UIWindow iVar
 */
- (void)checkVersion;

/*
 Perform daily check for new version of your app
 Useful if user returns to you app from background after extended period of time
 Place in applicationDidBecomeActive:
 */
- (void)checkVersionDaily;

/*
 Perform weekly check for new version of your app
 Useful if user returns to you app from background after extended period of time
 Place in applicationDidBecomeActive:
 */
- (void)checkVersionWeekly;
/*
 Perform to take application url from server
 */
-(void)getURLfromserver;

@end