package com.geniusgithub.mediarender;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		RenderApplication.onCatchError(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();

		RenderApplication.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		RenderApplication.onResume(this);
	}

}






