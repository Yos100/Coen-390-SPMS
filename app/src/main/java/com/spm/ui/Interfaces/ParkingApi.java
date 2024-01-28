package com.spm.ui.Interfaces;

import com.spm.ui.Models.ParkingLocation;
import com.spm.ui.Models.ParkingSpace;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ParkingApi {
    @GET("/api/parkingLocations")
    Call<List<ParkingLocation>> getParkingLocations();

    //get Parking Spaces by location ID
    @GET("/api/parkingSpaces/location/{locationId}")
    Call<List<ParkingSpace>> getParkingSpacesByLocationId(@Path("locationId") Long locationId);
}

