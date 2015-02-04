package com.yessul.data;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Parcel;
import android.os.Parcelable;

public class ExploreData implements Parcelable{
	private ArrayList<ContentsData> contetnsData;
	private String BackgroundUrl;
	private int index;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flag) {
		// TODO Auto-generated method stub
		 
		dest.writeTypedList(contetnsData);
		dest.writeInt(index);
	}
	 public ExploreData(Parcel src){
		// TODO Auto-generated constructor stub
		//userProfile = (UserProfile) src.readValue(null);
		//userToken = src.readString();
		 
		 contetnsData = new ArrayList<ContentsData>();
		src.readTypedList(contetnsData, ContentsData.CREATOR);
		index=src.readInt();
	}
	 public ExploreData(){
			// TODO Auto-generated constructor stub
			//userProfile = (UserProfile) src.readValue(null);
			//userToken = src.readString();
		 contetnsData = new ArrayList<ContentsData>();
			this.index = -1;
		}
	public ArrayList<ContentsData> getContetnsData() {
		return contetnsData;
	}

	public void setContetnsData(ArrayList<ContentsData>  object) {
		this.contetnsData.addAll(object);
		
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getBackgroundUrl() {
		return BackgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		BackgroundUrl = backgroundUrl;
	}
	public static final Parcelable.Creator<ExploreData> CREATOR = new Parcelable.Creator<ExploreData>() {
	    @Override
	    public ExploreData createFromParcel(Parcel source) {
	        return new ExploreData(source);
	    }
	    @Override
	    public ExploreData[] newArray(int size) {
	        return new ExploreData[size];
	    }
	};
}
