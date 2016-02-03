package com.example.googleplay.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.http.HttpHelper;
import com.example.googleplay.http.NetWorkResponse;
import com.example.googleplay.util.DrawableUtils;
import com.example.googleplay.util.ScreenUtil;
import com.example.googleplay.view.FlowLayout;
import com.example.volley.Request.Method;

public class TopFragment extends BaseFragment {

	private Drawable pressedDrawable;
	private FlowLayout layout;
	private ScrollView scrollView;

	@Override
	protected int getLayoutId() {
		return R.layout.scrollview_layout;
	}

	@Override
	protected void initView(View view) {
		scrollView = (ScrollView) view.findViewById(R.id.scrollView);
		scrollView.setBackgroundResource(R.drawable.grid_item_bg_normal);
		scrollView.setFillViewport(true);
		layout = new FlowLayout(getActivity());
		int padding = ScreenUtil.dip2px(getActivity(), 13);
		layout.setPadding(padding, padding, padding, padding);
		layout.setHorizontalSpacing(padding);
		layout.setVerticalSpacing(padding);
		int backColor = 0xffcecece;
		pressedDrawable = DrawableUtils.createShape(getActivity(), backColor);
	}

	@Override
	protected void initData() {
		NetWorkResponse<List<String>> netWorkResponse = new NetWorkResponse<List<String>>(getActivity(), Method.GET, HttpHelper.TOP_URL, null) {

			@Override
			public void onSuccess(List<String> backData) {
				layout.removeAllViews();
				scrollView.removeAllViews();
				Random random = new Random(); // 创建随机
				for (int i = 0; i < backData.size(); i++) {
					TextView textView = new TextView(getActivity());
					final String str = backData.get(i);
					textView.setGravity(Gravity.CENTER);
					textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
					textView.setText(str);

					int red = random.nextInt(200) + 22;
					int green = random.nextInt(200) + 22;
					int blue = random.nextInt(200) + 22;
					int color = Color.rgb(red, green, blue);// 范围 0-255
					//设置圆角
					GradientDrawable createShape = DrawableUtils.createShape(getActivity(), color); // 默认显示的图片
					StateListDrawable createSelectorDrawable = DrawableUtils.createSelectorDrawable(pressedDrawable, createShape);// 创建状态选择器
					textView.setBackgroundDrawable(createSelectorDrawable);
					textView.setTextColor(Color.WHITE);
					// textView.setTextSize(UiUtils.dip2px(14));
					int textPaddingV = ScreenUtil.dip2px(getActivity(), 4);
					int textPaddingH = ScreenUtil.dip2px(getActivity(), 7);
					textView.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV); // 设置padding
					textView.setClickable(true);// 设置textView可以被点击
					textView.setOnClickListener(new OnClickListener() {// 设置点击事件

						@Override
						public void onClick(View v) {
							Toast.makeText(getActivity(), str, 0).show();
						}
					});
					layout.addView(textView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, -2));// -2
																											// 包裹内容
				}
				scrollView.addView(layout);

			}
		};
	}
}
