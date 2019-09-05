package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapter.OnRecyclerViewItemClickListener;
import com.example.myapplication.bean.AppInfo;
import com.example.myapplication.bean.TopicDetail;
import com.example.myapplication.manager.ImageLoaderManager;
import com.example.myapplication.presenter.TopicDetailPresenter;
import com.example.myapplication.utils.UIUtils;
import com.example.myapplication.view.TopicDetailView;
import com.gc.materialdesign.views.LayoutRipple;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/2/16.
 */
public class TopicDetailActivity extends BaseActivity implements TopicDetailView, OnRecyclerViewItemClickListener {
    private ImageView mIvIcon;
    private TextView mTvDesc;
    private ListView mListView;
    private TopcDetailAdapter mAdapter;

    private int mId;
    private TopicDetailPresenter mPresenter;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mId = extras.getInt(Constans.Detail.DETAIL_ID);
            mPresenter = new TopicDetailPresenter(this);
            mPresenter.requestData(0, new Integer[]{mId});
        }
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        mIvIcon = (ImageView) findViewById(R.id.topic_avatar);
        mTvDesc = (TextView) findViewById(R.id.topic_tv_desc);
        mListView = (ListView) findViewById(R.id.topic_list_view);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        mListView.setFocusable(false);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mAdapter = new TopcDetailAdapter();
        mAdapter.setOnRecyclerViewItemClickListener(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void showGameDetail(AppInfo detailItem) {
        Bundle extras = new Bundle();
        extras.putLong(Constans.Detail.DETAIL_ID, detailItem.appid);
        extras.putString(Constans.Detail.DETAIL_TYPE, Constans.Detail.VIDEO_DETAIL);
        UIUtils.skipGameDetail(this, extras);
    }

    /*解决ScrollView嵌套ListView无法测量高度问题
    * @param listView
    */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideloading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showSuccess(TopicDetail topicDetail) {
        mCollapsingToolbarLayout.setTitle(topicDetail.topicInfo.topictitle);
        ImageLoader.getInstance().displayImage(topicDetail.topicInfo.topiclogo, mIvIcon,
                ImageLoaderManager.getInstance(this).getDisplayOptions());
        mTvDesc.setText(Html.fromHtml(topicDetail.topicInfo.topicdesc));
        mAdapter.addData(topicDetail.applist);
        setListViewHeightBasedOnChildren(mListView);
    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void onItemClick(View view, int position) {
        AppInfo item = mAdapter.getItem(position);
        mPresenter.onItemClick(view, item);
    }

    public static class TopcDetailHolder {

        public TextView title, size, tag;
        public ImageView avatar;
        public FrameLayout roundedFrame;
        public Button download;
        public LayoutRipple itemLayout;

        public static TopcDetailHolder getHolder(View view) {
            TopcDetailHolder holder = (TopcDetailHolder) view.getTag();
            if (holder == null) {
                holder = new TopcDetailHolder();
                holder.itemLayout = (LayoutRipple) view.findViewById(R.id.itemLayout);
                holder.title = (TextView) view.findViewById(R.id.game_item_title);
                holder.size = (TextView) view.findViewById(R.id.game_item_size);
                holder.roundedFrame = (FrameLayout) view.findViewById(R.id.game_item_rounded_frame);
                holder.download = (Button) view.findViewById(R.id.game_item_btn_download);
                holder.avatar = (ImageView) view.findViewById(R.id.game_item_avatar);
                holder.tag = (TextView) view.findViewById(R.id.game_item_tag);
                view.setTag(holder);
            }
            return holder;
        }
    }

    public class TopcDetailAdapter extends BaseAdapter {

        private ArrayList<AppInfo> mData = new ArrayList<>();
        private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

        public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener) {
            this.mOnRecyclerViewItemClickListener = mOnRecyclerViewItemClickListener;
        }

        public void addData(ArrayList data) {
            if (data != null) {
                mData.addAll(data);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public AppInfo getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TopcDetailHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.game_list_item, null);
                holder = TopcDetailHolder.getHolder(convertView);
            } else {
                holder = (TopcDetailHolder) convertView.getTag();
            }
            AppInfo item = getItem(position);
            if (item != null) {
                String category = item.categoryname;
                if ("角色扮演".equals(category)) {
                    holder.tag.setTextColor(UIUtils.getColor(R.color.fallow_color));
                    holder.roundedFrame.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.rounded_image_fallow));
                } else if ("动作射击".equals(category)) {
                    holder.tag.setTextColor(UIUtils.getColor(R.color.motion_color));
                    holder.roundedFrame.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.rounded_image_motion));
                } else if ("休闲益智".equals(category)) {
                    holder.tag.setTextColor(UIUtils.getColor(R.color.role_color));
                    holder.roundedFrame.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.rounded_image_role));
                }
                ImageLoader.getInstance().displayImage(item.applogo, holder.avatar,
                        ImageLoaderManager.getInstance(TopicDetailActivity.this).getDisplayOptions(12));
                holder.title.setText(item.apptitle);
                holder.size.setText(item.appsize + "M");
                holder.tag.setText(item.categoryname);
                holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnRecyclerViewItemClickListener != null)
                            mOnRecyclerViewItemClickListener.onItemClick(v, position);
                    }
                });
            }
            return convertView;
        }
    }
}
