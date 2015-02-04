package com.team.yessul;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ImageView.ScaleType;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.team.network.GetContentsData;
import com.yessul.apikey.MapApiKey;
import com.yessul.data.ContentsData;
import com.yessul.data.ExploreData;
import com.yessul.data.UserData;
import com.yessul.data.Coordinate;

public class ExploreContents extends TabActivity implements OnClickListener {
	  MyLocationListener listener;
	DisplayImageOptions options;
    UserData userData;
    LinearLayout exploreLayout;
    Button mapLocation;
    MapView mMapView ;
    ContentsData tmpContentsData;
    ContentsData contentsMaintmp;
    ArrayList<ContentsData> arrContentsData;
    ArrayList<Coordinate> coordinate;
    ArrayList<MapPoint> mPolyline2Points;
    ArrayList<ContentsData> contentsMain;
    ArrayList<ExploreData> exploreData;
    ContentsData tmpContentsData1;
    ExploreData exploreDatatmp;
    MapPoint[] mPolyline2PointsArr;
    ArrayList<ContentsData> contentsDataOriginal;
    LocationManager manager;
    Button mapBtn ;
    Button checkBtn;
    String bgUrl;
    ProgressDialog dialog ;
    int btnFlag = 0 ; //0 이야기 1 map 
    int location = 0;
    public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	
	}
	///////////////////////////////////////////////////////////////////////////////////
     TabHost tabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explore_contents);
		dialog= ProgressDialog.show(this, "", "탐방지 로딩중입니다. 잠시 기다려주세요");
		  Bundle bundle = getIntent().getExtras();
	      userData = bundle.getParcelable("UserData");
	      exploreLayout = (LinearLayout) findViewById(R.id.explore_contents_layout);
	      mapBtn = (Button) findViewById(R.id.btnMap);
	      Log.d("length", String.valueOf(userData.getMainData().size()));
	      int findDataIndex=userData.getContetnsData().get(0).getContentsIndex();
	      Log.d("index ", String.valueOf(findDataIndex));
	      Log.d("backGround Img","http://hywonder.cafe24.com:8001/uploads/administer/"+String.valueOf(findDataIndex+1)+"_background.jpg");
	      http://hywonder.cafe24.com:8001/uploads/administer/1_background.jpg
	    	  bgUrl = "http://hywonder.cafe24.com:8001/uploads/administer/"+String.valueOf(findDataIndex+1)+"_background.jpg";
	      new LoadBackground("http://hywonder.cafe24.com:8001/uploads/administer/"+String.valueOf(findDataIndex+1)+"_background.jpg","bg").execute();
	  
	    //  ImageLoader.getInstance().init(configuration);;       
	   //   initImageLoader(getApplicationContext());  
		    
			 options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(true)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300))
				.build();
			 	tmpContentsData = new ContentsData();
				Log.d("Explore Contents", "start");
				
			
					 manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
					  
			  
			 tabHost = getTabHost();
			  TabSpec tabSpecTab1= tabHost.newTabSpec("TAB1").setIndicator("지도보기");
			  tabSpecTab1.setContent(R.id.tab1);
			  tabHost.addTab(tabSpecTab1);
			  TabSpec tabSpecTab2= tabHost.newTabSpec("TAB2").setIndicator("이야기");
			  tabSpecTab2.setContent(R.id.tab2);
			  tabHost.addTab(tabSpecTab2);
			  TabSpec tabSpecTab3= tabHost.newTabSpec("TAB3").setIndicator("도장");
			  tabSpecTab3.setContent(R.id.tab3);
			  tabHost.addTab(tabSpecTab3);
			  
			  tabHost.setCurrentTab(1);
			  
			  mapBtn.setOnClickListener(this);
