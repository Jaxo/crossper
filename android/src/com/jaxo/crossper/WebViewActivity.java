package com.jaxo.crossper;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
   private WebView webView;
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.webview);
      webView = (WebView)findViewById(R.id.webView1);
      webView.getSettings().setJavaScriptEnabled(true);
      webView.loadUrl(
         "http://ec2-54-215-172-148.us-west-1.compute.amazonaws.com:8080/crossper/mobile/"
      );
   }
}
