package com.team.yessul;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.team.network.GetContentsData;
import com.team.network.GetContentsData.NetWorkTaskCompelet;
import com.yessul.data.BaseData;
import com.yessul.data.ContentsData;
import com.yessul.data.UserData;

public class ContentsGrid extends Activity implements NetWorkTaskCompelet {
	
	private Object options;
	private GridView listView;
	public static final int INDEX = 1;
	String[] baseData;
	UserData userDataUsing = new UserData();
	UserData userData = new UserData();
	GetContentsData contentsData;
	NetWorkTaskCompelet listener;
	 ArrayList<BaseData> tmpBd;
	private ImageView convertView; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		   setContentView(R.layout.fr_image_grid);
		   
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
	
		 convertView = new ImageView( getBaseContext() );
	      convertView.setLayoutParams( new GridView.LayoutParams( 300, 300 ) );
		 Bundle bundle = getIntent().getExtras();
		 userData = bundle.getParcelable("UserData");
         baseData = bundle.getStringArray("CmpData");
         //Log.d("gridView",);
          tmpBd = new ArrayList<BaseData>();
          listener =this;
        	 for(int j = 0 ;j<baseData.length;j++){
        			for(int i = 0; i<userData.getMainData().size();i++){
        	if(userData.getMainData().get(i).getContents_id().equals(baseData[j])){
        		tmpBd.add(userData.getMainData().get(i));
        		
        	}
        	 }
        	 }
        	 userDataUsing.setMainData(tmpBd);
	
			
		listView = (GridView) findViewById(R.id.grid);
	
		((GridView) listView).setAdapter(new ImageAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				contentsData = new GetContentsData(listener,userData.getMainData().get(position).getContents_id(),position);
				contentsData.execute(); // contents data	
				//startImagePagerActivity(position);
			}
		});
Log.d("dd","시작");
	}

//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View rootView = inflater.inflate(R.layout.fr_image_grid, container, false);
//		
//     
//		listView = (GridView) rootView.findViewById(R.id.grid);
//		((GridView) listView).setAdapter(new ImageAdapter());
//		listView.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				
//				
//				//startImagePagerActivity(position);
//			}
//		});
//		return rootView;
//	}

	public class ImageAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		

		ImageAdapter() {
			inflater = LayoutInflater.from(getApplication());
		}

		@Override
		public int getCount() {
			Log.d("GridView size",String.valueOf(userDataUsing.getMainData().size()));
		return userDataUsing.getMainData().size();
		
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = inflater.inflate(R.layout.item_grid_image, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (com.team.yessul.SquareImageView) view.findViewById(R.id.image);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			ImageLoader.getInstance().displayImage(userDataUsing.getMainData().get(position).getMinicover(), holder.imageView, (DisplayImageOptions) options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							holder.progressBar.setProgress(0);
							holder.progressBar.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
							holder.progressBar.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							holder.progressBar.setVisibility(View.GONE);
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri, View view, int current, int total) {
							holder.progressBar.setProgress(Math.round(100.0f * current / total));
						}
					});

			return view;
		}
	}

	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}

	@Override
	public void NetWorkTaskCompeletResult(ArrayList<ContentsData> td) {
		// TODO Auto-generated method stub
		userData.setContetnsData(td);
		contentsData.cancel(true);
		Log.d("Contents Complete", "complete");
		Log.d("Contents dd", String.valueOf(userData.getContetnsData().get(0).getContentsIndex()));		
			Intent i =new Intent(ContentsGrid.this,ExploreContents.class);
			i.putExtra("UserData", userData);
			
			  i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
			  
			    startActivity(i);
			overridePendingTransition(android.R.anim.slide_in_left, R.anim.disappear_to_left);	
		
		
		
	}
}
