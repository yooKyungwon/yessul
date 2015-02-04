package com.team.yessul;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.android.gms.internal.bg;
import com.kakao.APIErrorResult;
import com.kakao.KakaoStoryProfile;
import com.kakao.KakaoStoryService;
import com.kakao.LogoutResponseCallback;
import com.kakao.MeResponseCallback;
import com.kakao.UnlinkResponseCallback;
import com.kakao.UserManagement;
import com.kakao.UserProfile;
import com.kakao.helper.Logger;
import com.team.network.MainGetData;
import com.team.network.MainGetData.NetWorkTaskCompelet;
import com.team.network.UserLoginEnroll;
import com.team.network.UserLoginEnroll.NetworkUserComplete;
import com.yessul.data.BaseData;
import com.yessul.data.UserBaseData;
import com.yessul.data.UserData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import de.hdodenhof.circleimageview.*;
public class MenuListFragment extends Fragment implements NetworkUserComplete,NetWorkTaskCompelet{
 

     private Bitmap profileImg;
     private Bitmap profileBgBM;
     private Button logoutBtn;
     private urlImage profileUrl;
     private urlBg bgUrl;
     private  CircleImageView  profile;
     private  LinearLayout profileBg;
     private String backgroundURL;
     private TextView nickName;
     private  Button recommendBtn;
     private Button exploreBtn;
     private Button newExploreBtn;
     private Button historyBtn;
     private Button aboutusBtn;
     private Button completeBtn;
     private Button ingBtn;
     private Button withfriend;
     UserData intentUserData;
     MainGetData mainGetData;
     NetworkUserComplete listner ;
     
     UserData userData =new UserData();
     UserBaseData baseData = new UserBaseData();
     UserLoginEnroll userLogin;
     Bundle bundle =new Bundle();
	 ArrayList<BaseData> mainData;
 
