package com.example.googleplay.activity;

import java.util.List;

import org.json.JSONObject;

import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.googleplay.R;
import com.example.googleplay.activity.view.DetailBottomHolder;
import com.example.googleplay.activity.view.DetailDesHolder;
import com.example.googleplay.activity.view.DetailInfoHolder;
import com.example.googleplay.activity.view.DetailSafeHolder;
import com.example.googleplay.adapter.HorizontalScrollViewAdapter;
import com.example.googleplay.base.BaseActivity;
import com.example.googleplay.data.AppInfo;
import com.example.googleplay.http.HttpHelper;
import com.example.googleplay.http.NetWorkResponse;
import com.example.googleplay.util.DownloadManager;
import com.example.googleplay.view.MyHorizontalScrollView;
import com.example.volley.Request;
import com.example.volley.Request.Method;
import com.example.volley.RequestQueue;
import com.example.volley.Response;

public class DetailActivity extends BaseActivity {
	private FrameLayout detail_info, detail_safe, detail_des, bottom_layout;
	private ScrollView scrollView;
	private DetailInfoHolder detailInfoView;
	private DetailSafeHolder detailSafeHolder;
	private DetailBottomHolder bottomHolder;
	private DetailDesHolder desHolder;
	private int index = 0;
	private String packageName;
	private StringRequest request;
	private RequestQueue requestQueue;
	private MyHorizontalScrollView detail_screen_horizontal;
	private DownloadManager downloadManager;

	@Override
	protected int getLayoutId() {
		return R.layout.activity_detail;
	}

	@Override
	protected void initView() {
		requestQueue.add(new JsonObjectRequest(Request.Method.GET, "", null, 
				new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				
			}
		}, null));
		
		
		
		// 增加返回按钮
		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);
		detail_screen_horizontal = (MyHorizontalScrollView) findViewById(R.id.detail_screen_horizontal);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		initFrameLayout();

	}

	private void initFrameLayout() {
		bottom_layout = (FrameLayout) findViewById(R.id.bottom_layout);
		bottomHolder = new DetailBottomHolder(this);
		bottom_layout.addView(bottomHolder.getView());
		downloadManager = (DownloadManager) getIntent().getSerializableExtra("downloadManager");
		if (downloadManager != null) {
			downloadManager.addObserver(bottomHolder);
		}

		detail_info = (FrameLayout) findViewById(R.id.detail_info);
		detailInfoView = new DetailInfoHolder(this);
		detail_safe = (FrameLayout) findViewById(R.id.detail_safe);
		detailSafeHolder = new DetailSafeHolder(this);
		// 简介
		detail_des = (FrameLayout) findViewById(R.id.detail_des);
		desHolder = new DetailDesHolder(this, scrollView);
	}

	@Override
	protected void initData() {
		DownloadManager downloadManager = new DownloadManager();

		packageName = getIntent().getStringExtra("packageName");
		if (!TextUtils.isEmpty(packageName)) {
			detail_info.addView(detailInfoView.getView());
			detail_safe.addView(detailSafeHolder.getView());
			detail_des.addView(desHolder.getView());
			NetWorkResponse<AppInfo> netWorkResponse = new NetWorkResponse<AppInfo>(this, Method.GET, HttpHelper.getDetailUrl(index, packageName),
					null) {

				@Override
				public void onSuccess(AppInfo backData) {
					detailInfoView.initData(backData);
					detailSafeHolder.initData(backData);
					desHolder.initData(backData);
					initScreenPic(backData.getScreen());
				}
			};
		}
	}

	/**
	 * @param screen
	 */
	protected void initScreenPic(List<String> screen) {
		if (screen != null && screen.size() > 0) {
			detail_screen_horizontal.initDatas(new HorizontalScrollViewAdapter(this, screen));
		}

	}

}
