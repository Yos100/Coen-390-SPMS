package com.spm.ui.Adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.spm.ui.Models.ParkingLocation;
import com.spm.ui.R;

import java.util.List;
import java.util.Objects;

public class ParkingLocationAdapter extends RecyclerView.Adapter<ParkingLocationAdapter.ViewHolder> {
    private final AsyncListDiffer<ParkingLocation> parkingLocations;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ParkingLocation item);
    }

    public ParkingLocationAdapter(OnItemClickListener listener) {
        this.listener = listener;
        parkingLocations = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<ParkingLocation>() {
            @Override
            public boolean areItemsTheSame(@NonNull ParkingLocation oldItem, @NonNull ParkingLocation newItem) {
                return oldItem.id().equals(newItem.id());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ParkingLocation oldItem, @NonNull ParkingLocation newItem) {
                return Objects.equals(oldItem, newItem);
            }
        });
    }

    public void submitList(List<ParkingLocation> locations) {
        parkingLocations.submitList(locations);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationName, locationAddress, availableSpaces;
        MaterialCardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.locationName);
            locationAddress = itemView.findViewById(R.id.locationAddress);
            availableSpaces = itemView.findViewById(R.id.availableSpaces);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    ParkingLocation item = parkingLocations.getCurrentList().get(position);
                    listener.onItemClick(item);
                }
            });
        }

        public void changeColor(float availableSpacePercentage) {
            int greenColor = Color.parseColor("#90EE90");  // Light Green
            int yellowColor = Color.parseColor("#FFFF00");   // Yellow
            int redColor = Color.parseColor("#FF0000"); // Red
            int color;
            if (availableSpacePercentage > 0.50f) {
                color = greenColor; // if available space is greater than 50%, make it green
            } else if (availableSpacePercentage <= 0.10f) {
                color = redColor; // if available space is less than 10%, make it red
            } else {
                // if available space is between 10% and 50%, make it yellow
                color = yellowColor;
            }
            cardView.setCardBackgroundColor(color);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thisItemsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_location_item, parent, false);
        return new ViewHolder(thisItemsView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParkingLocation parkingLocation = parkingLocations.getCurrentList().get(position);

        holder.locationName.setText(parkingLocation.name());
        holder.locationAddress.setText(parkingLocation.address());
        holder.availableSpaces.setText(String.format("%d / %d", parkingLocation.numberOfAvailableSpaces(), parkingLocation.totalSpaces()));

        float availableSpacePercentage = (float) parkingLocation.numberOfAvailableSpaces() / parkingLocation.totalSpaces();
        holder.changeColor(availableSpacePercentage);
    }

    @Override
    public int getItemCount() {
        return parkingLocations.getCurrentList().size();
    }
}


