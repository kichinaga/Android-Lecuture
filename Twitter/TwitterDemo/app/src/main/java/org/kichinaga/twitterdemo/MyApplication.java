package org.kichinaga.twitterdemo;

import android.app.Application;

import twitter4j.TwitterFactory;

/**
 * Created by kichinaga on 2016/11/07.
 */

public class MyApplication extends Application {

    private final String CONSUMER_KEY = "obcX9RzMu9JkfPkxJyicsr6nn";
    private final String CONSUMER_SERCRET_KEY = "WbmfSE3MgcLltfn4VZXrvQlFxpHvjmytYLlFw3I6FytzEqWELt";

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
