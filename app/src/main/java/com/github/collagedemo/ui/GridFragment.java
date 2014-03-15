package com.github.collagedemo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.collagedemo.ImageAdapter;
import com.github.collagedemo.R;
import com.github.collagedemo.model.Media;
import com.github.collagedemo.tasks.MakeCollageTask;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sergey Ryabov
 *         Date: 13.03.14
 */
public class GridFragment extends BaseFragment {

    private static final String ARG_IMAGES = "images";

    @Inject Picasso picasso;

    @InjectView(R.id.gridview) GridView gridView;

    private ImageAdapter adapter;

    private List<Media> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ImageAdapter(getActivity(), picasso);
        gridView.setAdapter(adapter);

        gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            images = Arrays.asList((Media[]) getArguments().getSerializable(ARG_IMAGES));
            adapter.setItems(images);
        }
    }

    @OnClick(R.id.btn_make_collage)
    void onMakeCollageClick(View v) {
        final long[] itemIds = gridView.getCheckedItemIds();
        if (itemIds.length < 2 || itemIds.length > 6) {
            Toast.makeText(getActivity(), R.string.wrong_selection, Toast.LENGTH_SHORT).show();
        } else {
            makeCollage(filterImages(adapter.getItems(), itemIds));
        }
    }

    private void makeCollage(List<Media> medias) {

        new MyMakeCollageTask().execute(medias.toArray(new Media[medias.size()]));

    }

    private List<Media> filterImages(List<Media> items, long[] itemIds) {
        final List<Media> result = new ArrayList<>();

        for (long idx : itemIds) {
            result.add(items.get((int) idx));
        }

        return result;
    }

    private void goToCollage(Bitmap bitmap) {
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, CollageFragment.newInstance(bitmap))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    public static Fragment newInstance(List<Media> images) {
        final GridFragment f = new GridFragment();
        final Bundle args = new Bundle(1);
        args.putSerializable(ARG_IMAGES, images.toArray(new Media[images.size()]));
        f.setArguments(args);
        return f;
    }

    public class MyMakeCollageTask extends MakeCollageTask {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (getActivity() == null) return;

            if (bitmap == null) {
                Toast.makeText(getActivity(), R.string.error_making_collage, Toast.LENGTH_SHORT).show();
            } else {
                goToCollage(bitmap);
            }
        }
    }
}
