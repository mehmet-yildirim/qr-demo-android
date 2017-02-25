package innova.com.tr.qrdemo;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.Arrays;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        scannerView = new ZXingScannerView(this);
        scannerView.setFormats(Arrays.asList(BarcodeFormat.QR_CODE));
        setContentView(scannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void handleResult(Result result) {
        Toast.makeText(this, String.format("Read: %s", result.getText()), Toast.LENGTH_SHORT).show();

        try {
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            ringtone.play();

            Vibrator v = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
            if(v.hasVibrator()) {
                v.vibrate(500);
            }
        } catch (Exception ignored) {

        }

        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        i.putExtra("qrCode", result.getText());
        startActivity(i);

        finish();
    }
}
