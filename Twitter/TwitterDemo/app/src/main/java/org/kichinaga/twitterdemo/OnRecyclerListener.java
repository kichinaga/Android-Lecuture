package org.kichinaga.twitterdemo;

import android.view.View;

/**
 * Created by kichinaga on 2016/11/06.
 *
 * RecyclerViewでクリック処理を実装するためのインターフェイス
 */

public interface OnRecyclerListener {
    void onRecyclerClicked(View view, int position);
}