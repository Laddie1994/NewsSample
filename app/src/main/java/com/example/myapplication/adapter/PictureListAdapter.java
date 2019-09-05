package com.example.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.AppContext;
import com.example.myapplication.R;
import com.example.myapplication.bean.PictureInfo;
import com.example.myapplication.manager.ImageLoaderManager;
import com.gc.materialdesign.views.LayoutRipple;
import com.github.library.PinterestLikeAdapterView.PLAImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBENBEN on 2015/12/26.
 */
public class PictureListAdapter extends BaseAdapter implements AbsListView.RecyclerListener {

    protected ArrayList<PictureInfo> mDatas = new ArrayList<>();
    protected Context mContext;
    protected ArrayList<BaseViewHolder> mDisplayedHolder;
    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;

    public PictureListAdapter(Context context) {
        this.mContext = AppContext.getContext();
        mDisplayedHolder = new ArrayList<>();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener) {
        this.mOnRecyclerViewItemClickListener = mOnRecyclerViewItemClickListener;
    }

    public ArrayList<BaseViewHolder> getmDisplayedHolder() {
        return mDisplayedHolder;
    }

    public ArrayList<PictureInfo> getDatas() {
        return mDatas;
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<PictureInfo> getData() {
        return mDatas == null ? (mDatas = new ArrayList<PictureInfo>()) : mDatas;
    }

    /**
     * 设置数据
     *
     * @return
     */
    public void setData(ArrayList<PictureInfo> data) {
        if (data != null) {
            this.mDatas = data;
            notifyDataSetChanged();
        }
    }

    /**
     * 在末尾添加数据
     *
     * @param data
     */
    public void addData(List<PictureInfo> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 在末尾添加单个数据
     *
     * @param obj
     */
    public void addItem(PictureInfo obj) {
        if (mDatas != null && obj != null) {
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    /**
     * 在指定位置添加数据
     *
     * @param pos 位置
     * @param obj 数据
     */
    public void addItem(int pos, PictureInfo obj) {
        if (mDatas != null) {
            mDatas.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    /**
     * 删除单个数据
     *
     * @param obj
     */
    public void removeItem(Object obj) {
        if (mDatas != null) {
            mDatas.remove(obj);
        }
        notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return getDataSize();
    }

    public int getDataSize() {
        return mDatas.size();
    }

    @Override
    public PictureInfo getItem(int position) {
        if (mDatas.size() <= position || position < 0) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PictureItemHolder viewHolder = null;
        PictureInfo item = getItem(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.picture_list_item, null);
            viewHolder = PictureItemHolder.getViewHolder(convertView);
        } else {
            viewHolder = (PictureItemHolder) convertView.getTag();
        }
        if (!TextUtils.isEmpty(item.thumbnail_url)) {
            ImageLoader.getInstance().displayImage(item.thumbnail_url, viewHolder.imageView,
                    ImageLoaderManager.getInstance(mContext).getDisplayOptions());
            viewHolder.imageView.setImageWidth(item.thumbnail_width);
            viewHolder.imageView.setImageHeight(item.thumbnail_height);
            viewHolder.textView.setText(item.abs);
            viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnRecyclerViewItemClickListener != null)
                        mOnRecyclerViewItemClickListener.onItemClick(v, position);
                }
            });
        }
        return convertView;
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        if (view != null) {
            Object tag = view.getTag();
            if (tag instanceof BaseViewHolder) {
                BaseViewHolder viewHolder = (BaseViewHolder) tag;
                synchronized (mDisplayedHolder) {
                    mDisplayedHolder.remove(viewHolder);
                }
            }
        }
    }

    public static class PictureItemHolder {

        private PLAImageView imageView;
        private TextView textView;
        private LayoutRipple itemLayout;

        public static PictureItemHolder getViewHolder(View convertView){
            PictureItemHolder holder = (PictureItemHolder) convertView.getTag();
            if (holder == null) {
                holder = new PictureItemHolder();
                holder.itemLayout = (LayoutRipple) convertView.findViewById(R.id.itemLayout);
                holder.imageView = (PLAImageView) convertView.findViewById(R.id.pictureImageView);
                holder.textView = (TextView) convertView.findViewById(R.id.pictureTextView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
