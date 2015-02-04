package com.team.yessul;

import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.UserProfile;
import com.kakao.exception.KakaoException;
import com.kakao.widget.LoginButton;
import com.team.network.UserLoginEnroll;
import com.team.network.UserLoginEnroll.NetworkUserComplete;
import com.team.yessul.R;
import com.yessul.data.BaseData;
import com.yessul.data.UserData;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class login extends Activity   {
	 private LoginButton loginButton;
	 private Button  noLoginButton;
	    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
	
	    @Override
	    protected void onCreate(final Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login_main);
	        /*noLoginButton*/
	        noLoginButton = (Button) findViewById(R.id.no_login);
	        noLoginButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Intent intent = new Intent(login.this, MainPage.class);
					  Bundle bundle = getIntent().getExtras();
					 UserData userData = bundle.getParcelable("UserData");
				       
				        intent.putExtra("UserData", userData);
			        startActivity(intent);
			        overridePendingTransition(android.R.anim.slide_in_left, R.anim.disappear_to_left);
			        finish();
				}
			});
	        // 로그인 버튼에 로그인 결과를 받을 콜백을 설정한다.
	        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
	        loginButton.setLoginSessionCallback(mySessionCallback);
	    }

	    @Override
	    protected void onResume() {
	        super.onResume();
	        // 세션을 초기화 한다
	        if(Session.initializeSession(this, mySessionCallback)){
	            // 1. 세션을 갱신 중이면, 프로그레스바를 보이거나 버튼을 숨기는 등의 액션을 취한다
	            loginButton.setVisibility(View.GONE);
	        } else if (Session.getCurrentSession().isOpened()){
	            // 2. 세션이 오픈된된 상태이면, 다음 activity로 이동한다.
	            onSessionOpened();
	        }
	            // 3. else 로그인 창이 보인다.
	    }

	    private class MySessionStatusCallback implements SessionCallback {
	        @Override
	        public void onSessionOpened() {
	            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈후 보일 페이지로 이동
	            login.this.onSessionOpened();
	        }

	        @Override
	        public void onSessionClosed(final KakaoException exception) {
	            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈을 못했으니 다시 로그인 버튼 노출.
	            loginButton.setVisibility(View.VISIBLE);
	        }

	    }

	    protected void onSessionOpened(){
	    	
	    	
	    	
	    	 final Intent intent = new Intent(login.this, MainPage.class);
		        Bundle bundle = getIntent().getExtras();
		        UserData userData = bundle.getParcelable("UserData");
		       
		        intent.putExtra("UserData", userData);
		        startActivity(intent);
		        overridePendingTransition(android.R.anim.slide_in_left, R.anim.disappear_to_left);
		        finish();
	    }
	    

	

}
