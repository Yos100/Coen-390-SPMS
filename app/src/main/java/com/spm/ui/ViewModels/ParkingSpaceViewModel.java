package com.spm.ui.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.spm.ui.Helpers.Constants;
import com.spm.ui.Helpers.SortOption;
import com.spm.ui.Interfaces.ParkingApi;
import com.spm.ui.Models.ParkingSpace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParkingSpaceViewModel extends ViewModel {
    private MutableLiveData<List<ParkingSpace>> parkingSpaces;
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private SortOption sortOption = SortOption.NAME;


    public LiveData<List<ParkingSpace>> getParkingSpaces() {
        if (parkingSpaces == null) {
            parkingSpaces = new MutableLiveData<>();
        }
        return parkingSpaces;
    }

    public LiveData<List<ParkingSpace>> getParkingSpaces(Long locationId) {
        if (parkingSpaces == null) {
            parkingSpaces = new MutableLiveData<>();
            loadParkingSpaces(locationId);
        }
        return parkingSpaces;
    }

    public LiveData<String> getError() {
        return error;
    }

    private void loadParkingSpaces(Long locationId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ParkingApi parkingApi = retrofit.create(ParkingApi.class);

        Call<List<ParkingSpace>> call = parkingApi.getParkingSpacesByLocationId(locationId);
        call.enqueue(new Callback<List<ParkingSpace>>() {
            @Override
            public void onResponse(@NonNull Call<List<ParkingSpace>> call, @NonNull Response<List<ParkingSpace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ParkingSpace> parkingSpaceList = new ArrayList<>(response.body());
                    parkingSpaces.setValue(response.body());
                    switch (sortOption) {
                        case NAME:
                            parkingSpaceList.sort(Comparator.comparingInt(ParkingSpace::getIdFromName));
                            break;
                        case AVAILABLE:
                            parkingSpaceList.sort(Comparator.comparing(ParkingSpace::status).reversed());
                            break;
                    }
                    parkingSpaces.setValue(parkingSpaceList);
                } else {
                    error.setValue("Received empty response from server");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ParkingSpace>> call, @NonNull Throwable t) {
                error.setValue("Failed to load parking spaces: " + t.getMessage());
            }
        });
    }

    public void setSortOption(SortOption sortOption, long locationId) {
        this.sortOption = sortOption;
        refreshParkingSpaces(locationId);
    }

    public void refreshParkingSpaces(Long locationId) {
        loadParkingSpaces(locationId);
    }
}

