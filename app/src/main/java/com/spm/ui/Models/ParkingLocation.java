package com.spm.ui.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ParkingLocation implements Parcelable {
    private final Long id;
    private final String name;
    private final String address;
    private final int totalSpaces;
    private final int numberOfAvailableSpaces;
    private final String mapImage;

    ParkingLocation(Long id, String name, String address, int totalSpaces, int numberOfAvailableSpaces, String mapImage) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.totalSpaces = totalSpaces;
        this.numberOfAvailableSpaces = numberOfAvailableSpaces;
        this.mapImage = mapImage;
    }

    protected ParkingLocation(Parcel in) {
        id = in.readLong();
        name = in.readString();
        address = in.readString();
        totalSpaces = in.readInt();
        numberOfAvailableSpaces = in.readInt();
        mapImage = in.readString();
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String address() {
        return address;
    }

    public int totalSpaces() {
        return totalSpaces;
    }

    public int numberOfAvailableSpaces() {
        return numberOfAvailableSpaces;
    }

    public String mapImage() {
        return mapImage;
    }

    public static final Creator<ParkingLocation> CREATOR = new Creator<ParkingLocation>() {
        @Override
        public ParkingLocation createFromParcel(Parcel in) {
            return new ParkingLocation(in);
        }

        @Override
        public ParkingLocation[] newArray(int size) {
            return new ParkingLocation[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(totalSpaces);
        dest.writeInt(numberOfAvailableSpaces);
        dest.writeString(mapImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
