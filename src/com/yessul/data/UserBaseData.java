package com.yessul.data;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBaseData implements Parcelable {
	private ArrayList<String> completeData = new ArrayList<String>();
	private ArrayList<String> ingData = new ArrayList<String>();
	
	public UserBaseData(ArrayList<String> cmpData, ArrayList<String> ingData) {
		this.completeData = cmpData;
		this.ingData =ingData;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
public UserBaseData(Parcel source) {
	// TODO Auto-generated constructor stub
	 
	ingData = new ArrayList<String>();
	completeData=source.readArrayList(String.class.getClassLoader());
	ingData=source.readArrayList(String.class.getClassLoader());
}
	public UserBaseData() {
	// TODO Auto-generated constructor stub
}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	dest.writeStringList(completeData);
	dest.writeStringList(ingData);
	}
	
	 public static final Parcelable.Creator<UserBaseData> CREATOR = new Parcelable.Creator<UserBaseData>() {
	        @Override
	        public UserBaseData createFromParcel(Parcel source) {
	            return new UserBaseData(source);
	        }
	        @Override
	        public UserBaseData[] newArray(int size) {
	            return new UserBaseData[size];
	        }
	    };
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

}
