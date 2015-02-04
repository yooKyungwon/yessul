package com.yessul.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ContentsData implements Parcelable{
private String contentsId;
private String contentsUrl;
private String locationId;
private int contentsStatus;
private int contentsIndex;
private double lat;
private double lng;
public  ContentsData() {
	// TODO Auto-generated constructor stub
}

   public ContentsData(String contentsUrl, String locationId,int contentsStatus,double lat, double lng) {
        this.contentsUrl = contentsUrl;
        this.locationId = locationId;
        this.contentsStatus=contentsStatus;
        this.lat = lat;
        this.lng = lng;
    }
   private ContentsData(Parcel src) {
   	contentsUrl= src.readString();
   	locationId = src.readString();
   	contentsStatus = src.readInt();
   	lat = src.readDouble();
   	lng = src.readDouble();
   	contentsIndex=src.readInt();
   }
@Override
public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public void writeToParcel(Parcel dest, int flags) {
	// TODO Auto-generated method stub
	dest.writeString(contentsUrl);
	dest.writeString(locationId);
	dest.writeInt(contentsStatus);
	dest.writeDouble(lat);
	dest.writeDouble(lng);
	dest.writeInt(contentsIndex);
}


/*Getter Setter*/
public String getContentsUrl() {
	return contentsUrl;
}
public void setContentsUrl(String contentsUrl) {
	this.contentsUrl = contentsUrl;
}
public String getLocationId() {
	return locationId;
}
public void setLocationId(String locationId) {
	this.locationId = locationId;
}
public int getContentsStatus() {
	return contentsStatus;
}
public void setContentsStatus(int contentsStatus) {
	this.contentsStatus = contentsStatus;
}
public double getLat() {
	return lat;
}
public void setLat(double lat) {
	this.lat = lat;
}
public double getLng() {
	return lng;
}
public void setLng(double lng) {
	this.lng = lng;
}

public static final Parcelable.Creator<ContentsData> CREATOR = new Parcelable.Creator<ContentsData>() {
    @Override
    public ContentsData createFromParcel(Parcel source) {
        return new ContentsData(source);
    }
    @Override
    public ContentsData[] newArray(int size) {
        return new ContentsData[size];
    }
};
public String getContentsId() {
	return contentsId;
}

public void setContentsId(String contentsId) {
	this.contentsId = contentsId;
}

public int getContentsIndex() {
	return contentsIndex;
}

public void setContentsIndex(int contentsIndex) {
	this.contentsIndex = contentsIndex;
}





}
