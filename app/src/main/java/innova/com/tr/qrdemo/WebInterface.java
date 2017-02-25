package innova.com.tr.qrdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

/**
 * Created by mehmet on 25.02.2017.
 */

public class WebInterface{

    final Context context;


    public WebInterface(Context context) {
        this.context = context;
    }

     @JavascriptInterface
    public void showQROptions() {
         Intent i = new Intent(context, MainActivity.class);
         i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
         i.putExtra("showQROptions", true);
         context.startActivity(i);
    }

}
