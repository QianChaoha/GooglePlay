package com.example.googleplay.activity.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.googleplay.R;
import com.example.googleplay.base.BaseViewHolder;
import com.example.googleplay.data.AppInfo;
import com.example.googleplay.util.DownloadManager.Observer;

public class DetailBottomHolder extends BaseViewHolder<AppInfo> implements OnClickListener, Observer {
	Button bottom_favorites;
	Button bottom_share;
	ProgressBar progress_btn;

	/**
	 * @param context
	 */
	public DetailBottomHolder(Context context) {
		super(context);
	}

	@Override
	public int getLayoutId() {
		return R.layout.detail_bottom;
	}

	@Override
	public void initView(View view) {
		bottom_favorites = (Button) view.findViewById(R.id.bottom_favorites);
		bottom_share = (Button) view.findViewById(R.id.bottom_share);
		progress_btn = (ProgressBar) view.findViewById(R.id.progress_btn);
		progress_btn.setMax(360);
		
		bottom_favorites.setOnClickListener(this);
		bottom_share.setOnClickListener(this);
		progress_btn.setOnClickListener(this);

	}

	@Override
	public void initData(AppInfo data) {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bottom_favorites:
			Toast.makeText(context, "收藏", 0).show();
			break;
		case R.id.bottom_share:
			Toast.makeText(context, "分享", 0).show();
			break;
		case R.id.progress_btn:
			Toast.makeText(context, "下载", 0).show();
			break;
		}
	}

	@Override
	public void update(int progress) {
		progress_btn.setProgress(progress);
	}

}
