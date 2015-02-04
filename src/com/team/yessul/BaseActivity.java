package com.team.yessul;
 
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.kakao.UserProfile;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yessul.data.UserData;
 
public class BaseActivity extends SlidingFragmentActivity   {
    Button btnToggle;
    protected Fragment mFrag;
    Bundle args; 
	
private ViewFlipper m_viewFlipper;
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
	private int m_nPreTouchPosX = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//final NetworkGetData netGetData = new NetworkGetData();
		//netGetData.getMain();
    	args = new Bundle();
        super.onCreate(savedInstanceState);
     
       // getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        initImageLoader(getApplicationContext());
    
       /* getSupportActionBar().setDisplayShowCustomEnabled(true);
 		
   	 getSupportActionBar().setDisplayHomeAsUpEnabled(false);
   	 getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.action_bar_title);
		*/
		//Drawable d = getResources().getDrawable(R.drawable.main_top);
		//getActionBar().setBackgroundDrawable(d);
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.action_bar_title);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.main_top));
        
		//getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
		//getSupportActionBar().setIcon(R.drawable.main_menu);
		getActionBar().show();
		btnToggle = (Button) findViewById(R.id.btnToggle);

		btnToggle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggle();
			}
		});		
		 fragmentReplace(0);
        // set the Behind View
		 
		setBehindContentView(R.layout.menu_frame);
																										
        																										
        if (savedInstanceState == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            mFrag = new MenuListFragment();
            t.replace(R.id.menu_frame, mFrag);
           
         
            t.commit();
        } else {
            mFrag = (Fragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
            
            
        }
 
        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //sm.setAboveOffset(10);
        sm.setFadeDegree(0.35f); //0.35f
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
 
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

   

        return super.onOptionsItemSelected(item);

    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
     
 
    public void fragmentReplace(int reqNewFragmentIndex) {
          
        Fragment newFragment = null; 
        newFragment = getFragment(0);
  
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
  
       transaction.replace(R.id.fragment_mainContainer, newFragment);
  
        getSlidingMenu().showContent();
        transaction.commit();
    }
     
    private Fragment getFragment(int idx) {
        Fragment newFragment = null;
  
        switch (idx) {
        case 0:
            newFragment = new Fragment1();
            break;
        case 1:
            newFragment = new Fragment2();
            break;
        case 2:
            newFragment = new Fragment3();
            break;
        default:
            break;
        }
  
        return newFragment;
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}
  
}