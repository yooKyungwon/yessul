package com.team.yessul;

import android.util.Log;

import com.kakao.APIErrorResult;
import com.kakao.KakaoStoryHttpResponseHandler;
import com.kakao.KakaoStoryProfile;

public class MyStoryHttpResponseHandler<T> extends
		KakaoStoryHttpResponseHandler<KakaoStoryProfile> {

	@Override
	protected void onNotKakaoStoryUser() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onFailure(APIErrorResult errorResult) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onHttpSuccess(KakaoStoryProfile resultObj) {
		// TODO Auto-generated method stub

	
		 //execute( resultObj.getBgImageURL());
	}

	@Override
	protected void onHttpSessionClosedFailure(APIErrorResult errorResult) {
		// TODO Auto-generated method stub

	}

}
