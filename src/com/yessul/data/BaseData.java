package com.yessul.data;

import java.util.ArrayList;

import com.google.android.gms.internal.go;

import android.R.string;
import android.os.Parcel;
import android.os.Parcelable;

public class BaseData implements Parcelable{
	private String mainCover;
	private String contents_id;
	private String bgCover;
	private String startCover;
	private String minicover;
	private ArrayList<String> completeData;
	private ArrayList<String> ingData;
	private String cmpData;
	private String goingData;
	public BaseData(){
		completeData = new ArrayList<String>();
		ingData = new ArrayList<String>();
	}
	
	   public BaseData(String img, String id,String bgCover,String startCover,ArrayList<String >cmpData,ArrayList<String> goingData, String miniCover) {
		   completeData = new ArrayList<String>();
			ingData = new ArrayList<String>();
		   this.mainCover = img;
	        this.contents_id = id;
	        this.completeData = cmpData;
	        this.ingData= goingData;
	        //this.bgCover = bgCover;
	    //    this.startCover = startCover;
	        this.minicover = miniCover;
	        }
	     
	    @Override
	    public int describeContents() {
	        return 0;
	    }
	    @Override
		public void writeToParcel(Parcel desc, int flags) {
			// TODO Auto-generated method stub
			desc.writeString(mainCover);
			desc.writeString(contents_id);
			desc.writeStringList(completeData);
			desc.writeStringList(ingData);
			
			//desc.writeString(bgCover);
		//	desc.writeString(startCover);
			desc.writeString(minicover);
	    }
	    @SuppressWarnings("unchecked")
		private BaseData(Parcel src) {
	    	completeData = new ArrayList<String>();
			ingData = new ArrayList<String>();
	    	mainCover= src.readString();
	    	contents_id = src.readString();	   	    
	    	completeData= src.readArrayList(String.class.getClassLoader());
	    	ingData =  src.readArrayList(String.class.getClassLoader());
	    	minicover=src.readString();
	    }
	    public static final Parcelable.Creator<BaseData> CREATOR = new Parcelable.Creator<BaseData>() {
	        @Override
	        public BaseData createFromParcel(Parcel source) {
	            return new BaseData(source);
	        }
	        @Override
	        public BaseData[] newArray(int size) {
	            return new BaseData[size];
	        }
	    };
	
	
	
	
	/*setter and getter*/
	public String getContents_img() {
		return mainCover;
	}
	public void setContents_img(String contents_img) {
		this.mainCover = contents_img;
	}
	public String getContents_id() {
		return contents_id;
	}
	public void setContents_id(String contents_id) {
		this.contents_id = contents_id;
	}

	public String getBgCover() {
		return bgCover;
	}

	public void setBgCover(String bgCover) {
		this.bgCover = bgCover;
	}

	public String getStartCover() {
		return startCover;
	}

	public void setStartCover(String startCover) {
		this.startCover = startCover;
	}

	public String getMinicover() {
		return minicover;
	}

	public void setMinicover(String minicover) {
		this.minicover = minicover;
	}

	public ArrayList<String> getCompleteData() {
		return completeData;
	}

	public void setCompleteData(ArrayList<String> completeData) {
		this.completeData = completeData;
	}

	public ArrayList<String> getIngData() {
		return ingData;
	}

	public void setIngData(ArrayList<String> ingData) {
		this.ingData = ingData;
	}

	public String getCmpData() {
		return cmpData;
	}

	public void setCmpData(String cmpData) {
		this.cmpData = cmpData;
	}

	public String getGoingData() {
		return goingData;
	}

	public void setGoingData(String goingData) {
		this.goingData = goingData;
	}

	
	
}
