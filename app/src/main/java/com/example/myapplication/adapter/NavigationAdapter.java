package com.example.myapplication.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.NaviItem;
import com.example.myapplication.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/3/14.
 */
public class NavigationAdapter extends RecyclerView.Adapter {

    private static final int HEADER_TYPE = 1;
    private static final int NORMAL_TYPE = 0;
    private ArrayList<NaviItem> mNaviItems = new ArrayList<>();
    private View mHeaderView;
    private OnRecyclerViewItemClickListener mItemClickListener;
    private int mChoose;

    public int getChoose() {
        return mChoose;
    }

    public void setChoose(int mChoose) {
        this.mChoose = mChoose;
        notifyItemChanged(mChoose);
    }

    public void setItemClickListener(OnRecyclerViewItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public NaviItem getItem(int position) {
        return mNaviItems.get(getRealPosition(position));
    }

    public void addItem(NaviItem item) {
        if (item != null) {
            mNaviItems.add(item);
        }
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<NaviItem> items) {
        if (items != null)
            mNaviItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            return new HeaderHolder(mHeaderView);
        }
        return new NaviHolder(View.inflate(parent.getContext(), R.layout.navi_item, null), mItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == NORMAL_TYPE) {
            NaviHolder naviHolder = (NaviHolder) holder;
            NaviItem naviItem = mNaviItems.get(getRealPosition(naviHolder.getLayoutPosition()));
            naviHolder.tvTitle.setSelected(position == mChoose);
            naviHolder.tvTitle.setText(naviItem.getName());
            Drawable drawable = UIUtils.getDrawable(naviItem.getIcon());
            int size = UIUtils.getDimen(R.dimen.nav_icon_size);
            drawable.setBounds(0, 0, size, size);
            naviHolder.tvTitle.setCompoundDrawablesRelative(drawable, null, null, null);
        }
    }

    private int getRealPosition(int position) {
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return HEADER_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mNaviItems.size() : mNaviItems.size() + 1;
    }

    public class NaviHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvTitle;
        private OnRecyclerViewItemClickListener listener;

        public NaviHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.findViewById(R.id.itemLayout).setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.navi_title);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onItemClick(v, getLayoutPosition());
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }
}
