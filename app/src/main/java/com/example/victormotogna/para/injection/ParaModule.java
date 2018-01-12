package com.example.victormotogna.para.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by victormotogna on 1/12/18.
 */

@Module
public class ParaModule {
    private Context context;

    public ParaModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
