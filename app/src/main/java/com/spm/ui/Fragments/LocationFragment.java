package com.spm.ui.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spm.ui.Adapters.ParkingLocationAdapter;
import com.spm.ui.Helpers.SortOption;
import com.spm.ui.Interfaces.SortableFragment;
import com.spm.ui.MainActivity;
import com.spm.ui.R;
import com.spm.ui.ViewModels.ParkingLocationViewModel;

public class LocationFragment extends Fragment implements SortableFragment {

    private ParkingLocationViewModel parkingLocationViewModel;
    private RecyclerView locationRecyclerView;
    private ParkingLocationAdapter parkingLocationAdapter;
    private Handler locationHandler;
    private HandlerThread locationHandlerThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        locationRecyclerView = view.findViewById(R.id.locationRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initRecyclerViewAnimation(locationRecyclerView);

        initAdaptors();
        initViewModels();
        initLocationHandler();

        return view;
    }

    private void initRecyclerViewAnimation(RecyclerView recyclerView) {
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(500); // duration of the animation when items are added
        animator.setRemoveDuration(500); // duration of the animation when items are removed
        recyclerView.setItemAnimator(animator);
    }

    private void initLocationHandler() {
        // Create the HandlerThread and Handler
        locationHandlerThread = new HandlerThread("backgroundLocationThread");
        locationHandlerThread.start();
        locationHandler = new Handler(locationHandlerThread.getLooper());

        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                parkingLocationViewModel.refreshParkingLocations();
                locationHandler.postDelayed(this, 10000);
            }
        };
        locationHandler.post(runnableCode);
    }

    private void initViewModels() {
        parkingLocationViewModel = new ViewModelProvider(this).get(ParkingLocationViewModel.class);

        parkingLocationViewModel.getParkingLocations().observe(getViewLifecycleOwner(), parkingLocations ->
                parkingLocationAdapter.submitList(parkingLocations));

        // handle error messages
        parkingLocationViewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorMessage);
    }

    private void initAdaptors() {
        parkingLocationAdapter = new ParkingLocationAdapter(item -> {
            if(getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToSpaceFragment(item);
            }
        });
        locationRecyclerView.setAdapter(parkingLocationAdapter);
    }

    @Override
    public void changeSortOption(SortOption sortOption) {
        parkingLocationViewModel.setSortOption(sortOption);
    }

    private void displayErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationHandler.removeCallbacksAndMessages(null); // remove callbacks and messages
        locationHandlerThread.quitSafely(); // quit the handlerThread safely
    }
}
