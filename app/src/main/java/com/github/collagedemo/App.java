package com.github.collagedemo;

import android.app.Application;
import dagger.ObjectGraph;

/**
 * @author Sergey Ryabov
 *         Date: 12.03.14
 */
public class App extends Application {

    private static App INSTANCE;
    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        buildGraphAndInject();
    }

    private void buildGraphAndInject() {
        objectGraph = ObjectGraph.create(getModules());
        objectGraph.inject(this);
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    public static App get() {
        return INSTANCE;
    }

    private Object[] getModules() {
        return new Object[] { new RootModule(this) };
    }
}
