package com.chiragaggarwal.bolt.common;

import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocations;

public class PolylineTransformer {
    public String encode(final UserLocations userLocations) {
        long lastLatitude = 0;
        long lastLongitude = 0;

        final StringBuffer result = new StringBuffer();

        for (final UserLocation userLocation : userLocations) {
            long latitude = Math.round(userLocation.latitude * 1e5);
            long longitude = Math.round(userLocation.longitude * 1e5);

            long dLatitude = latitude - lastLatitude;
            long dLongitude = longitude - lastLongitude;

            encode(dLatitude, result);
            encode(dLongitude, result);

            lastLatitude = latitude;
            lastLongitude = longitude;
        }
        return result.toString();
    }

    private void encode(long v, StringBuffer result) {
        v = v < 0 ? ~(v << 1) : v << 1;
        while (v >= 0x20) {
            result.append(Character.toChars((int) ((0x20 | (v & 0x1f)) + 63)));
            v >>= 5;
        }
        result.append(Character.toChars((int) (v + 63)));
    }
}
