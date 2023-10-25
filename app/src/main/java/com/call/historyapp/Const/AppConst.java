package com.call.historyapp.Const;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Build;

import com.call.historyapp.R;
import com.karumi.dexter.PermissionToken;

import androidx.annotation.RequiresApi;

public class AppConst {
    public static void showSettingsDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permissions to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings(activity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showPermissionDialog(final Activity activity, final PermissionToken permissionToken) {
        new AlertDialog.Builder(activity
        ).setMessage(R.string.MSG_ASK_PERMISSION).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.cancelPermissionRequest();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                permissionToken.continuePermissionRequest();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                permissionToken.cancelPermissionRequest();
            }
        }).show();
    }

    private static void openSettings(Activity activity) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivityForResult(intent, 101);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String GetStorage(double strTotalRam) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double d2 = strTotalRam / 1048576.0d;
        double d3 = strTotalRam / 1.073741824E9d;
        double d4 = strTotalRam / 1.099511627776E12d;
        if (d4 > 1.0d) {
            return decimalFormat.format(d4).concat(" TB");
        }
        if (d3 > 1.0d) {
            return decimalFormat.format(d3).concat(" GB");
        }
        if (d2 > 1.0d) {
            return decimalFormat.format(d2).concat(" MB");
        }
        return decimalFormat.format(strTotalRam).concat(" KB");
    }
}
