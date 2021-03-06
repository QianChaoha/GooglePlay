package com.example.googleplay.fragment;

import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.googleplay.R;
import com.example.googleplay.activity.DetailActivity;
import com.example.googleplay.adapter.HomeContentAdapter;
import com.example.googleplay.adapter.HomePageAdapter;
import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.base.BaseListView;
import com.example.googleplay.data.AppInfo;
import com.example.googleplay.data.HomeData;
import com.example.googleplay.http.HttpHelper;
import com.example.googleplay.http.NetWorkResponse;
import com.example.googleplay.view.HeadView;
import com.example.googleplay.view.IndicatorView;
import com.example.volley.Request.Method;
import com.google.gson.Gson;

public class HomeFragment extends BaseFragment {
	private BaseListView listview;
	private int index = 0;
	private HomeContentAdapter homeContentAdapter = null;
	private NetWorkResponse<HomeData> netWorkResponse;
	private ViewPager viewPager;
	private List<String> pictures;
	private HomeData homeData;
	private List<AppInfo> appInfos;
	private boolean clearList = true;
	private IndicatorView mIndicatorView;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % pictures.size());
			mIndicatorView.setSelection((viewPager.getCurrentItem() + 1) % pictures.size());
			handler.sendEmptyMessageDelayed(0, 3000);
		};
	};

	public void onResume() {
		super.onResume();
		clearList = true;
	};

	@Override
	protected int getLayoutId() {
		return R.layout.homefragment_layout;
	}

	@Override
	protected void initView(View view) {
		listview = (BaseListView) view.findViewById(R.id.listview);
		viewPager=new ViewPager(getActivity());
		mIndicatorView = new IndicatorView(getActivity());
		initHeadView();
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int newPosition = position - listview.getHeaderViewsCount();
				if (newPosition >= 0 && appInfos != null && appInfos.size() > 0) {
					clearList = false;

					// 添加观察者
					// DownloadManager downloadManager=new DownloadManager();
					// downloadManager.addObserver(homeContentAdapter);

					Intent intent = new Intent(getActivity(), DetailActivity.class);
					intent.putExtra("packageName", appInfos.get(newPosition).getPackageName());
					// intent.putExtra("downloadManager", downloadManager);
					startActivity(intent);
				}
			}
		});
	}

	private void initHeadView() {
		HeadView headView = new HeadView(getActivity());
		headView.initData(200, viewPager, mIndicatorView);
		listview.addHeaderView(headView);
	}

	@Override
	protected void initData() {
		netWorkResponse = new NetWorkResponse<HomeData>(getActivity(), Method.GET, HttpHelper.HOME_URL + 0, null) {

			@Override
			public void onSuccess(HomeData backData) {
				appInfos = backData.getList();
				pictures = backData.getPicture();
				index++;
				homeContentAdapter = new HomeContentAdapter<HomeData>(getActivity(), backData.getList()) {

					@Override
					protected String getUrl() {
						return HttpHelper.HOME_URL + index;
					}

					@Override
					protected Map<String, String> getParam() {
						return null;
					}

					@Override
					protected int getMethod() {
						return Method.GET;
					}

					@Override
					protected List<AppInfo> onLoadMore(Gson mGson, String data) {
						index++;
						homeData = mGson.fromJson(data, HomeData.class);
						if (homeData != null) {
							appInfos.addAll(homeData.getList());
							return homeData.getList();
						}
						return null;
					}

					@Override
					protected void size(int currentSize, int totalSize) {

					}

				};
				listview.setAdapter(homeContentAdapter);
				viewPager.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							handler.removeMessages(0);
							break;
						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							handler.sendEmptyMessageDelayed(0, 3000);
							break;

						default:
							break;
						}
						return false;
					}
				});
				// 增加图片轮询
				viewPager.setAdapter(new HomePageAdapter(getActivity(), pictures));
				mIndicatorView.setCount(pictures.size());
				if (pictures != null && pictures.size() > 0) {
					handler.sendEmptyMessageDelayed(0, 3000);
				}
			}
		};
	}

	@Override
	public void onDestroy() {
		if (clearList) {
			handler.removeMessages(0);
			// 在viewpager滑动到游戏页面时，将homeFragment中appInfos清空,释放内存空间
			// appInfos.clear();
			// appInfos = null;
		}
		super.onDestroy();
	}
}
