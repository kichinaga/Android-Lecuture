package org.kichinaga.twitterdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by kichinaga on 2016/11/07.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final AccessToken accessToken = TwitterAuthUtil.loadAccessToken();
        if (accessToken != null) {
            /*
              もとからアクセストークンが保存されていた場合や、
              認証成功した場合にここを通る。
              MainActivityに移動する
             */
            TwitterFactory.getSingleton().setOAuthAccessToken(accessToken);
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        final Button button = (Button) findViewById(R.id.login);
        //noinspection ConstantConditions
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeLogin();
            }
        });
    }

    //非同期ログイン処理
    private void executeLogin() {
        new AsyncTask<Void, Throwable, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TwitterAuthUtil.clearTokens();
                final Twitter twitter = TwitterFactory.getSingleton();

                try {
                    final RequestToken requestToken = twitter.getOAuthRequestToken(
                            TwitterAuthUtil.getCallbackUrlString());
                    //OAuth認証のためにURLをセット
                    final String authUrl = requestToken.getAuthorizationURL();
                    TwitterAuthUtil.saveRequestToken(requestToken);
                    //ブラウザからログインさせる
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));

                } catch (TwitterException e) {
                    //ログイン処理で何らかのエラーが起きた時の処理
                    Log.e("Twitter", "通信でエラーが発生しました: " + e.getMessage());
                    publishProgress(e);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Throwable... values) {
                super.onProgressUpdate(values);

                Toast.makeText(LoginActivity.this,
                        "通信でエラーが発生しました: " + values[0].getMessage(), Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}
