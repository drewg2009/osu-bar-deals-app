package mobileapps.osubardeals.osubardealsapp.Utilities;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by drewgallagher on 3/9/18.
 */

public class DialogHelper {
    public static void showDialog(Context c, String msg, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(msg)
                .setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
