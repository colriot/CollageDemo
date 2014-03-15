package com.github.collagedemo.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.github.collagedemo.R;

/**
 * @author Sergey Ryabov
 *         Date: 13.03.14
 */
public class ProgressDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setMessage(getString(R.string.saving));
        dialog.setCancelable(false);
        return dialog;
    }
}
