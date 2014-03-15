package com.github.collagedemo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.collagedemo.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Sergey Ryabov
 *         Date: 13.03.14
 */
public class CollageFragment extends BaseFragment {

    private static final String TAG = "InstaCollage";

    private static final String ARG_BITMAP = "bitmap";

    private static final String PROGRESS_DIALOG_TAG = "progress-tag";
    private static final String TMP_COLLAGE_FILENAME = "collage.png";

    @InjectView(R.id.result) ImageView resultView;

    private Bitmap bitmap;

    private ProgressDialogFragment progressDialog= new ProgressDialogFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            bitmap = getArguments().getParcelable(ARG_BITMAP);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collage, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resultView.setImageBitmap(bitmap);
    }

    @OnClick(R.id.btn_share)
    void onShareClick() {
        if (bitmap == null) return;

        final File imageFile = new File(getActivity().getCacheDir(), TMP_COLLAGE_FILENAME);
        imageFile.setReadable(true, false);


        progressDialog.show(getFragmentManager(), PROGRESS_DIALOG_TAG);

        new AsyncTask<Void, Void, Uri>() {
            @Override
            protected Uri doInBackground(Void... params) {
                try {
                    if (!imageFile.exists())
                        imageFile.createNewFile();
                    final FileOutputStream fos = new FileOutputStream(imageFile);
                    final BufferedOutputStream bos = new BufferedOutputStream(fos);

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    bos.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error", e);
                    return null;
                }

                return Uri.fromFile(imageFile);
            }

            @Override
            protected void onPostExecute(Uri uri) {
                final DialogFragment fr =
                        (DialogFragment) getFragmentManager().findFragmentByTag(PROGRESS_DIALOG_TAG);

                if (fr != null) {
                    fr.dismiss();
                }

                shareImage(uri);
            }
        }.execute();

    }

    private void shareImage(Uri imageUri) {
        final Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_STREAM, imageUri);
        i.setType("image/*");
        startActivity(i);
    }

    public static Fragment newInstance(Bitmap bitmap) {
        final CollageFragment f = new CollageFragment();
        final Bundle args = new Bundle(1);
        args.putParcelable(ARG_BITMAP, bitmap);
        f.setArguments(args);
        return f;
    }
}
