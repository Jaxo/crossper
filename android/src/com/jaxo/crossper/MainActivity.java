package com.jaxo.crossper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
   IntentIntegrator zxing;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      zxing = new IntentIntegrator(this);
      Button decodeButton = ((Button)findViewById(R.id.btn_scan));
      decodeButton.setOnClickListener(
         new Button.OnClickListener() {
            public void onClick(View v) {
               zxing.setTitleByID(R.string.app_name);
               zxing.setTargetApplications(IntentIntegrator.TARGET_BARCODE_SCANNER_ONLY);
               zxing.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
         }
      );
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

   public void onActivityResult(int requestCode, int resultCode, Intent intent) {
      IntentResult scanResult = IntentIntegrator.parseActivityResult(
         requestCode, resultCode, intent
      );
      if (scanResult != null) {
         String contents = scanResult.getContents();
         Toast.makeText(
            getApplicationContext(),
            contents,
            Toast.LENGTH_LONG
         ).show();
         startActivity(new Intent(getBaseContext(), WebViewActivity.class));
      }
   }
}
