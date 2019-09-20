package com.ajoyajoya.movieliciousv2.widgetmovie;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetTvService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteTvViewsFactory(this.getApplicationContext());
    }
}
