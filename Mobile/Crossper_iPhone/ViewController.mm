//
//  ViewController.m
//  Crossper

#import "ViewController.h"
#import <QuartzCore/QuartzCore.h>
#import <CommonCrypto/CommonDigest.h>

#define SPACING 3.
@interface ViewController ()

@end

@implementation ViewController
@synthesize busyIndicator;
@synthesize widController;
@synthesize urlRequest;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewWillAppear:(BOOL)animated
{
    // Set the main view to utilize the entire application frame space of the device.
    // Change this to suit your view's UI footprint needs in your application.
    [super viewWillAppear:animated];

    UIView* rootView = [[[[UIApplication sharedApplication] keyWindow] rootViewController] view];
    CGRect webViewFrame = [[[rootView subviews] objectAtIndex:0] frame];  // first subview is the UIWebView
    
    if (CGRectEqualToRect(webViewFrame, CGRectZero)) { // UIWebView is sized according to its parent, here it hasn't been sized yet
        self.view.frame = [[UIScreen mainScreen] applicationFrame]; // size UIWebView's parent according to application frame, which will in turn resize the UIWebView
    }
    // [self ScannedPressed];
   
}

- (void)viewDidLoad
{
    [super viewDidLoad];
  
    NSURLRequest *tempurlRequest = [NSURLRequest requestWithURL:[[NSURL alloc] initWithString:[NSString stringWithFormat:@"%@",kBaseURL]]];
    self.webView.delegate = self;
     urlRequest  = tempurlRequest;
    [self.webView loadRequest:urlRequest];
    self.webView.frame = self.view.frame;
    [self.webUIview addSubview:self.webView];
    [self.webUIview addSubview:self.loadingView];
}

