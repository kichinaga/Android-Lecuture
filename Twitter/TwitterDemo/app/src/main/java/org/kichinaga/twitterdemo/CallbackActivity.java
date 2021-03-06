package org.kichinaga.twitterdemo;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by kichinaga on 2016/11/07.
 *
 * ブラウザからの結果を受け取る画面
 */

public class CallbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);

        final Uri uri = getIntent().getData();
        if (uri == null || !uri.toString().startsWith(TwitterAuthUtil.getCallbackUrlString())) {
            Toast.makeText(this, "unexpected Uri in Intent: " + uri, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        final String verifier = uri.getQueryParameter("oauth_verifier");

        final RequestToken requestToken = TwitterAuthUtil.loadRequestToken();
        TwitterAuthUtil.clearTokens();
        if (requestToken == null) {
            Toast.makeText(this, "RequestTokenのロードに失敗しました。", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        new AsyncTask<Void, Throwable, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    //ログインが成功していたらLoginActivityを通ってMainActivityに移動
                    final Twitter twitter = TwitterFactory.getSingleton();

                    final AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                    TwitterAuthUtil.saveAccessToken(accessToken);
                    startMainActivity();
                } catch (TwitterException e) {
                    Log.e("RealmTwitter", "通信でエラーが発生しました: " + e.getMessage());
                    publishProgress(e);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Throwable... values) {
                super.onProgressUpdate(values);

                Toast.makeText(CallbackActivity.this,
                        "通信でエラーが発生しました: " + values[0].getMessage(), Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    private void startMainActivity() {
        final Intent intent = new Intent(CallbackActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
