package com.ufrpe.bsi.soresenha.infra.app;

import android.app.Application;
import android.content.Context;

public class SoResenhaApp extends Application {
    private static android.content.Context Context;

    @Override
    public void onCreate() {
        super.onCreate();
        Context = this;
    }

    public static Context getContext(){
        return Context;
    }
}
