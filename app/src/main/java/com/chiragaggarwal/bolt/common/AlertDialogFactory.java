package com.chiragaggarwal.bolt.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.chiragaggarwal.bolt.R;

public class AlertDialogFactory {
    private static AlertDialog permissionRequiredAlertDialog;

    public static AlertDialog getPermissionRequiredAlertDialog(Context context,
                                                               DialogInterface.OnClickListener onNegativeButtonClickListener,
                                                               DialogInterface.OnClickListener onPositiveButtonClickListener) {
        if (permissionRequiredAlertDialog == null)
            permissionRequiredAlertDialog = new AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.title_location_permission_required))
                    .setMessage(context.getString(R.string.message_location_permission_required))
                    .setNegativeButton(context.getString(R.string.exit), onNegativeButtonClickListener)
                    .setPositiveButton(context.getString(R.string.ok), onPositiveButtonClickListener)
                    .setCancelable(false)
                    .create();
        return permissionRequiredAlertDialog;
    }

    public static AlertDialog getEnableGpsDialog(Context context,
                                                 DialogInterface.OnClickListener onNegativeButtonClickListener,
                                                 final DialogInterface.OnClickListener onPositiveButtonClickListener) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.title_location_services_disabled))
                .setMessage(context.getString(R.string.message_location_permission_disabled))
                .setPositiveButton(context.getString(R.string.enable), onPositiveButtonClickListener)
                .setNegativeButton(context.getString(R.string.cancel), onNegativeButtonClickListener)
                .setCancelable(false)
                .create();
    }
}
