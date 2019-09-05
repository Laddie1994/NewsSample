package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.ViewGroup;

import com.example.myapplication.bean.AppDetail;
import com.example.myapplication.utils.UIUtils;

/**
 * Created by MBENBEN on 2016/2/23.
 */
public class GameDetailActivity extends AppDetailActivity {

    @Override
    public void initView() {
        super.initView();
        int size = UIUtils.dip2px(65);
        ViewGroup.LayoutParams params = mDetailHeander.mIvAvatar.getLayoutParams();
        params.height = size;
        params.width = size;
    }

    @Override
    public void showSimail(AppDetail.Similar similar) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constans.Detail.DETAIL_ID, similar.appid);
        Intent intent = new Intent(this, GameDetailActivity.class);
        intent.putExtras(bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.transation_in, R.anim.transation_out);
        startActivity(intent, options.toBundle());
    }
}
