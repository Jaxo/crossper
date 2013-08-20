package com.crossper;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import com.crossper.Constants;
/**
 * @author shyamgosavi
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity{// implements CordovaInterface{

	Button scanBarCode,buttonfacebook;
	
	
//	  CordovaWebView webView;
	 	private WebView webView;
	  private String TAG = "Crossper";
	  private ValueCallback<Uri> mUploadMessage;  
	  private final static int FILECHOOSER_RESULTCODE=1;  
	  private ProgressDialog dialog; 
	  boolean loadingFinished = true;
      boolean redirect = false;
      
	  public String makeSHA1Hash(String input)
	        throws NoSuchAlgorithmException
	    {
	        MessageDigest md = MessageDigest.getInstance("SHA1");
	        md.reset();
	        byte[] buffer = input.getBytes();
	        md.update(buffer);
	        byte[] digest = md.digest();

	        String hexStr = "";
	        for (int i = 0; i < digest.length; i++) {
	            hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	        }
	        return hexStr;
	    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.i("Shyam", "onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
//	        webView = (CordovaWebView) findViewById(R.id.webView);
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
       
//      webView = new WebView(this);  
        webView.getSettings().setJavaScriptEnabled(true);
      webView.getSettings().setAllowFileAccess(true);

      webView.setWebViewClient(new myWebClient());  
      webView.setWebChromeClient((new WebChromeClient()  
      {  
    	 
             public void openFileChooser(ValueCallback<Uri> uploadMsg) {  
               
              mUploadMessage = uploadMsg;  
              Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
              i.addCategory(Intent.CATEGORY_OPENABLE);  
              i.setType("image/*");  
              MainActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);  
         
             }  
             
             public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType) {
              mUploadMessage = uploadFile;  
               Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
               i.addCategory(Intent.CATEGORY_OPENABLE);  
               i.setType("image/*");  
               MainActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);
             }
             
             public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
              mUploadMessage = uploadFile;  
               Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
               i.addCategory(Intent.CATEGORY_OPENABLE);  
               i.setType("image/*");  
               MainActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);
             }
      }));  
      String urlToLoad = Constants.WELCOME_PAGE;
      webView.loadUrl(urlToLoad);
//      webView.loadUrl("");
//      startScanning();
   }
    /**
     * Method to show progress bar
     */
    private void showProgressBar() {
     if (dialog == null || !dialog.isShowing()) {
      try {
       dialog = ProgressDialog.show(this, "", "Loading... ", true,true);//, new OnCancelListener() {
//        public void onCancel(DialogInterface dialog) {
//         webView.stopLoading();
//        }
//       });
      } catch (Exception e) {
       e.printStackTrace();
      }
     }
    }

    /**
     * Method to cancel progress bar
     */
    private void cancelProgressBar() {
     if (dialog != null) {
      dialog.dismiss();
      dialog = null;
     }
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            showProgressBar();
            Log.i(TAG, url);
            if(url.contains("http://www.crossperapp.com/"))
            {
            	//starting scanner
            	Log.i(TAG, "Starting scanner"+url);
            }
            else{
            	Log.i(TAG, "Loading url");
            }
            	
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            if(url.contains("http://www.crossperapp.com/"))
            {
            	Log.i(TAG, "shouldOverrideUrlLoading"+url);
            	webView.stopLoading();
            	startScanning();
            	return false;
            }
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            cancelProgressBar();
//            progressBar.setVisibility(View.GONE);
        }
    }

    //flipscreen not loading again
    @Override
    public void onConfigurationChanged(Configuration newConfig){        
       super.onConfigurationChanged(newConfig);
    }
      public void startScanning()
      {
    	  Log.i(TAG,"startScanning launching scanner");
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
      }
      
      


	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
//		 Session.getActiveSession().onActivityResult(this, requestCode, resultCode, intent);
		Log.i("Shyam", "scan succcessfully--"+requestCode+" FILECHOOSER_RESULTCODE "+FILECHOOSER_RESULTCODE);
		//super.onActivityResult(requestCode, resultCode, intent);
		
		if(requestCode==FILECHOOSER_RESULTCODE)  
		   {  
		    if (null == mUploadMessage) return;  
		             Uri result = intent == null || resultCode != RESULT_OK ? null  
		                     : intent.getData();  
		             mUploadMessage.onReceiveValue(result);  
		             mUploadMessage = null;  
		               
		   }  
		
		
		if (requestCode == 0) {
			Log.i("Shyam", "inside onactivity result with request code = 0...");
			if (resultCode == RESULT_OK) {
				
				if(intent.hasExtra(Constants.EXTRA_URL)){
					String url = intent.getStringExtra(Constants.EXTRA_URL);
					Log.i("Shyam","Extra_URL "+ url);
					String jsFuncation = "javascript:downloadOfferForPromoter('"+url+"');";
					Log.i("Shyam", "jsFuntion "+jsFuncation);
					webView.loadUrl(jsFuncation);
				} else {
					String contents = intent.getStringExtra("SCAN_RESULT");
					Log.i("Shyam", "contents: " + contents);
					// Handle successful scan
					if (URLUtil.isValidUrl(contents)) {
//						if (contents.contains(Constants.FORM_ID)) {
//							
//						} else {
//					
//						}

					} else {
					
						Log.i("Invalide data", "Barcode has invalide data");
						
					}
				}
			}
	}
    

	}

}
