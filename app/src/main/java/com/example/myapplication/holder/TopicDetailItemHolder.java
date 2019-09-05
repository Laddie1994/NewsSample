package com.example.myapplication.holder;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseViewHolder;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.manager.ImageLoaderManager;
import com.example.myapplication.utils.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by MBENBEN on 2016/2/25.
 */
public class TopicDetailItemHolder extends BaseViewHolder<AppInfo> {

    TextView title, size, tag;
    Button download;
    ImageView avatar;
    FrameLayout roundedFrame;

    public TopicDetailItemHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = mInflater.inflate(R.layout.game_list_item, null);
        title = (TextView) view.findViewById(R.id.game_item_title);
        size = (TextView) view.findViewById(R.id.game_item_size);
        roundedFrame = (FrameLayout) view.findViewById(R.id.game_item_rounded_frame);
        download = (Button) view.findViewById(R.id.game_item_btn_download);
        avatar = (ImageView) view.findViewById(R.id.game_item_avatar);
        tag = (TextView) view.findViewById(R.id.game_item_tag);
        return view;
    }

    @Override
    public void initData() {
        String category = mData.categoryname;
        if ("角色扮演".equals(category)) {
            tag.setTextColor(UIUtils.getColor(R.color.fallow_color));
            roundedFrame.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.rounded_image_fallow));
        } else if ("动作射击".equals(category)) {
            tag.setTextColor(UIUtils.getColor(R.color.motion_color));
            roundedFrame.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.rounded_image_motion));
        } else if ("休闲益智".equals(category)) {
            tag.setTextColor(UIUtils.getColor(R.color.role_color));
            roundedFrame.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.rounded_image_role));
        }
        ImageLoader.getInstance().displayImage(mData.applogo, avatar,
                ImageLoaderManager.getInstance(mContext).getDisplayOptions(12));
        title.setText(mData.apptitle);
        size.setText(mData.appsize + "M");
        tag.setText(mData.categoryname);
    }
}
