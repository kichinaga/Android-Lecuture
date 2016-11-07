package org.kichinaga.twitterdemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private List<Tweet> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerViewの設定
        mRecyclerView = ((RecyclerView) findViewById(R.id.recycler_view));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        //タイムラインデータの入手
        executeGetTweet();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                executeGetTweet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void executeGetTweet(){
        new AsyncTask<Void,Void,List<Tweet>>(){

            @Override
            protected List<Tweet> doInBackground(Void... params) {
                final Twitter twitter = TwitterFactory.getSingleton();
                final ResponseList<twitter4j.Status> homeTimeLine;
                final List<Tweet> list = new ArrayList<>();

                try {
                    //タイムラインのTweetを入手
                    homeTimeLine = twitter.getHomeTimeline();
                } catch (TwitterException e) {
                    //エラーが起きたとき
                    Toast.makeText(MainActivity.this, "通信でエラーが発生しました: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("TwitterDemo", "通信でエラーが発生しました。", e);
                    return null;
                }

                for (twitter4j.Status status: homeTimeLine) {
                    //入手したタイムラインのTweetをリストに格納
                    final Tweet tweet = new Tweet(status);
                    list.add(tweet);
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<Tweet> tweets) {
                super.onPostExecute(tweets);

                if (null != tweets){
                    //Tweetを入手できた時の処理
                    mData = tweets;

                    //入手したタイムラインTweetのリストをAdapterにセット
                    mAdapter = new MyAdapter(MainActivity.this, mData, new OnRecyclerListener() {
                        @Override
                        public void onRecyclerClicked(View view, int position) {
                            Toast.makeText(MainActivity.this,((TextView)view).getText().toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    //RecyclerViewにセット
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }.execute();
    }
}
