package innova.com.tr.qrdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebInterface(this), "Android");

        webView.loadUrl("https://qr-generator-demo.herokuapp.com/");

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle extras = intent.getExtras();

        if (extras != null && extras.getBoolean("showQROptions")) {
            showQRAlertDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(ad != null && ad.isShowing()) {
            ad.dismiss();
        }

        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                webView.loadUrl(String.format("https://qr-generator-demo.herokuapp.com/read/%s", data.getExtras().getString("qrCode", "NO DATA PASSED")));
            }
        }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.qrcode) {

            showQRAlertDialog();

//            startActivity(new Intent(this, QRScannerActivity.class));
//            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showQRAlertDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.qrcode_alertdialog);
        adb.setIcon(R.drawable.qrcode_black);

        CharSequence[] options = new CharSequence[]{getString(R.string.qrcode_alert_receive), getString(R.string.qrcode_alert_send)};

        adb.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivityForResult(new Intent(getApplicationContext(), QRScannerActivity.class), 1);
                        dialog.cancel();
                        break;
                    case 1:
                        webView.loadUrl("https://qr-generator-demo.herokuapp.com/generate");
                        break;
                }

            }
        });
        ad = adb.show();
    }
}
