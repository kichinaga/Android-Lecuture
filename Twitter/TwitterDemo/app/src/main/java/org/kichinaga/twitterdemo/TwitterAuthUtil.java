package org.kichinaga.twitterdemo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by kichinaga on 2016/11/07.
 *
 * 認証に必要な情報をSharedPreferencesに保存する等のサポートクラス
 */

public final class TwitterAuthUtil {
    private static Application app;

    //SharedPreferenceのキートークン
    private static final String PREF_NAME = "auth";
    private static final String KEY_REQUEST_TOKEN = "requestToken";
    private static final String KEY_REQUEST_TOKEN_SECRET = "requestTokenSecret";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_ACCESS_TOKEN_SECRET = "accessTokenSecret";
    private static final String KEY_ACCESS_USER = "accessUser";

    public static void init(Application app) {
        TwitterAuthUtil.app = app;
    }

    //コールバックURLの設定
    public static String getCallbackUrlString() {
        return app.getPackageName() + ".callback://CallBackActivity";
    }

    //Twitterのリクエストトークンを保存
    public synchronized static void saveRequestToken(RequestToken requestToken) {
        getPreference().edit()
                .putString(KEY_REQUEST_TOKEN, requestToken.getToken())
                .putString(KEY_REQUEST_TOKEN_SECRET, requestToken.getTokenSecret())
                .apply();
    }

    //保存されてるリクエストトークンを入手
    public synchronized static RequestToken loadRequestToken() {
        final SharedPreferences pref = getPreference();

        final String token = pref.getString(KEY_REQUEST_TOKEN, null);
        final String secret = pref.getString(KEY_REQUEST_TOKEN_SECRET, null);
        if (token == null || secret == null) {
            return null;
        }
        return new RequestToken(token, secret);
    }

    //Twitterのアクセストークンを保存
    public synchronized static void saveAccessToken(AccessToken accessToken) {
        getPreference().edit()
                .putString(KEY_ACCESS_TOKEN, accessToken.getToken())
                .putString(KEY_ACCESS_TOKEN_SECRET, accessToken.getTokenSecret())
                .putLong(KEY_ACCESS_USER, accessToken.getUserId())
                .apply();
    }

    //保存されているアクセストークンを入手
    public synchronized static AccessToken loadAccessToken() {
        final SharedPreferences pref = getPreference();

        final String token = pref.getString(KEY_ACCESS_TOKEN, null);
        final String secret = pref.getString(KEY_ACCESS_TOKEN_SECRET, null);
        if (token == null || secret == null) {
            return null;
        }
        return new AccessToken(token, secret, pref.getLong(KEY_ACCESS_USER, -1L));
    }

    //保存されているトークンを全て削除
    public synchronized static void clearTokens() {
        getPreference().edit()
                .clear()
                .apply();
    }

    //SharedPreferences情報を入手
    private static SharedPreferences getPreference() {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}