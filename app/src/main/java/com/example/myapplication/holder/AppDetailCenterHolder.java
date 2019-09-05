package com.example.myapplication.holder;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.AppContext;
import com.example.myapplication.R;
import com.example.myapplication.bean.AppDetail;
import com.example.myapplication.bean.AppDetail.Similar;
import com.example.myapplication.manager.ImageLoaderManager;
import com.example.myapplication.utils.UIUtils;
import com.example.myapplication.widget.FilterImageView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/2/24.
 */
public class AppDetailCenterHolder extends BaseHolder<AppDetail> implements View.OnClickListener {
    private LinearLayout mScrren;
    private LinearLayout mSimilar;
    private TextView mTvSystem;
    private TextView mTvLanguage;
    private TextView mTvAuthor;
    private TextView mTvCategory;
    private TextView mTvCrackdesc;
    private TextView mTvDesc;
    private ImageView mIvArrow;

    private boolean isExpand;

    public AppDetailCenterHolder(Context context) {
        super(context);
    }

    private OnAppCenterListener mListener;

    public void setListener(OnAppCenterListener mListener) {
        this.mListener = mListener;
    }

    public interface OnAppCenterListener{
        void onScrrenShotClick(View view, int position, String[] images);
        void onSimilarClick(Similar similar);
    }

    @Override
    public View initView() {
        View view = mInflater.inflate(R.layout.app_detail_center, null);
        mScrren = (LinearLayout) view.findViewById(R.id.app_detail_scrren);
        mSimilar = (LinearLayout) view.findViewById(R.id.app_detail_similar);
        mTvSystem = (TextView) view.findViewById(R.id.app_detail_system);
        mTvLanguage = (TextView) view.findViewById(R.id.app_detail_language);
        mTvAuthor = (TextView) view.findViewById(R.id.app_detail_author);
        mTvCategory = (TextView) view.findViewById(R.id.app_detail_category);
        mTvCrackdesc = (TextView) view.findViewById(R.id.app_detail_crackdesc);
        mTvDesc = (TextView) view.findViewById(R.id.app_detail_desc);
        mIvArrow = (ImageView) view.findViewById(R.id.app_detail_arrow);
        mTvDesc.setOnClickListener(this);
        mTvDesc.getLayoutParams().height = getDescStartHeight();
        return view;
    }

    @Override
    public void initData() {
        mTvSystem.setText(String.format("系统：%s", mData.gameinfo.system));
        mTvLanguage.setText(String.format("语言：%s", mData.gameinfo.applanguage));
        mTvAuthor.setText(String.format("作者：%s", mData.gameinfo.username));
        mTvCategory.setText(String.format("分类：%s", mData.gameinfo.categoryname));
        //去除字符串中的\n\t\r
        if (!TextUtils.isEmpty(mData.gameinfo.appcrackdesc)) {
            mTvCrackdesc.setText(Html.fromHtml(mData.gameinfo.appcrackdesc));
        } else {
            mTvCrackdesc.setVisibility(View.GONE);
        }
        mTvDesc.setText(Html.fromHtml(mData.gameinfo.appdesc));
        String[] imageresource = mData.gameinfo.imageresource;
        if (imageresource != null && imageresource.length > 0) {
            for (int i = 0; i < imageresource.length; i++) {
                String uri = imageresource[i];
                if (!TextUtils.isEmpty(uri)) {
                    addScrren(uri, i, imageresource);
                }
            }
            mScrren.setVisibility(View.VISIBLE);
        } else {
            mScrren.setVisibility(View.GONE);
        }
        ArrayList<Similar> similarList = mData.similarList;
        if (similarList != null && similarList.size() > 0) {
            for (int i = 0; i < similarList.size(); i++) {
                Similar similar = similarList.get(i);
                if (similar != null) {
                    addSimilar(similar);
                }
            }
            mSimilar.setVisibility(View.VISIBLE);
        } else {
            mSimilar.setVisibility(View.GONE);
        }
    }

    private void addScrren(String uri, int position, String[] imageresource) {
        FilterImageView imageView = new FilterImageView(AppContext.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnClickListener(onScrrenShotClick(position, imageresource));
        ImageLoader.getInstance().displayImage(uri, imageView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(99), UIUtils.dip2px(99), 1);
        int margin = UIUtils.px2dip(5);
        params.setMargins(margin, margin, margin, margin);
        mScrren.addView(imageView, params);
    }

    private void addSimilar(Similar similar) {
        if (similar != null) {
            LinearLayout linearLayout = new LinearLayout(AppContext.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            FilterImageView imageView = new FilterImageView(AppContext.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setOnClickListener(onSimilarClick(similar));
            TextView textView = new TextView(AppContext.getContext());
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setSingleLine();
            ImageLoader.getInstance().displayImage(similar.applogo, imageView,
                    ImageLoaderManager.getInstance(AppContext.getContext()).getDisplayOptions(12));
            textView.setText(similar.apptitle);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(80), UIUtils.dip2px(80), 1);
            int margin = UIUtils.dip2px(5);
            params.setMargins(margin, margin, margin, margin);
            linearLayout.addView(imageView, params);
            linearLayout.addView(textView);
            mSimilar.addView(linearLayout);
        }
    }

    public View.OnClickListener onScrrenShotClick(final int position, final String[] imageresource) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onScrrenShotClick(view, position, imageresource);
                }
            }
        };
    }

    public View.OnClickListener onSimilarClick(final Similar similar) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onSimilarClick(similar);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        toAnimator();
        isExpand = !isExpand;
    }

    private void toAnimator() {
        final int startHeight;
        final int endHeight;
        if (!isExpand) {
            startHeight = getDescStartHeight();
            endHeight = getDescEndHeight();
        } else {
            startHeight = getDescEndHeight();
            endHeight = getDescStartHeight();
        }
        final ViewGroup.LayoutParams params = mTvDesc.getLayoutParams();
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                Integer percent = evaluate(fraction, startHeight, endHeight);
                params.height = percent;
                mTvDesc.setLayoutParams(params);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIvArrow.setImageResource(isExpand ? R.mipmap.ic_arrow_up : R.mipmap.ic_arrow_down);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private int getDescStartHeight() {
        TextView textView = new TextView(AppContext.getContext());
        textView.setMaxLines(2);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        textView.setLines(2);

        int width = mTvDesc.getMeasuredWidth();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    private int getDescEndHeight() {
        int width = mTvDesc.getMeasuredWidth();
        mTvDesc.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(1000, View.MeasureSpec.AT_MOST);
        mTvDesc.measure(widthMeasureSpec, heightMeasureSpec);
        return mTvDesc.getMeasuredHeight();
    }

    /**
     * Int类型估计器
     */
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue.intValue();
        return Integer.valueOf((int) ((float) startInt + fraction * (float) (endValue.intValue() - startInt)));
    }
}
