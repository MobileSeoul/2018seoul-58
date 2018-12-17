package com.walk.angel.angelwalk;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;

public class UILocker {
    private Dialog innderDialog;

    public UILocker(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            innderDialog = new SuspendDialog(context);
        else
            innderDialog = new SuspendDialog(context, true);

    }

    public boolean lock() {
        if (innderDialog.isShowing()) {
            return false;
        }
        innderDialog.show();
        return true;
    }

    public boolean isLocked() {
        return innderDialog.isShowing();
    }

    public boolean unlock() {
        if (!innderDialog.isShowing()) {
            return false;
        }
        innderDialog.dismiss();
        return true;
    }

    class SuspendDialog extends Dialog {
        public SuspendDialog(Context context) {
            super(context, android.R.style.Theme_Translucent_NoTitleBar);
            setContentView(R.layout.loading_dialog);
        }

        public SuspendDialog(Context context, boolean isAvailableHoloUi) {
            super(
                    context,
                    android.R.style.Theme_Holo_Light_Panel);

            setContentView(R.layout.loading_dialog);
        }

        @Override
        public void onBackPressed() {
        }
    }
}