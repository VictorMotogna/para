package com.example.victormotogna.para.injection;

import android.app.Application;

/**
 * Created by victormotogna on 1/12/18.
 */

public class ParaApp extends Application {
    private static ParaGraph graph;

    public static ParaGraph component() {
        return graph;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        graph = ParaComponent.Initializer.init(this);
    }
}
