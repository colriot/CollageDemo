package com.github.collagedemo.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new InputFragment())
                    .commit();
        }
    }
}
