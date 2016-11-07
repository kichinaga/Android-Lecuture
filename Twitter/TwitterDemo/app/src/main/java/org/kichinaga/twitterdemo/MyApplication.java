package org.kichinaga.twitterdemo;

import android.app.Application;

import twitter4j.TwitterFactory;

/**
 * Created by kichinaga on 2016/11/07.
 */

public class MyApplication extends Application {

    //自分で入手したキーを入れてください
    private final String CONSUMER_KEY = "<consumer-key>";
    private final String CONSUMER_SERCRET_KEY = "<consumer-sercret-key>";

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthUtil.init(this);

        TwitterFactory.getSingleton().setOAuthConsumer(
                CONSUMER_KEY,
                CONSUMER_SERCRET_KEY
        );
    }
}
