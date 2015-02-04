package com.yessul.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.deser.impl.InnerClassProperty;
import com.kakao.UserProfile;

import android.R.string;
import android.os.Parcel;
import android.os.Parcelable;

public class UserData implements Parcelable {
	private UserProfile userProfile;
	private String userToken; //id
	private ArrayList<BaseData> mainData;
	private ArrayList<ContentsData> contentsData;

	public UserData() {
		mainData = new ArrayList<BaseData>();
    }
 
	public UserData(Parcel src){
		//userProfile = (UserProfile) src.readValue(null);
		//userToken = src.readString();
		contentsData = new ArrayList<ContentsData>();
		mainData = new ArrayList<BaseData>();
		src.readTypedList(mainData,BaseData.CREATOR);
		src.readTypedList(contentsData, ContentsData.CREATOR);
		
	
	}
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}


	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<BaseData> getMainData() {
		return mainData;
	}

	public void setMainData(ArrayList<BaseData> dt) {
		this.mainData.addAll(dt);
	}
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
		//dest.writeValue(userProfile);
		//dest.writeString(userToken);
		dest.writeTypedList(mainData);
		dest.writeTypedList(contentsData);

	}
//	private void readFromParcel(Parcel in){
//		 //userProfile = (UserProfile) in.readValue(null);
//		 //userToken = in.readString();
//		 mainData = in.readTypedList(mainData, InnerData);
//		
//	}
//	
	
	
	 public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
	        public UserData createFromParcel(Parcel in) {
	             return new UserData(in);
	       }

	       public UserData[] newArray(int size) {
	            return new UserData[size];
	       }
	   };


	public UserProfile getUserProfile() {
		return userProfile;
	}

	public ArrayList<ContentsData> getContetnsData() {
		return contentsData;
	}

	public void setContetnsData(ArrayList<ContentsData> content) {
		this.contentsData=content;
		
	}


	}
	

