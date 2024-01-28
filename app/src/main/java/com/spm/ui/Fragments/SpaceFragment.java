package com.spm.ui.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spm.ui.Adapters.ParkingSpaceAdapter;
import com.spm.ui.Helpers.SortOption;
import com.spm.ui.Interfaces.SortableFragment;
import com.spm.ui.Models.ParkingLocation;
import com.spm.ui.R;
import com.spm.ui.ViewModels.ParkingSpaceViewModel;

public class SpaceFragment extends Fragment  implements SortableFragment {

    private ParkingSpaceViewModel parkingSpaceViewModel;
    private RecyclerView spaceRecyclerView;
    private ParkingSpaceAdapter parkingSpaceAdapter;
    private Handler spaceHandler;
    private HandlerThread spaceHandlerThread;
    private long locationId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_space, container, false);

        if (getArguments() != null) {
            ParkingLocation locationItem = getArguments().getParcelable("location");
            this.locationId = locationItem.id();
            TextView name = view.findViewById(R.id.textViewName);
            name.setText(locationItem.name());
            TextView address = view.findViewById(R.id.textViewAddress);
            address.setText(locationItem.address());

            ImageView imageViewThumbnail = view.findViewById(R.id.imageViewThumbnail);

            byte[] decodedString = Base64.decode(locationItem.mapImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageViewThumbnail.setImageBitmap(decodedByte);

            imageViewThumbnail.setOnClickListener(view1 -> {
                FullScreenImageDialogFragment dialogFragment = new FullScreenImageDialogFragment(locationItem.mapImage());
                dialogFragment.show(getParentFragmentManager(), "imageDialog");
            });

        }

        spaceRecyclerView = view.findViewById(R.id.spaceRecyclerView);
        spaceRecyclerView.setHasFixedSize(true);
        spaceRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        initRecyclerViewAnimation(spaceRecyclerView);

        initAdaptors();
        initViewModels();
        initSpaceHandler();

        return view;
    }

    private void initRecyclerViewAnimation(RecyclerView recyclerView) {
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(100); // duration of the animation when items are added
        animator.setRemoveDuration(100); // duration of the animation when items are removed
        recyclerView.setItemAnimator(animator);
    }

    private void initSpaceHandler() {
        // Create the HandlerThread and Handler
        spaceHandlerThread = new HandlerThread("backgroundSpaceThread");
        spaceHandlerThread.start();
        spaceHandler = new Handler(spaceHandlerThread.getLooper());

        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                if (getArguments() != null) {
                    ParkingLocation locationItem = getArguments().getParcelable("location");
                    parkingSpaceViewModel.refreshParkingSpaces(locationItem.id());
                }
                spaceHandler.postDelayed(this, 5000);
            }
        };
        spaceHandler.post(runnableCode);
    }

    private void initViewModels() {
        parkingSpaceViewModel = new ViewModelProvider(this).get(ParkingSpaceViewModel.class);

        parkingSpaceViewModel.getParkingSpaces().observe(getViewLifecycleOwner(), parkingSpaces ->
                parkingSpaceAdapter.submitList(parkingSpaces));

        // handle error messages
        parkingSpaceViewModel.getError().observe(getViewLifecycleOwner(), this::displayErrorMessage);
    }

    private void initAdaptors() {
        parkingSpaceAdapter = new ParkingSpaceAdapter(item -> {
            // TODO: handle click event on parking space
        });
        spaceRecyclerView.setAdapter(parkingSpaceAdapter);
    }

    private void displayErrorMessage(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeSortOption(SortOption sortOption) {
        parkingSpaceViewModel.setSortOption(sortOption, locationId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        spaceHandler.removeCallbacksAndMessages(null); // remove callbacks and messages
        spaceHandlerThread.quitSafely(); // quit the handlerThread safely
    }
}
