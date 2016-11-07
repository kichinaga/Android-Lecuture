package org.kichinaga.twitterdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kichinaga on 2016/11/06.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Tweet> mData;             //表示するデータのリスト
    private LayoutInflater mInflater;       //MyViewHolderとitem_view.xmlを紐づける変数
    private OnRecyclerListener mListener;   //クリック処理を実装するためのリスナ
    private Context mContext;

    //コンストラクタ
    public MyAdapter(Context context, List<Tweet> data,OnRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;
        mContext = context;
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
            holder.screenName.setText(mData.get(position).getScreenName());
            holder.text.setText(mData.get(position).getText());
            //Picassoを使ってアイコン画像をセット
            Picasso.with(mContext)
                    .load(mData.get(position).getIconUrl())
                    .fit()
                    .into(holder.icon);
        }

        holder.text.setOnClickListener(new View.OnClickListener() {
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

        TextView screenName;
        TextView text;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            //リソースIDと紐づけ
            screenName = (TextView) itemView.findViewById(R.id.screen_name);
            text = ((TextView) itemView.findViewById(R.id.text));
            icon = ((ImageView) itemView.findViewById(R.id.image));
        }
    }
}
