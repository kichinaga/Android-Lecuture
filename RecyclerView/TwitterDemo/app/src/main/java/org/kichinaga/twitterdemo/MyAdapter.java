package org.kichinaga.twitterdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kichinaga on 2016/11/06.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<String> mData;             //表示するデータのリスト
    private LayoutInflater mInflater;       //MyViewHolderとitem_view.xmlを紐づける変数
    private OnRecyclerListener mListener;   //クリック処理を実装するためのリスナ

    //コンストラクタ
    public MyAdapter(Context context, List<String> data,OnRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;
    }

    //ViewHolderとレイアウト(item_view.xml)を紐づける
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_view, parent, false));
    }

    //データをMyViewHolderを通してitem_view.xmlにセット
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (!mData.isEmpty()) {
            //viewにデータをセット
            holder.textView.setText(mData.get(position));
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRecyclerClicked(view,position);
            }
        });
    }

    //データの数を返すメソッド
    @Override
    public int getItemCount() {
        if (!mData.isEmpty()) return mData.size();
        else return 0;
    }

    //自作ViewHolderクラス
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