     public void setUserData(UserData userData){
    	 this.userData = userData;
     }
    public MenuListFragment(){
    }
 
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//bundle = getArguments();
    	//Log.d("bundle", String.valueOf((UserData)bundle.getParcelable("UserData")));
        return inflater.inflate(R.layout.sliding_menu_list, null);
      
    }
 
    
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    	
    	listner =this;
        profileUrl = new urlImage();
        bgUrl = new urlBg();
        mainData =  new ArrayList<BaseData>();
        profile = (CircleImageView) getView().findViewById(R.id.profile_image);	
        logoutBtn = (Button) getView().findViewById(R.id.logout_btn);
        profileBg =(LinearLayout) getView().findViewById(R.id.profilebg);
        nickName =(TextView) getView().findViewById(R.id.nickName);      
         recommendBtn = (Button) getView().findViewById(R.id.recommend_btn);
         exploreBtn = (Button) getView().findViewById(R.id.explore_btn);
         newExploreBtn = (Button) getView().findViewById(R.id.newexplore_btn);
         historyBtn = (Button) getView().findViewById(R.id.history_btn);
         aboutusBtn = (Button) getView().findViewById(R.id.aboutus_btn);
          completeBtn = (Button) getView().findViewById(R.id.complete_btn);
          ingBtn = (Button) getView().findViewById(R.id.ing_btn);
          withfriend=(Button) getView().findViewById(R.id.withfriend_btn);
          intentUserData = new UserData();
          mainGetData=new MainGetData(this);
          mainGetData.execute();
          /*aboutusBtn*/
          aboutusBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					aboutusBtn.setSelected(false);
				   Intent intent = new Intent(getActivity(), AboutUsPage.class);
			
				
				 startActivity(intent);
				
			}
		});
          /*historyBtn*/
          historyBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				historyBtn.setSelected(false);
				// TODO Auto-generated method stub
				
			}
		});
          
          /*recommendBtn*/
          recommendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
          /*newExploreBtn*/
          newExploreBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 String[] cmpData = new String[1];
				 newExploreBtn.setSelected(false);
				   cmpData[0] = userData.getMainData().get(userData.getMainData().size()-1).getContents_id();
				   
						   Intent intent = new Intent(getActivity(), ContentsGrid.class);
						// userData.setMainData(baseData);
						 intent.putExtra("UserData", userData); 
						 intent.putExtra("CmpData", cmpData);
						
						 startActivity(intent);
						
						 Log.d("completeBtn", "End");
			}
		});
          /*exploreBtn*/
          exploreBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				exploreBtn.setSelected(false);
				
				   String[] cmpData = new String[userData.getMainData().size()];
				   for (int i =0; i<userData.getMainData().size();i++){
				   cmpData[i] = userData.getMainData().get(i).getContents_id();
				   }
						   Intent intent = new Intent(getActivity(), ContentsGrid.class);
						// userData.setMainData(baseData);
						 intent.putExtra("UserData", userData); 
						 intent.putExtra("CmpData", cmpData);
						
						 startActivity(intent);
						
						 Log.d("completeBtn", "End");
			}
		});
          /*completeBtn*/
          completeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("completeBtn", "start");
				if(baseData.getCompleteData()!=null){
			   String[] cmpData = new String[baseData.getCompleteData().size()];
			  for(int i = 0 ;i<baseData.getCompleteData().size();i++){
			   cmpData[i]=baseData.getCompleteData().get(i);
			  }
				 Intent intent = new Intent(getActivity(), ContentsGrid.class);
				// userData.setMainData(baseData);
				 intent.putExtra("UserData", userData); 
				 intent.putExtra("CmpData", cmpData);
				
				 startActivity(intent);
				
				 Log.d("completeBtn", "End");
			}else {
				String[] cmpData = new String[1];
					cmpData[0]="0";
					 Intent intent = new Intent(getActivity(), ContentsGrid.class);
					// userData.setMainData(baseData);
					 intent.putExtra("UserData", userData); 
					 intent.putExtra("CmpData", cmpData);

	 				 startActivity(intent);
			}
				
			}
		});
          /*ingBtn*/
          ingBtn.setOnClickListener(new OnClickListener() { 			
  			@Override
  			public void onClick(View v) {
  				// TODO Auto-generated method stub
  				Log.d("completeBtn", "start");
  				if(baseData.getIngData()!=null){
 			   String[] cmpData = new String[baseData.getIngData().size()];
 			  for(int i = 0 ;i<baseData.getIngData().size();i++){
 			   cmpData[i]=baseData.getIngData().get(i);
 			  }
 			 Intent intent = new Intent(getActivity(), ContentsGrid.class);
				// userData.setMainData(baseData);
				 intent.putExtra("UserData", userData); 
				 intent.putExtra("CmpData", cmpData);

 				 startActivity(intent);
  				}else {
  					String[] cmpData = new String[1];
  					cmpData[0]="0";
  					 Intent intent = new Intent(getActivity(), ContentsGrid.class);
  					// userData.setMainData(baseData);
  					 intent.putExtra("UserData", userData); 
  					 intent.putExtra("CmpData", cmpData);

  	 				 startActivity(intent);
  					
  				}
 				
 				
 				
 				 Log.d("completeBtn", "End");
  			}
  		}); 
          
          
         /* Logout Btn */
        logoutBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*앱정보 지우기*/
	            UserManagement.requestUnlink(new UnlinkResponseCallback() {
	                @Override
	                protected void onSuccess(final long userId) {
	            		Log.v("LogoutBtn","err"); //데이터불러오기 성공 로그    
	                   // redirectLoginActivity();
	            		//----------- Logout and session destroy -------------------- 
	    				UserManagement.requestLogout(new LogoutResponseCallback() {
	    				        @Override
	    				        protected void onSuccess(final long userId) {
	    				           // redirectLoginActivity();
	    				        	//startActivity(new Intent(this,login.class));
	    				        

	    				        }

	    				        @Override
	    				        protected void onFailure(final APIErrorResult apiErrorResult) {
	    				            //redirectLoginActivity();
	    				        //	startActivity(new Intent(this,login.class));
	    				        }
	    				    });
	                }

	                @Override
	                protected void onSessionClosedFailure(final APIErrorResult errorResult) {
	                    //redirectLoginActivity();
	                }

	                @Override
	                protected void onFailure(final APIErrorResult errorResult) {
	                    Logger.getInstance().d("failed to unlink. msg");
	                   // redirectLoginActivity();
	                }
	            });
	            /*앱정보지우기 끝*/
				
				
				
			}
		});
        /* 세션 파괴  끝 */
                KakaoStoryService.requestProfile(new MyStoryHttpResponseHandler<KakaoStoryProfile>() {
            @Override
            protected void onHttpSuccess(final KakaoStoryProfile storyProfile) {
//                  final String nickName = storyProfile.getNickName();
//                  final String profileImageURL = storyProfile.getProfileImageURL();
//                  final String thumbnailURL = storyProfile.getThumbnailURL();
            	Log.v("BgImageURL",storyProfile.getBgImageURL()); //데이터불러오기 성공 로그    
            	backgroundURL = storyProfile.getBgImageURL();
                   
                   bgUrl.execute(backgroundURL);
                //  final Calendar birthday = storyProfile.getBirthdayCalendar();
                  //final BirthdayType birthDayType = storyProfile.getBirthdayType();
                  // display
            }
        });
        /* 유저 데이터 불러오기 */
        UserManagement.requestMe(new MeResponseCallback() {
			
			@Override
			protected void onSuccess(UserProfile userProfile) {			
				// TODO Auto-generated method stub
				  userLogin = new UserLoginEnroll( listner,String.valueOf(userProfile.getId()),userProfile.getNickname());
					userLogin.execute();
				Log.v("userProfile",userProfile.getProfileImagePath()); //데이터불러오기 성공 로그 
			nickName.setText(userProfile.getNickname());
		    profileUrl.execute( userProfile.getProfileImagePath());
			 
			}
			
			@Override
			protected void onSessionClosedFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onNotSignedUp() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void onFailure(APIErrorResult errorResult) {
				// TODO Auto-generated method stub
				
			}
		});
        /*유저 데이터 불러오기 끝 */
    }
 /*Profile setting Url*/
	  private class urlImage extends AsyncTask<String, Integer,Bitmap>{
	        
		  
		  
	        @Override
	        protected Bitmap doInBackground(String... urls) {
	            // TODO Auto-generated method stub
	            try{
	                URL myFileUrl = new URL(urls[0]);
	                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
	                conn.setDoInput(true);
	                conn.connect();
	                
	                InputStream is = conn.getInputStream();
	                
	                profileImg = BitmapFactory.decodeStream(is);
	                
	                
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	            return profileImg;
	        }
	        
	        protected void onPostExecute(Bitmap img){
	            profile.setImageBitmap(profileImg);
	        }
	        
	    }

	  
	  private class urlBg extends AsyncTask<String, Integer,Bitmap>{
	        
		
		  
	        @Override
	        protected Bitmap doInBackground(String... urls) {
	            // TODO Auto-generated method stub
	            try{
	                URL myFileUrl = new URL(urls[0]);
	                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
	                conn.setDoInput(true);
	                conn.connect();
	                
	                InputStream is = conn.getInputStream();
	                
	                profileBgBM = BitmapFactory.decodeStream(is);
	                
	                
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	            return profileBgBM;
	        }
	        
	        protected void onPostExecute(Bitmap img){
	        	  
	    		  BitmapDrawable background = new BitmapDrawable(profileBgBM);
	            profileBg.setBackgroundDrawable(background);
	        }
	        
	    }


	@Override
	public void NetworkUserCompleteResult(String statusFlag, UserData user,
			UserBaseData bdTmp) {
		// TODO Auto-generated method stub
		Log.d("NetworkUserCompleteResult menuListFragment",String.valueOf(bdTmp.getIngData()));
		
		baseData = bdTmp;
		if(bdTmp.getIngData()==null){
			completeBtn.setText("0");
			ingBtn.setText("0");
			
		}else{
		completeBtn.setText(String.valueOf(bdTmp.getCompleteData().size()));
		for (int i = 0 ;i<bdTmp.getCompleteData().size();i++){
			Log.d("NetworkUserCompleteResult menuListFragment",bdTmp.getCompleteData().get(i));
		}
		
		ingBtn.setText(String.valueOf(bdTmp.getIngData().size()));
		}
		Log.d("NetworkUserCompleteResult menuListFragment","end");
	}
	@Override
	public void NetWorkTaskCompeletResult() {
		// TODO Auto-generated method stub
		mainData.addAll(mainGetData.getData());
		userData.setMainData(mainData);
	}

}