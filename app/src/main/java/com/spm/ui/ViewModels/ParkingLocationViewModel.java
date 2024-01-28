package com.spm.ui.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spm.ui.Helpers.Constants;
import com.spm.ui.Helpers.SortOption;
import com.spm.ui.Interfaces.ParkingApi;
import com.spm.ui.Models.ParkingLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParkingLocationViewModel extends ViewModel {
    private MutableLiveData<List<ParkingLocation>> parkingLocations;
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private SortOption sortOption = SortOption.NAME;


    public LiveData<List<ParkingLocation>> getParkingLocations() {
        if (parkingLocations == null) {
            parkingLocations = new MutableLiveData<>();
            loadParkingLocations();
        }
        return parkingLocations;
    }

    public LiveData<String> getError() {
        return error;
    }

    private void loadParkingLocations() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ParkingApi parkingApi = retrofit.create(ParkingApi.class);

        Call<List<ParkingLocation>> call = parkingApi.getParkingLocations();
        call.enqueue(new Callback<List<ParkingLocation>>() {
            @Override
            public void onResponse(@NonNull Call<List<ParkingLocation>> call, @NonNull Response<List<ParkingLocation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ParkingLocation> parkingLocation = new ArrayList<>(response.body());
                    switch (sortOption) {
                        case NAME:
                            parkingLocation.sort(Comparator.comparing(ParkingLocation::name));
                            break;
                        case AVAILABLE:
                            parkingLocation.sort(Comparator.comparingInt(ParkingLocation::numberOfAvailableSpaces).reversed());
                            break;
                    }
                    parkingLocations.setValue(parkingLocation);
                } else {
                    error.setValue("Received empty response from server");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ParkingLocation>> call, @NonNull Throwable t) {
                error.setValue("Failed to load parking locations: " + t.getMessage());
            }
        });
    }

    public void setSortOption(SortOption sortOption) {
        this.sortOption = sortOption;
        refreshParkingLocations();
    }

    public void refreshParkingLocations() {
        loadParkingLocations();
    }
}

