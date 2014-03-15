package com.github.collagedemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.collagedemo.InstagramApi;
import com.github.collagedemo.R;
import com.github.collagedemo.model.IGResponse;
import com.github.collagedemo.model.Media;
import com.github.collagedemo.model.User;
import com.github.collagedemo.operators.IGResponseToInnerData;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

import javax.inject.Inject;
import java.util.List;

import static rx.android.observables.AndroidObservable.fromFragment;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
public class InputFragment extends BaseFragment {
    private static final String TAG = "InstaCollage";

    @Inject InstagramApi api;

    @InjectView(R.id.edit_username) EditText usernameView;
    @InjectView(R.id.progress) ProgressBar progressView;
    @InjectView(R.id.error_text) TextView errorView;

    private Subscription request = Subscriptions.empty();

    private Observable<List<Media>> imagesStream;

    private Observer<List<Media>> observer = new Observer<List<Media>>() {
        @Override
        public void onCompleted() {
            Log.i(TAG, "FINISHED");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, String.valueOf(e));

            progressView.setVisibility(View.GONE);
            errorView.setText(getString(R.string.error_format, convertErrorToMessage(e)));
            errorView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onNext(List<Media> images) {
            Log.i(TAG, String.valueOf(images));
            progressView.setVisibility(View.GONE);

            goToGrid(images);
        }
    };

    private String convertErrorToMessage(Throwable e) {
        String msg = e.getMessage();
        if (e instanceof RetrofitError) {
            final RetrofitError re = (RetrofitError) e;
            if (re.isNetworkError()) {
                msg = getString(R.string.network_error);
            } else if (re.getResponse() != null) {
                final IGResponse<?> body = (IGResponse<?>) re.getBody();
                msg = body != null ? body.getMeta().getErrorMessage() : getString(R.string.unknown_error);
            }
        }
        return msg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (imagesStream != null) {
            imagesStream.subscribe(observer);
        }
    }

    @Override
    public void onDestroyView() {
        request.unsubscribe();
        super.onDestroyView();
    }

    @OnClick({ R.id.btn_get_collage, R.id.error_text })
    void onGetCollageClick(View v) {
        progressView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        hideKeyboard();

        request.unsubscribe();

        final String username = String.valueOf(usernameView.getText());
        imagesStream = getImageListRequest(username);
        request = fromFragment(this, imagesStream).subscribe(observer);
    }

    private Observable<List<Media>> getImageListRequest(String username) {
        return api.searchUser(username)
                .flatMap(new IGResponseToInnerData<User>())
                .first()
                .flatMap(new Func1<User, Observable<IGResponse<Media>>>() {
                    @Override
                    public Observable<IGResponse<Media>> call(User user) {
                        return api.getRecentMedia(user.getId());
                    }
                })
                .flatMap(new IGResponseToInnerData<Media>())
                .filter(new Func1<Media, Boolean>() {
                    @Override
                    public Boolean call(Media media) {
                        return media.isImage();
                    }
                })
                .toList()
                .cache();
    }

    private void goToGrid(List<Media> images) {
        imagesStream = null;
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, GridFragment.newInstance(images))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usernameView.getWindowToken(), 0);
    }
}
