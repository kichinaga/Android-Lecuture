package org.kichinaga.twitterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //表示するデータの最大件数
    private final int MAX = 30;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = ((RecyclerView) findViewById(R.id.recycler_view));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //表示するデータの作成
        List<String> data = new ArrayList<>();
        for (int i = 0; i < MAX; i++) {
            data.add("Item : " + String.valueOf(i));
        }

        //RecyclerViewにアダプターをセット
        mAdapter = new MyAdapter(this, data, new OnRecyclerListener() {
            @Override
            public void onRecyclerClicked(View view, int position) {
                Toast.makeText(MainActivity.this,((TextView)view).getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
