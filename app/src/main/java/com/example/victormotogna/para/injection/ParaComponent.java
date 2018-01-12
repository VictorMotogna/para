package com.example.victormotogna.para.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by victormotogna on 1/12/18.
 */

@Singleton
@Component(modules = {ParaModule.class})
public interface ParaComponent extends ParaGraph {
    final class Initializer {
        public Initializer() {

        }

        public static ParaComponent init(Context context) {
            return DaggerParaComponent.builder()
                    .paraModule(new ParaModule(context)).build();
        }
    }
}
