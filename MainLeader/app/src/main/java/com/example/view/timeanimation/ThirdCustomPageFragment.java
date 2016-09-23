package com.example.view.timeanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleveroad.slidingtutorial.PageFragment;
import com.cleveroad.slidingtutorial.TransformItem;
import com.example.view.activity.LoginActivity;
import com.example.view.activity.R;
import com.example.view.customview.LoadingDialog;


public class ThirdCustomPageFragment extends PageFragment {

//	private LayoutInflater inflater;
	private LoadingDialog dialog;

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_page_third;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.fragment_page_third,null);
		TextView enterView = (TextView) view.findViewById(R.id.enterView);

		enterView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				goLoginActivity();
			}
		});

		return view;
	}

	private void goLoginActivity() {
		dialog = new LoadingDialog(getActivity());
		dialog.show();
		new Thread(){
			@Override
			public void run() {
				super.run();
				try {
					sleep(2000);
					Intent intent = new Intent();
					intent.setClass(getActivity(),LoginActivity.class);
					startActivity(intent);
					getActivity().finish();
					dialog.dismiss();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	@Override
	protected TransformItem[] provideTransformItems() {
		return new TransformItem[]{
				new TransformItem(R.id.ivFirstImage, true, 20),
				new TransformItem(R.id.ivSecondImage, false, 6),
				new TransformItem(R.id.ivThirdImage, true, 8),
				new TransformItem(R.id.ivFourthImage, false, 10),
				new TransformItem(R.id.ivFifthImage, false, 3),
				new TransformItem(R.id.ivSixthImage, false, 9),
				new TransformItem(R.id.ivSeventhImage, false, 14),
		};
	}
}
