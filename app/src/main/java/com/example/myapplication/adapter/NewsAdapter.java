package com.example.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.AppContext;
import com.example.myapplication.R;
import com.example.myapplication.bean.NewsInfo;
import com.example.myapplication.manager.ImageLoaderManager;
import com.example.myapplication.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by MBENBEN on 2015/12/29.
 */
public class NewsAdapter extends AppAdapter<NewsInfo> {

    public static final int SLIDE_TYPE = 0x02;
    public static final int DESC_TYPE = 0x03;

    public static final int HEADER_TYPE = 0x01;
    protected View mHeaderView;
    private OnRecyclerViewItemClickListener mItemClickListener;

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (mHeaderView != null && viewType == HEADER_TYPE) {
            viewHolder = new HeaderHolder(mHeaderView);
        } else if (viewType == SLIDE_TYPE) {
            view = inflater.inflate(R.layout.news_list_slide_item, null);
            viewHolder = new NewsSlideHolder(view, mItemClickListener);
        } else if (viewType == NORMAL_TYPE) {
            view = inflater.inflate(R.layout.news_list_desc_item, null);
            viewHolder = new NewsDescHolder(view, mItemClickListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == HEADER_TYPE) {
            return;
        }
        if (type == SLIDE_TYPE) {
            bindSlideData(holder, position);
            return;
        }
        if (type == NORMAL_TYPE) {
            bindDescData(holder, position);
            return;
        }
    }

    private void bindDescData(RecyclerView.ViewHolder holder, int position) {
        NewsDescHolder descHolder = (NewsDescHolder) holder;
        int pos = getRealPosition(descHolder.getLayoutPosition());
        String title = getItem(pos).title;
        String time = getItem(pos).updateTime;
        String avatar = getItem(pos).thumbnail;
        descHolder.tvTitle.setText(title);
        descHolder.tvTime.setText(time);
        DisplayImageOptions options = ImageLoaderManager.getInstance(AppContext.getContext()).getDisplayOptions(6);
        ImageLoader.getInstance().displayImage(avatar, descHolder.ivAvatar, options);
    }

    private void bindSlideData(RecyclerView.ViewHolder holder, int position) {
        NewsSlideHolder slideHolder = (NewsSlideHolder) holder;
        int pos = getRealPosition(slideHolder.getLayoutPosition());
        String title = getItem(pos).title;
        String time = getItem(pos).updateTime;
        if (!TextUtils.isEmpty(title))
            slideHolder.tvTitle.setText(title);
        if (!TextUtils.isEmpty(time)) {
            slideHolder.tvTime.setText(time);
        }
        LogUtils.e(getItem(pos).toString() + "--------------->");
        NewsInfo.Style style = getItem(pos).style;
        if (style != null) {
            String[] images = style.images;
            DisplayImageOptions options = ImageLoaderManager.getInstance(AppContext.getContext()).getDisplayOptions(6);
            ImageLoader.getInstance().displayImage(images[0], slideHolder.iv1, options);
            ImageLoader.getInstance().displayImage(images[1], slideHolder.iv2, options);
            ImageLoader.getInstance().displayImage(images[2], slideHolder.iv3, options);
        }
    }

    private int getRealPosition(int position) {
        position = mHeaderView == null ? position : position - 1;
        if (position < 0)
            position = 0;
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderView != null)
            return HEADER_TYPE;
        NewsInfo item = getItem(getRealPosition(position));
        if (TextUtils.equals("slide", item.type))
            return SLIDE_TYPE;
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? getData().size() : getData().size() + 1;
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
