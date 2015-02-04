package com.team.yessul;

import java.util.ArrayList;

import com.team.network.MainGetData;




import com.team.network.MainGetData.NetWorkTaskCompelet;
import com.yessul.data.BaseData;
import com.yessul.data.UserData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class splash extends Activity implements NetWorkTaskCompelet{
	
	UserData userData;
	  MainGetData mainGetData;
 public ArrayList<BaseData> mainData = new ArrayList<BaseData>();

  
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yessul_splash);
		//final NetworkGetData netGetData = new NetworkGetData();

				// TODO Auto-generated method stub
		mainData= new ArrayList<BaseData>();
		userData= new UserData();
		mainGetData=new MainGetData(this);
		Log.e("Network", "starting Network");
		mainGetData.execute();

	}


	public void NetWorkTaskCompeletResult() {
		// TODO Auto-generated method stub
		//Log.e("NetWorkTaskCompeletResult", String.valueOf(mainGetData.getData().size()));
		mainData.addAll(mainGetData.getData());
		userData.setMainData(mainData);
		mainGetData.cancel(true);
		if(!mainData.isEmpty()){
			overridePendingTransition(android.R.anim.slide_in_left, R.anim.disappear_to_left);
			
			Intent i =new Intent(splash.this,login.class);
			i.putExtra("UserData", userData);
			startActivity(i);
			finish();
			}
	}

}
