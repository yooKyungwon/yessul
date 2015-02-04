package com.team.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

import android.R.string;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.team.network.Uris;
import com.team.yessul.MainPage;
import com.yessul.data.BaseData;
import com.yessul.data.ContentsData;
import com.yessul.data.UserData;


	public class MainGetData extends AsyncTask<Void, Void, String>{
		  private  ArrayList<BaseData> tempData = new ArrayList<BaseData>();
		  NetWorkTaskCompelet listener;
		public ArrayList<BaseData> getData(){
			return tempData;
			
		}
		   public interface NetWorkTaskCompelet{
		    void NetWorkTaskCompeletResult();
		}
		
		 
	 public MainGetData(NetWorkTaskCompelet listener){
		 
		this.listener = listener;
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
			
			client = new DefaultHttpClient();
			post = new HttpPost(Uris.GET_MAIN_DATA_URI);
			mData = new JSONObject();
			//MusicData music = diary.getMusic();
			try {
				//mData.put("music", diary.getMusic());
				//mData.put("musicId", music.getMusicId());
				//mData.put("emotion", diary.getEmotion());
				//post.setEntity(new StringEntity(mData.toString(), "UTF-8"));
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
				JSONArray jAr;
				
			//	JSONObject object = new JSONObject(result);
				jAr = new JSONArray(resultData);
			
				for(int i=0; i< jAr.length();i++){
			         JSONObject tmp = jAr.getJSONObject(i);
		                // 객체에서 데이터를 추출
		              //  strData += student.getString("name") + " - " + student.getInt("math") + "\n";
			         BaseData Bd = new BaseData();
			         Bd.setContents_img(tmp.getString("maincover"));
			         Bd.setContents_id(String.valueOf(tmp.getInt("contents_id")));
			         Bd.setStartCover(tmp.getString("startcover"));
			         Bd.setBgCover(tmp.getString("bgcover"));
			         Bd.setMinicover(tmp.getString("minicover"));
					tempData.add(Bd);
				
			    
//			         Log.d("data_id",tempData.get(i).getContents_id().toString());
			         Log.d("data_img",tempData.get(i).getMinicover().toString());
			         
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
			listener.NetWorkTaskCompeletResult();
		}
	}
	
	
