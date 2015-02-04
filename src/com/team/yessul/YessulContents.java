package com.team.yessul;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.yessul.data.ContentsData;
import com.yessul.data.ExploreData;
import com.yessul.data.UserData;

public class YessulContents extends Activity {
DisplayImageOptions options;
ArrayList<ExploreData> exploreData = new ArrayList<ExploreData>();
LinearLayout yessulLayout; 
ArrayList<ContentsData> contentsMain = new ArrayList<ContentsData>();
ArrayList<ContentsData> contentsTmp = new ArrayList<ContentsData>();
ArrayList<ContentsData> contentsMainData = new ArrayList<ContentsData>();
UserData userData;
int index;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yessul_contents);
		
	         yessulLayout = (LinearLayout) findViewById(R.id.yessul_contents_layout);
		initImageLoader(getApplicationContext());  
		userData = new UserData();
		  Bundle bundle = getIntent().getExtras();
	         exploreData = bundle.getParcelableArrayList("ExploreData");
	         userData	  =bundle.getParcelable("UserData");
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
	  
		 Log.d("exploreData Size",String.valueOf(exploreData.get(index).getBackgroundUrl()));
		 for(int i = 0 ; i< exploreData.size();i++){
			 
			 if(exploreData.get(i).getIndex()!=-1){
		 index =exploreData.get(i).getIndex();
		 Log.d("index",String.valueOf(index));
		 
			 }
		 }
		
		 double lat = exploreData.get(index).getContetnsData().get(0).getLat();
		 double lng =exploreData.get(index).getContetnsData().get(0).getLng();
//		 for(int j = 0; j<exploreData.get(index).getContetnsData().size();j++){
			
 for(int i = exploreData.get(index).getContetnsData().size()-1 ; i< userData.getContetnsData().size();i++){
			
	 
//		 contentsMain.add(contentsMainData.get(i+index));
//		 Log.d("contentsMain",String.valueOf(contentsMainData.get(i+index)));
		 if((userData.getContetnsData().get(i).getLat()!=0) && (userData.getContetnsData().get(i).getLng()!=0)){
		  
			 
			 if((lat == userData.getContetnsData().get(i).getLat() )&& (lng== userData.getContetnsData().get(i).getLng())){
				 	
				 contentsTmp.add(userData.getContetnsData().get(i));
				 
				 Log.d("contentsMain size",String.valueOf(contentsTmp.size()));
				 Log.d("explore contentsData size",String.valueOf(exploreData.get(index).getContetnsData().size()));
//				 if(contentsMain.size()==exploreData.get(index).getContetnsData().size()+1){
//					 break;
//				 }
//			 }
		 }
		
		 }
		 
		 }
		 
 for(int i = 0 ;i<contentsTmp.size();i++){
	 
		
		 contentsMain.add(contentsTmp.get(i));
    
	 Log.d("contensMainData",contentsMain.get(i).getContentsUrl());
	 }
//			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			/*View Pager*/ 
		// if(!netGetData.tempData.isEmpty())
		 
		 new LoadBackground(exploreData.get(index).getBackgroundUrl(),"bg").execute();
			ViewPager pager = (ViewPager)findViewById(R.id.pagerYessul);
			
			pager.setAdapter(new ImageAdapter(this));
			

	        
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
		
			return contentsMain.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.yessul_item_pager_image, view, false);
		
			 
			assert imageLayout != null;
			com.team.yessul.SquareImageView ImageView = (com.team.yessul.SquareImageView) imageLayout.findViewById(R.id.imageYessul);
			ImageView.setScaleType(ScaleType.CENTER_CROP);
			
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loadingYessul);
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
	        yessulLayout.setBackgroundDrawable(result);
	       
	    }
	}
}