//				class mapFragment extends Fragment{
//					 
//			        @Override
//			        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			                Bundle savedInstanceState) {
//			            return inflater.inflate(R.layout.map_fragment, null);
//			        }
//			         
//			    }
			  mMapView = (MapView) findViewById(R.id.map_view);

		        mMapView.setDaumMapApiKey(MapApiKey.DAUM_MAPS_ANDROID_APP_API_KEY);

			  /*map*/
			  double lng=0;
			  double lat=0;
			 mPolyline2Points = new ArrayList<MapPoint>();
			 coordinate = new ArrayList<Coordinate>();
			 contentsMain = new ArrayList<ContentsData>();
			 arrContentsData = new ArrayList<ContentsData>();
			 exploreData =new ArrayList<ExploreData>();
			 contentsDataOriginal = new ArrayList<ContentsData>();
			
			// bgJob = new ExploreContentsBackGround(this,userData);	
		//	 bgJob.execute();
			 
		    
		       exploreDatatmp = new ExploreData();
		       arrContentsData = new ArrayList<ContentsData>();
		       
		        int j;
		        int k;
		        int g = 0;
		        
		        for(j=0;j<userData.getContetnsData().size();j++){
		        	contentsMaintmp = new ContentsData();
		        	if(userData.getContetnsData().get(j).getLat()==0 && userData.getContetnsData().get(j).getLng()==0){
		        		contentsMaintmp.setContentsUrl(userData.getContetnsData().get(j).getContentsUrl());
				        contentsMaintmp.setLat(lat);
				        contentsMaintmp.setLng(lng);
				        contentsMain.add(contentsMaintmp);
				        contentsMaintmp = null;
		        	}
		        else if(userData.getContetnsData().get(j).getLat()!=0 && userData.getContetnsData().get(j).getLng()!=0){
		        			k=j+1;
		        			int l =0;
		        			lat =userData.getContetnsData().get(j).getLat() ;
					        lng = userData.getContetnsData().get(j).getLng();
					        contentsMaintmp.setContentsUrl(userData.getContetnsData().get(j).getContentsUrl());
					        contentsMaintmp.setLat(lat);
					        contentsMaintmp.setLng(lng);
					        contentsMain.add(contentsMaintmp);
					        
					        contentsMaintmp = null;
		        			while(userData.getContetnsData().get(k).getLat()==lat&&userData.getContetnsData().get(k).getLng()==lng){
		        				tmpContentsData.setContentsUrl(userData.getContetnsData().get(k).getContentsUrl());
						        tmpContentsData.setLat(userData.getContetnsData().get(k).getLat());
						        tmpContentsData.setLng(userData.getContetnsData().get(k).getLng());
						        tmpContentsData.setLocationId(userData.getContetnsData().get(k).getLocationId());
								 Log.d("exploreDataTmp1",userData.getContetnsData().get(k).getContentsUrl());
		        				k++;
		        				contentsDataOriginal.add(tmpContentsData);
		        				arrContentsData.add(tmpContentsData);
		        				
		        				l++;
		        				
		        			}
		        			exploreDatatmp.setContetnsData(arrContentsData);
	        				 exploreData.add(exploreDatatmp);
					        Log.d("while 1", "?");
					      
					        
			     
//					        
//					        exploreData.add(exploreDatatmp);
		        			  Log.d("while 1", "pass ");
					        mPolyline2Points.add(MapPoint.mapPointWithGeoCoord(lat, lng));	
					        break;
				        
		        	 }
		        }
		        
		        Log.d("data",exploreDatatmp.getContetnsData().get(0).getContentsUrl());
		        for(int i = j;i< userData.getContetnsData().size();i++){
		        	contentsMaintmp = new ContentsData();
		        
		        	 if(userData.getContetnsData().get(i).getLat()!=lat && userData.getContetnsData().get(i).getLng()!=lng){
		        			lat =userData.getContetnsData().get(i).getLat() ;
					        lng = userData.getContetnsData().get(i).getLng();
		        		 contentsMaintmp.setContentsUrl(userData.getContetnsData().get(i).getContentsUrl());
					        contentsMaintmp.setLat(lat);
					        contentsMaintmp.setLng(lng);
					        contentsMain.add(contentsMaintmp);
					        Log.d("loop ", String.valueOf(i));
		        			k=i+1;
		        		
		        			 tmpContentsData1 = new ContentsData();
					       arrContentsData = new ArrayList<ContentsData>();
					       exploreDatatmp =new ExploreData();
					       int l =0;
		        			while(userData.getContetnsData().get(k).getLat()==lat&&userData.getContetnsData().get(k).getLng()==lng && k< userData.getContetnsData().size()){
		        				tmpContentsData1.setContentsUrl(userData.getContetnsData().get(k).getContentsUrl());
		        				
		        				tmpContentsData1.setLat(userData.getContetnsData().get(k).getLat());
		        				tmpContentsData1.setLng(userData.getContetnsData().get(k).getLng());
		        				tmpContentsData1.setLocationId(userData.getContetnsData().get(k).getLocationId());
								 Log.d("exploreDataTmp1",userData.getContetnsData().get(k).getContentsUrl());
		        				k++;
		        				contentsDataOriginal.add(tmpContentsData1);
		        				arrContentsData.add(tmpContentsData1);
		        				
		        			//	Log.d("exploreDatatmp check",exploreDatatmp.getContetnsData().get(l).getContentsUrl()); 
		        				Log.d("Url",String.valueOf(	arrContentsData.get(l).getContentsUrl()));
		        				l++;
		        				Log.d("loop k", String.valueOf(k));
		        				
		        				if(k>=userData.getContetnsData().size()){
		        					break;
		        				}
		        				
		        			}
		        			exploreDatatmp.setContetnsData(arrContentsData);
		        			exploreData.add(exploreDatatmp);
		        	
		        			
		        			
		        			 
		        			 exploreDatatmp=null;
		        	mPolyline2Points.add(MapPoint.mapPointWithGeoCoord(lat, lng));
		       
		        	  
		        }
		       
		        }
		     	for(int a = 0 ;a<exploreData.size();a++){		        		
		     		for(int q=0;q<exploreData.get(a).getContetnsData().size();q++){
		        	Log.d("exploreData check3",exploreData.get(a).getContetnsData().get(q).getContentsUrl());
		        	
		     		}
		     		}
		        Log.d("explore Data tmp size",String.valueOf(exploreData.size()));
		        Log.d("mPolyline2Points count",String.valueOf(mPolyline2Points.size()));
		   
		     
				/*View Pager*/ 
			    mPolyline2PointsArr = new MapPoint[mPolyline2Points.size()];
			    
		        addPolyline2();
				 ViewPager pager = (ViewPager)findViewById(R.id.pagerContents);
					
					pager.setAdapter(new ImageAdapter(this));
	}
	 private void addPolyline2() {
	        MapPOIItem existingPOIItemStart = mMapView.findPOIItemByTag(10001);
	        if (existingPOIItemStart != null) {
	            mMapView.removePOIItem(existingPOIItemStart);
	        }

	        MapPOIItem existingPOIItemEnd = mMapView.findPOIItemByTag(10002);
	        if (existingPOIItemEnd != null) {
	            mMapView.removePOIItem(existingPOIItemEnd);
	        }

	        MapPolyline existingPolyline = mMapView.findPolylineByTag(2000);
	        if (existingPolyline != null) {
	            mMapView.removePolyline(existingPolyline);
	        }
	      
           /* marker start*/
	   ArrayList<MapPOIItem> poiItemArr = new ArrayList<MapPOIItem>();
	
	    MapPOIItem[] poiItem = new MapPOIItem[mPolyline2Points.size()];
	        for(int i=0 ; i<mPolyline2Points.size();i++){
	        	Log.d("marker",String.valueOf(i));
	        	   MapPOIItem poiTemp = new MapPOIItem();
	        	poiTemp.setItemName(String.valueOf((i+1))+"번째 장소");
	        	poiTemp.setTag(10001);
	        	poiTemp.setMapPoint(MapPoint.mapPointWithGeoCoord(mPolyline2Points.get(i).getMapPointGeoCoord().latitude,mPolyline2Points.get(i).getMapPointGeoCoord().longitude));
	        	Log.d("marker coordinate",String.valueOf(mPolyline2Points.get(i).getMapPointGeoCoord().latitude)+String.valueOf(mPolyline2Points.get(i).getMapPointGeoCoord().longitude));
	        	poiTemp.setMarkerType(MapPOIItem.MarkerType.BluePin);
	        	poiTemp.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
	        	poiTemp.setShowCalloutBalloonOnTouch(true);
		        //poiItem.setCustomImageResourceId(R.drawable.custom_poi_marker_start);   poiItemStart.setMarkerType(MapPOIItem.MarkerType.CustomImage);
	        	poiTemp.setCustomImageAnchorPointOffset(new MapPOIItem.ImageOffset(29, 2));
		     poiItemArr.add(poiTemp);
	        }
	        mMapView.addPOIItems(poiItemArr.toArray(poiItem));
	        MapPolyline polyline2 = new MapPolyline(21);
	        polyline2.setTag(2000);
	        polyline2.setLineColor(Color.argb(128, 0, 0, 255));
	        polyline2.addPoints( mPolyline2Points.toArray(mPolyline2PointsArr));
	        mMapView.addPolyline(polyline2);
	        
	     

	        final MapPointBounds mapPointBounds = new MapPointBounds( mPolyline2Points.toArray(mPolyline2PointsArr));
	        final int padding =100; // px
	        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
	        mapLocation =(Button) findViewById(R.id.location);
	        mapLocation.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(location==0){
						mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
						location =1;
					}if(location ==1){
						  mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
						  location=0;
					}
				
				
				}
			});
	    }
	private class ImageAdapter extends PagerAdapter implements  OnClickListener {

		private LayoutInflater inflater;
		

		
		ImageAdapter(Context con) {
			inflater = LayoutInflater.from(con);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			Log.d("getCount() ", String.valueOf(userData.getContetnsData().size()));
			return contentsMain.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.explore_item_pager_image, view, false);
			  checkBtn = (Button) imageLayout.findViewById(R.id.btnCheck);
			 
			assert imageLayout != null;
			SquareImageView ImageView = (SquareImageView) imageLayout.findViewById(R.id.imageExplore);
			ImageView.setScaleType(ScaleType.CENTER_CROP);
			
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loadingExplore);
			//ImageSize minImageSize = new ImageSize(screenWidth, screenHeight);
		//	ImageSize minImageSize = new ImageSize(100, 	200);
			final int selectedPosition=position; //when click is done , then the selectedPosition
			
			Log.d("start ImageLoader", "start");
			ImageLoader.getInstance().displayImage(contentsMain.get(position).getContentsUrl(), ImageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}
				
				/**/
				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					String message = null;
					switch (failReason.getType()) {
						case IO_ERROR:
							message = "Input/Output error";
							break;
						case DECODING_ERROR:
							message = "Image can't be decoded";
							break;
						case NETWORK_DENIED:
							message = "Downloads are denied";
							break;
						case OUT_OF_MEMORY:
							message = "Out Of Memory error";
							break;
						case UNKNOWN:
							message = "Unknown error";
							break;
					}
					//Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

					spinner.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					spinner.setVisibility(View.GONE);
					/*버튼*/
					if(contentsMain.get(selectedPosition).getLat()!=0 && contentsMain.get(selectedPosition).getLng()!=0){
						checkBtn.setVisibility(View.VISIBLE);
					}
				}
			});
			
			checkBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					  getMyLocation(selectedPosition); //자신의 좌표 불러오기 .
					 
				}
			});
			
			view.addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	
	private class LoadBackground extends AsyncTask<String, Void, Drawable> {

	    private String imageUrl , imageName;
	    
	    public LoadBackground(String url, String imageName) {
	        this.imageUrl = url;
	       this.imageName= imageName;
	      
	    }

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }

	    @Override
	    protected Drawable doInBackground(String... urls) {

	        try {
	            InputStream is = (InputStream) this.fetch(this.imageUrl);
	            Drawable d = Drawable.createFromStream(is, this.imageName);
	            return d;
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	            return null;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    private Object fetch(String address) throws MalformedURLException,IOException {
	        URL url = new URL(address);
	        Object content = url.getContent();
	        return content;
	    }

	    @SuppressWarnings("deprecation")
	    @Override
	    protected void onPostExecute(Drawable result) {
	        super.onPostExecute(result);
	        Log.d("LoadBg", "bg complete");
	        exploreLayout.setBackgroundDrawable(result);
	        dialog.dismiss();
	    }
	}
	 private void getMyLocation(int index) {
		  if (manager == null) {
		   manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		  }
		  // provider 기지국||GPS 를 통해서 받을건지 알려주는 Stirng 변수
		  // minTime 최소한 얼마만의 시간이 흐른후 위치정보를 받을건지 시간간격을 설정 설정하는 변수
		  // minDistance 얼마만의 거리가 떨어지면 위치정보를 받을건지 설정하는 변수
		  // manager.requestLocationUpdates(provider, minTime, minDistance, listener);
		 
		  // 10초
		  long minTime = 1000;
		   
		  // 거리는 0으로 설정 
		  // 그래서 시간과 거리 변수만 보면 움직이지않고 10초뒤에 다시 위치정보를 받는다
		  float minDistance = 0;
		 
		  listener = new MyLocationListener(index);
		 
		  manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
		  manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, listener);
		  dialog= ProgressDialog.show(this, "", "위치 표시중 입니다. (GPS를 켜주세요)");
		 Log.d("위치요청","위치요청");
		 Toast.makeText(this, "위치요청", 3);
		 }
	 class MyLocationListener implements android.location.LocationListener {
		  int index = -1;
		  // 위치정보는 아래 메서드를 통해서 전달된다.
		  @Override
		  public void onLocationChanged(Location location) {
		 //  appendText("onLocationChanged()가 호출되었습니다");
		 
		   double latitude = location.getLatitude();
		   double longitude = location.getLongitude();
		   /* */
		   Log.d("인덱스 ",String.valueOf(index));
		   Log.d("컨텐츠 비석 위치",String.valueOf(contentsMain.get(index).getLng())+"  "+String.valueOf(contentsMain.get(index).getLat()));
		   Log.d("거리",String.valueOf(getDistance(latitude,longitude,contentsMain.get(index).getLng(),contentsMain.get(index).getLat())));
		   if(getDistance(latitude,longitude,contentsMain.get(index).getLng(),contentsMain.get(index).getLat())>100){
			  
			 
			 
			
			   for(int j = 0 ; j<exploreData.size();j++){
 if( (contentsMain.get(index).getLat()== exploreData.get(j).getContetnsData().get(0).getLat()) && (contentsMain.get(index).getLng()== exploreData.get(j).getContetnsData().get(0).getLng())){
	exploreData.get(j).setIndex(j);

 	}
				   exploreData.get(j).setBackgroundUrl(bgUrl);
				  
			   }
			   overridePendingTransition(android.R.anim.slide_in_left, R.anim.disappear_to_left);
			   Intent i =new Intent(getApplication().getBaseContext(),YessulContents.class);
			   i.putParcelableArrayListExtra("ExploreData", exploreData);
				i.putExtra("UserData", userData);
				  i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
				   manager.removeUpdates(listener);
				   dialog.dismiss();
				    startActivity(i);
			    
				
			   
		   } else{
			   Toast.makeText(getApplication().getBaseContext(), "가까운 장소로 가서 버튼을 눌러주세요", 2);
		   }
		   
		   Log.d("현재 위치 좌표",String.valueOf(latitude)+"  "+String.valueOf(longitude));
		   manager.removeUpdates(listener);
		 Toast.makeText(getApplicationContext(), String.valueOf(latitude).concat(String.valueOf(longitude)), 2);
		   //appendText("현재 위치:" + latitude + "," + longitude);
		  }
		  MyLocationListener(int index){
			 
			  this.index = index;
		  }

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
	
		 
	 }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(btnFlag ==0 ){
		tabHost.setCurrentTab(1);
		mapBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.map_btn_selector));
		Log.d("지도보기",String.valueOf(btnFlag));
		btnFlag =1;
		}		else if(btnFlag ==1){
			tabHost.setCurrentTab(0);
			mapBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.story_btn_selector));
			Log.d("이야기",String.valueOf(btnFlag));
			btnFlag =0;	
			
		}
  	}
//	@Override
//	public void ExploreConentsBgJobComplete( ArrayList<Coordinate> coordinate,
//		    ArrayList<MapPoint> mPolyline2Points,
//		    ArrayList<ContentsData> contentsMain,
//		    ArrayList<ExploreData> exploreData) {
//		// TODO Auto-generated method stub
//		this.coordinate = coordinate;
//		this.mPolyline2Points = mPolyline2Points;
//		this. contentsMain = contentsMain;
//		this.exploreData 
//		   addPolyline2();
//		
//		
//		
//	}
	private double getDistance(double sLat, double sLong, double dLat, double dLong)
	{
	    final int radius=6371009;
	    double uLat=Math.toRadians(sLat-dLat);
	    double uLong=Math.toRadians(sLong-dLong);

	    double a = Math.sin(uLat/2) * Math.sin(uLat/2) + 
	            Math.cos(Math.toRadians(sLong)) * Math.cos(Math.toRadians(dLong)) *  
	            Math.sin(uLong/2) * Math.sin(uLong/2);  

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));  
	    double distance = radius * c;

	    return distance/1000;
	}

}


