package com.spm.ui.Adapters;

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
import com.spm.ui.Models.ParkingSpace;
import com.spm.ui.R;

import java.util.List;
import java.util.Objects;

public class ParkingSpaceAdapter extends RecyclerView.Adapter<ParkingSpaceAdapter.ViewHolder> {
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ParkingSpace item);
    }

    public ParkingSpaceAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView parkingSpaceName;
        MaterialCardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            parkingSpaceName = itemView.findViewById(R.id.parkingSpaceName);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    ParkingSpace item = parkingSpaces.getCurrentList().get(position);
                    listener.onItemClick(item);
                }
            });
        }

        public void changeColor(boolean isAvailable) {
            int color = isAvailable ? Color.parseColor("#90EE90") : Color.parseColor("#FF0000");
            cardView.setCardBackgroundColor(color);
        }
    }

    private final AsyncListDiffer<ParkingSpace> parkingSpaces = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<ParkingSpace>() {
        @Override
        public boolean areItemsTheSame(@NonNull ParkingSpace oldItem, @NonNull ParkingSpace newItem) {
            return oldItem.sensorId().equals(newItem.sensorId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ParkingSpace oldItem, @NonNull ParkingSpace newItem) {
            return Objects.equals(oldItem, newItem);
        }
    });

    public void submitList(List<ParkingSpace> parkingSpaces) {
        this.parkingSpaces.submitList(parkingSpaces);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thisItemsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_space_item, parent, false);
        return new ViewHolder(thisItemsView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParkingSpace parkingSpace = parkingSpaces.getCurrentList().get(position);

        holder.parkingSpaceName.setText(parkingSpace.name());
        holder.changeColor(parkingSpace.status());
    }

    @Override
    public int getItemCount() {
        return parkingSpaces.getCurrentList().size();
    }
}