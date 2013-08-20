//
//  ViewController.h




#import <UIKit/UIKit.h>
#import <ZXingWidgetController.h>
#import <QRCodeReader.h>
#import <Cordova/CDVViewController.h>
#import <Cordova/CDVPlugin.h>

NSString * const kDummyURLForIOS =  @"http://www.google.com/";
NSString * const kBaseURL = @"http://192.168.41.1:8080/crossper/mobile";
NSString * const kLoginURL  = @"/api/login";
NSString * const kOffers  = @"/api/offers";
@interface ViewController : CDVViewController  <UIWebViewDelegate, ZXingDelegate>
{
    ZXingWidgetController *widController;
    BOOL authenticated;
    NSURLConnection *urlConnection;
    NSURLRequest *urlRequest;
    NSDictionary *jsonDict;
}

@property (retain, nonatomic) IBOutlet UIActivityIndicatorView *busyIndicator;
@property (retain, nonatomic) IBOutlet UIView *cameraView;
@property (retain, nonatomic) IBOutlet UIView *webUIview;
@property (retain, nonatomic) NSURLRequest *urlRequest;
@property (nonatomic, retain) ZXingWidgetController *widController;
@property (unsafe_unretained, nonatomic) IBOutlet UIView *loadingView;
@property (unsafe_unretained, nonatomic) IBOutlet UILabel *loadinglabel;

- (void)hideOtherViews;
- (void)ScannedPressed;
@end
