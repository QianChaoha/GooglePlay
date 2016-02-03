/**
 * 
 */
package com.example.googleplay.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import com.example.googleplay.R;
import com.example.googleplay.util.ScreenUtil;

/**
 * Description:ListView图片轮询的HeadView
 * 
 * @author qianchao
 */
public class HeadView extends RelativeLayout {

	/**
	 * @param context
	 */
	public HeadView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 */
	public void initData(int headViewHeight, ViewPager viewPager, IndicatorView mIndicatorView) {
		// 设置相对布局的宽度和高度
		AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(getContext(), headViewHeight));
		// 把宽高加入到相对布局里面
		this.setLayoutParams(params);
		// 初始化viewpager的宽高
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		if (viewPager==null) {
			viewPager=new ViewPager(getContext());
		}
		// 把viewpager的宽高添加到布局里面
		viewPager.setLayoutParams(rl);
		// 初始化点
		if (mIndicatorView==null) {
			mIndicatorView = new IndicatorView(getContext());
		}
		// 初始化点的宽高
		rl = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// 设置点的宽高
		mIndicatorView.setLayoutParams(rl);
		// 设置点和点之间的间隙
		mIndicatorView.setInterval(5);
		mIndicatorView.setSelection(0);
		// 设置点之间的间距
		rl.setMargins(0, 0, 20, 20);
		// 设置点的图片
		mIndicatorView.setIndicatorDrawable(getResources().getDrawable(R.drawable.indicator));
		// 设置点到屏幕的下面
		rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// 设置点到屏幕的右边
		rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		this.addView(viewPager);
		this.addView(mIndicatorView);
	}

}