- (void)viewDidUnload
{
    [self setBusyIndicator:nil];
    [self setCameraView:nil];
    [self setWebUIview:nil];
    [self setWidController:nil];
    [self setLoadingView:nil];
    [self setLoadinglabel:nil];
    [super viewDidUnload];
    
    NSURL * url = [NSURL URLWithString:@"about:blank"];
    
    //URL Requst Object
    NSMutableURLRequest *requestObj = [NSMutableURLRequest requestWithURL:url];
    [requestObj setCachePolicy:NSURLRequestReloadIgnoringLocalCacheData];
    urlRequest = requestObj;
    //Load the request in the UIWebView.
    [self.webView loadRequest:requestObj];
    
    [[NSURLCache sharedURLCache] removeAllCachedResponses];
    
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{    
//    NSString *currentUrl = self.webView.request.URL.absoluteString;
//    NSLog(@"current url : %@", currentUrl);
//
//    if (currentUrl != NULL) {
//        if (![kAddProfilePage rangeOfString:currentUrl].location == NSNotFound) {
//            return YES;
//        }
//        else {
//            return (interfaceOrientation == UIInterfaceOrientationPortrait);
//        }
//    }
//    
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
    
//    return YES;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

#pragma mark - NURLConnection delegate

- (void)connection:(NSURLConnection *)connection didReceiveAuthenticationChallenge:(NSURLAuthenticationChallenge *)challenge;
{
    NSLog(@"WebController Got auth challange via NSURLConnection");
    
    if ([challenge previousFailureCount] == 0)
    {
        authenticated = YES;
        
        NSURLCredential *credential = [NSURLCredential credentialForTrust:challenge.protectionSpace.serverTrust];
        
        [challenge.sender useCredential:credential forAuthenticationChallenge:challenge];
        
    } else
    {
        [[challenge sender] cancelAuthenticationChallenge:challenge];
    }
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response;
{
    NSLog(@"WebController received response via NSURLConnection");
    
    // remake a webview call now that authentication has passed ok.
    authenticated = YES;
    [self.webView loadRequest:urlRequest];
    
    // Cancel the URL connection otherwise we double up (webview + url connection, same url = no good!)
    [urlConnection cancel];
}

// We use this method is to accept an untrusted site which unfortunately we need to do, as our PVM servers are self signed.
- (BOOL)connection:(NSURLConnection *)connection canAuthenticateAgainstProtectionSpace:(NSURLProtectionSpace *)protectionSpace
{
    return [protectionSpace.authenticationMethod isEqualToString:NSURLAuthenticationMethodServerTrust];
}

#pragma mark - Webview delegate

// Note: This method is particularly important. As the server is using a self signed certificate,
// we cannot use just UIWebView - as it doesn't allow for using self-certs. Instead, we stop the
// request in this method below, create an NSURLConnection (which can allow self-certs via the delegate methods
// which UIWebView does not have), authenticate using NSURLConnection, then use another UIWebView to complete
// the loading and viewing of the page. See connection:didReceiveAuthenticationChallenge to see how this works.

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType
{
    
    NSString *requestString = [[[request URL] absoluteString] stringByReplacingPercentEscapesUsingEncoding: NSUTF8StringEncoding];
    NSLog(@"URL Request :%@", requestString);  
    NSLog(@"Did start loading: %@ auth:%d", [[request URL] absoluteString], authenticated);
    NSArray *arrayOfRequestString = [requestString componentsSeparatedByString:@"jsessionid="];
    if ([arrayOfRequestString count]>1) {
            authenticated = YES;
        NSURLRequest *tempurlRequest = [NSURLRequest requestWithURL:[[NSURL alloc] initWithString:@"file://localhost/Users/shyamgosavi/Documents/Projects/Crosspher/Crossper_mobile/offers_list.html"]];
        self.webView.delegate = self;
        urlRequest  = tempurlRequest;
        [self.webView loadRequest:urlRequest];

        return YES;
    }
        return YES;
}

- (void)webViewDidFinishLoad:(UIWebView *)webView
{
//    if (![[webView.request URL] isEqual:kLoginPage] || ![[webView.request URL] isEqual:kprofilepage] || ![[webView.request URL] isEqual:kServicespage] || ![[webView.request URL] isEqual:kSettingspage]) {
//        UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
//        
//        if (UIInterfaceOrientationIsLandscape(orientation))
//        {
////            self.webView.frame = self.view.frame;
//            NSLog(@"landscape %@", NSStringFromCGRect(self.webView.frame));
//        }
//        else
//        {
////            self.webView.frame = self.view.frame;
//            NSLog(@"portrait %@", NSStringFromCGRect(self.webView.frame));
//        }
//    }
    NSLog(@"URL %@ ",[webView.request URL]);
    if([[[webView.request URL] absoluteString] isEqualToString:@"file:///Users/shyamgosavi/Documents/Projects/Crosspher/Crossper_mobile/offers_list.html"])
    {
    NSURL *url=[NSURL URLWithString:[NSString stringWithFormat:@"%@%@",kBaseURL,kOffers]];
    NSData *data=[NSData dataWithContentsOfURL:url];
    NSString *responseData = [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
        
    NSLog(@" %@",responseData);
//        NSString *jsonDataSuccess = [NSString stringWithFormat:@"reloadPageWithJsonData('{\"Offers\" : [{\"offer_id\"  : 1,\"offer_name\" : \"Save 50%% on select baked goods!\",\"offer_reamingDays\" : 23,\"distance\" : \"200 miles\",\"logoURL\" : \"http://www.fondos7.net/wallpaper-original/wallpapers/taza-de-cafe-caliente-6177.jpg\"}],\"Offers2\" : \"shyam\" }');"];
    NSString *jsonDataSuccess = [NSString stringWithFormat:@"reloadPageWithJsonData('{\"serviceResultSuccess\": \"true\",\"statusMessage\": \"You have successfully completed this transaction.\",\"payKey\": \"%@\"}');",@"23456789"];
        
        NSError *error= nil;
        NSDictionary *response=[NSJSONSerialization JSONObjectWithData:data options: NSJSONReadingMutableContainers error:&error];
        NSLog(@"%@",jsonDataSuccess);
    [self.webView stringByEvaluatingJavaScriptFromString:jsonDataSuccess];
        
    }
//    NSDictionary *response=[NSJSONSerialization JSONObjectWithData:data options:
//                  NSJSONReadingMutableContainers error:&error];
//    
    [self.loadingView setHidden:TRUE];
    [self.busyIndicator stopAnimating];
    self.busyIndicator.hidden = YES;
    return [super webViewDidFinishLoad:webView];
}

- (void)webViewDidStartLoad:(UIWebView *)webView
{
    [self.loadingView setHidden:FALSE];
    self.busyIndicator.hidden = NO;
    [self.busyIndicator startAnimating];
//    NSLog(@"webViewDidStartLoad : %@", [webView.request URL]);
    return [super webViewDidStartLoad:webView];
}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error
{
//    NSLog(@"Request load failed with error : %@", [error localizedDescription]);
    [self.busyIndicator stopAnimating];
    self.busyIndicator.hidden = YES;
    [self.loadingView setHidden:TRUE];
    return [super webView:webView didFailLoadWithError:error];
}

- (IBAction)cameraButtonClicked:(id)sender
{
//    self.footerbar.hidden = FALSE;
    self.cameraView.hidden = FALSE;
    self.webUIview.hidden =TRUE ;
    [self ScannedPressed];
}

- (void)ScannedPressed
{
    ZXingWidgetController *tempwidController = [[ZXingWidgetController alloc] initWithDelegate:self showCancel:NO OneDMode:NO];
    
    NSMutableSet *readers = [[NSMutableSet alloc ] init];
    
    
    QRCodeReader* qrcodeReader = [[QRCodeReader alloc] init];
    [readers addObject:qrcodeReader];
    
    tempwidController.readers = readers;
    self.widController = tempwidController;
    
    [self.view addSubview:self.widController.view];
}
+ (NSString *)getMD5FromString:(NSString *)source{
	const char *src = [source UTF8String];
	unsigned char result[CC_MD5_DIGEST_LENGTH];
	CC_MD5(src, strlen(src), result);
    NSString *ret = [[NSString alloc] initWithFormat:@"%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X",
					  result[0], result[1], result[2], result[3],
					  result[4], result[5], result[6], result[7],
					  result[8], result[9], result[10], result[11],
					  result[12], result[13], result[14], result[15]
					  ];
    return [ret lowercaseString];
}

-(void)hideOtherViews
{
    [self.widController.view removeFromSuperview];
//    self.footerbar.hidden = TRUE;
    self.cameraView.hidden = TRUE;
    self.webUIview.hidden = FALSE;
}

- (void)zxingController:(ZXingWidgetController*)controller didScanResult:(NSString *)result
{
    NSURL *url = [NSURL URLWithString:result];
    if(!url)
        url = [NSURL URLWithString:@"about:blank"];
    [self hideOtherViews];
    //URL Requst Object
    NSMutableURLRequest *requestObj = [NSMutableURLRequest requestWithURL:url];
    
    if ([result hasPrefix:kBaseURL]) {
        
        //Load the request in the UIWebView.
//        result = [NSString stringWithFormat:@"%@&crossper=true",result];
//        url = [NSURL URLWithString:result];
        requestObj = [NSMutableURLRequest requestWithURL:url];
        self.urlRequest  = requestObj;
        [self.webView loadRequest:self.urlRequest];
        
    }
    else{
        [[[UIAlertView alloc] initWithTitle:@"Corssper" message:@"Unknown Coupon, Please scan valid QR code" delegate:self cancelButtonTitle:@"OK" otherButtonTitles: nil] show];
    }

}

- (void)zxingControllerDidCancel:(ZXingWidgetController*)controller
{
//    self.footerbar.hidden = TRUE;
    self.cameraView.hidden = TRUE;
    self.webUIview.hidden = FALSE;

    [self.widController.view removeFromSuperview];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    NSString *title = [alertView buttonTitleAtIndex:buttonIndex];
    if([title isEqualToString:@"OK"])
    {
        NSLog(@"unknown form");
        //        [self hideOtherViews];
        //         [self.widController.view removeFromSuperview];
        self.webUIview.hidden = TRUE;
        //        self.footerbar.hidden = FALSE;
        self.cameraView.hidden = FALSE;
        [self ScannedPressed];
    }
}



@end
