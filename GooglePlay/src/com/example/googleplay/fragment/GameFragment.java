package com.example.googleplay.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.widget.TextView;

import com.example.googleplay.base.BaseFragment;
import com.example.googleplay.http.MultipartRequest;
import com.example.googleplay.http.NetRequest;
import com.example.googleplay.view.CommenNetImageView;
import com.example.volley.Response;
import com.example.volley.VolleyError;

public class GameFragment extends BaseFragment {
	String url = "http://Test.telecomsh.cn:9091/ywglapp/myInfo/AddFeedBackinfo.json";
	@Override
	protected int getLayoutId() {
		return android.R.layout.simple_expandable_list_item_1;
	}

	@Override
	protected void initView(View view) {
		((TextView)view).setText("GameFragment");
	}

	@Override
	protected void initData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("accessToken", "B0E54DB29AE785BC8556A931A6A386C95EFFBAFB3B3449439CF09E8A4E485615");
		params.put("operationTime", "2015-11-30-15:05:05");
		params.put("backValue", "77");
		params.put("backType", "1");
		params.put("deviceType", "2");
		List<File> files = new ArrayList<File>();
		files.add(new File("/storage/sdcard0/WebInfos/app/com.iyd.reader.book45586/safeDesUrl0.jpg"));
		files.add(new File("/storage/sdcard0/WebInfos/app/com.iyd.reader.book45586/screen1.jpg"));
		MultipartRequest multipartRequest = new MultipartRequest(url, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println();

			}

		}, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				System.out.println();

			}
		}, "icon", files, params);
		NetRequest.getInstance(getActivity()).addToRequestQueue(multipartRequest);
	}
}
