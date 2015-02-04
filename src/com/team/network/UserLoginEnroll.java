package com.team.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.ja;
import com.team.network.GetContentsData.NetWorkTaskCompelet;
import com.yessul.data.BaseData;
import com.yessul.data.ContentsData;
import com.yessul.data.UserBaseData;
import com.yessul.data.UserData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UserLoginEnroll extends AsyncTask<Void, Void, String> {

	 NetworkUserComplete listener;  
	  String id;
	  String name; 
		UserData userTmp = new UserData();
		UserBaseData   bdTmp =  new UserBaseData(null, null);
	  String statusFlag;
	   public interface NetworkUserComplete{
	  

		void NetworkUserCompleteResult(String statusFlag, UserData userTmp,
				UserBaseData bdTmp);		
	   }
	
	 
public UserLoginEnroll(NetworkUserComplete listener, String id,String name){
	 
	this.listener = listener;
	this.id = id;
	this.name = name;
	}
	

	HttpClient client;
	HttpPost post;
	UserData userdata;
	JSONObject mData;
	ProgressDialog dialog;
	String resultData  = "";
	private Context context;
	private Object reader;
	
	@Override
	 protected void onPreExecute() {
		//dialog = ProgressDialog.show(context, "", "getData");
		Log.d("excute","preExecute");
		client = new DefaultHttpClient();
		post = new HttpPost(Uris.Login_Enroll);
		mData = new JSONObject();
		//MusicData music = diary.getMusic();
		try {
			//mData.put("music", diary.getMusic());
			//mData.put("musicId", music.getMusicId());
			//mData.put("emotion", diary.getEmotion());
			//post.setEntity(new StringEntity(mData.toString(), "UTF-8"));
			mData.put("id", id);
			mData.put("name", name);
			post.setEntity(new StringEntity(mData.toString(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String doInBackground(Void... params) {
		try {
			post.setHeader(HTTP.CONTENT_TYPE, "application/json; charset=UTF-8");
			HttpResponse response = client.execute(post);
			
		        HttpEntity entity = response.getEntity();
		       
		        InputStream is = entity.getContent();
			 BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
			//Log.e("e",response.getEntity().toString());
			 StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
		            sb.append(line + "\n");
		        }
		       
		        resultData = sb.toString();
		    
			return EntityUtils.toString(response.getEntity(), "UTF-8");
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@Override
	protected void onPostExecute(String result) {

		Log.d("json:body",resultData);
		try {
			
			
		
			String idTmp = new String();
			JSONObject objTmp = new JSONObject(resultData);
			Log.d("json:stautsFlag",String.valueOf(objTmp.getInt("statusFlag")));
			
			statusFlag= String.valueOf(objTmp.getInt("statusFlag"));
			JSONArray jAr;					
			jAr = new JSONArray();
			jAr = objTmp.getJSONArray("userData");
			  ArrayList<String> ingData = new ArrayList<String>();
			  ArrayList<String> completeData = new ArrayList<String>();
			for(int i=0; i< jAr.length();i++){
		         JSONObject tmp = jAr.getJSONObject(i);
	      
					if(Integer.parseInt(statusFlag) ==1){
					
						
					}else if(Integer.parseInt(statusFlag) ==2){
						JSONArray userDataArr = new JSONArray();
						
						
						
							if(!tmp.getString("ing_mission").equals("0")){
							ingData.add(tmp.getString("ing_mission")) ;
							}
							if(!tmp.getString("cmp_mission").equals("0")){
								completeData.add( tmp.getString("cmp_mission"));
							}
							
							Log.d("completeData", completeData.get(i));
							
						
						
						
					}
		
		         
			}
			if(Integer.parseInt(statusFlag) ==1){
				Log.d("UserLoginEnroll","첫회원");
				userTmp.setUserToken(id);
			
				  bdTmp.setCompleteData(null);
				    bdTmp.setIngData(null);
				
			}else if(Integer.parseInt(statusFlag) ==2){
				Log.d("UserLoginEnroll","data 받기");
				userTmp.setUserToken(id);
			    bdTmp.setCompleteData(completeData);
			    bdTmp.setIngData(ingData);
				
			}
			
			
			
	
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		  
		listener.NetworkUserCompleteResult(statusFlag,userTmp,bdTmp);
	}
}
