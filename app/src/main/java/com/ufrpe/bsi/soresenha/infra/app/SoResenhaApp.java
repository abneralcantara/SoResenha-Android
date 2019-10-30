package com.ufrpe.bsi.soresenha.infra.app;

import android.app.Application;
import android.content.Context;

public class SoResenhaApp extends Application {
    private android.content.Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public Context getContext(){
        return context;
    }
}
