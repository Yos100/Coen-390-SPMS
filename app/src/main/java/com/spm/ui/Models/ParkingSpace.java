package com.spm.ui.Models;

public final class ParkingSpace {
    private final boolean status;
    private final String name;
    private final Long locationId;
    private final String sensorId;

    ParkingSpace(boolean status, String name, Long locationId, String sensorId) {
        this.status = status;
        this.name = name;
        this.locationId = locationId;
        this.sensorId = sensorId;
    }

    public boolean status() {
        return status;
    }

    public String name() {
        return name;
    }

    public Long locationId() {
        return locationId;
    }

    public String sensorId() {
        return sensorId;
    }

    public int getIdFromName() {
        if (name != null && name.length() > 1) {
            String idStr = name.substring(1); // Extract the numerical part after the first character
            try {
                return Integer.parseInt(idStr); // Convert to integer
            } catch (NumberFormatException e) {
                // Handle the exception if the substring is not a valid integer
                e.printStackTrace();
            }
        }
        return -1; // Return -1 or other appropriate value if the extraction fails
    }
}
