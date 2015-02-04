package com.team.yessul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.team.network.GetContentsData;
import com.team.network.MainGetData.NetWorkTaskCompelet;
import com.yessul.data.ContentsData;
import com.yessul.data.UserData;


public class MainPage extends BaseActivity implements  com.team.network.GetContentsData.NetWorkTaskCompelet {
	UserData userData;

	int screenWidth;
	int screenHeight;
	DisplayMetrics metrics;
	public static  GetContentsData contentsData ;
	
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
	DisplayImageOptions options;
	public UserData userDataGet(){
		return this.userData;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		  Bundle bundle = getIntent().getExtras();
	         userData = bundle.getParcelable("UserData");
	 		//getSupportFragmentManager().findFragmentById(R.id.menu_frame).setArguments(bundle);
		initImageLoader(getApplicationContext());  
	    
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
	
//			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			/*View Pager*/ 
		// if(!netGetData.tempData.isEmpty())
		 metrics = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(metrics);
		 screenWidth = metrics.widthPixels;
		 screenHeight = metrics.heightPixels;
			ViewPager pager = (ViewPager)findViewById(R.id.pager);
			
			pager.setAdapter(new ImageAdapter(this));
			

	        
	}
	
	
	
	
	
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View rootView = inflater.inflate(R.layout.fr_image_pager, container, false);
//		
//		// NetworkGetData.tempData.clear();
//		// NetworkGetData.tempData.add("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg");
//		ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
//		pager.setAdapter(new ImageAdapter());
//		
//		//pager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));
//		return rootView;
//	}
	
	private class ImageAdapter extends PagerAdapter  {

		private LayoutInflater inflater;
		 int   selectedPostion_;
	
		ImageAdapter(Context con) {
			inflater = LayoutInflater.from(con);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return userData.getMainData().size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
		
			 
			assert imageLayout != null;
			ImageView ImageView = (ImageView) imageLayout.findViewById(R.id.image);
			
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			//ImageSize minImageSize = new ImageSize(screenWidth, screenHeight);
		//	ImageSize minImageSize = new ImageSize(100, 	200);
			final int selectedPosition=position; //when click is done , then the selectedPosition
			selectedPostion_ =selectedPosition;
			imageLayout.setTag(MainPage.this);
			ImageLoader.getInstance().displayImage(userData.getMainData().get(position).getContents_img(), ImageView, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					spinner.setVisibility(View.VISIBLE);
				}
				
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
				}
			});
			
			ImageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					contentsData = new GetContentsData((com.team.network.GetContentsData.NetWorkTaskCompelet) MainPage.this,userData.getMainData().get(selectedPosition).getContents_id(),selectedPosition);
				contentsData.execute(); // contents data	
					Log.d("Click GetContentsData Networking Start",userData.getMainData().get(selectedPosition).getContents_id());
				}
			});		
//			@Override
			
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

	
//		public void onClick(View arg0) {
//			// TODO Auto-generated method stub
//			Log.d("Click GetContentsData Networking Start",arg0.getTag().toString());
//			contentsData = new GetContentsData(this,userData.getMainData().get(Integer.parseInt(arg0.getTag().toString())).getContents_id());
//			contentsData.execute(); // contents data	
//			Log.d("Click GetContentsData Networking Start",userData.getMainData().get(Integer.parseInt(arg0.getTag().toString())).getContents_id());
//		 
//	}
		


	

	
}

	@Override
	public void NetWorkTaskCompeletResult(ArrayList<ContentsData> td) {
		// TODO Auto-generated method stub
	 //   Log.d("객체",contentsData.toString());
	//	Log.d("Network End -> check Contents val : ", contentsData.getData().get(0).getLocationId().toString());
		
		//userData1.setContetnsData();
		Log.d("data length", td.toString());
		userData.setContetnsData(td);
		contentsData.cancel(true);
		Log.d("Contents Complete", "complete");
		Log.d("Contents dd", String.valueOf(userData.getContetnsData().get(0).getContentsIndex()));		
			Intent i =new Intent(MainPage.this,ExploreContents.class);
			i.putExtra("UserData", userData);
			
			  i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			  
			    startActivity(i);
			overridePendingTransition(android.R.anim.slide_in_left, R.anim.disappear_to_left);	
			
		
		
	}



	
}
