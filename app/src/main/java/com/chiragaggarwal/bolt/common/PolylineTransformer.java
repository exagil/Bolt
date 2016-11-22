package com.chiragaggarwal.bolt.common;

import com.chiragaggarwal.bolt.location.UserLocation;
import com.chiragaggarwal.bolt.location.UserLocations;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

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

    public List<LatLng> decode(final String encodedPath) {
        int len = encodedPath.length();
        final List<LatLng> path = new ArrayList<LatLng>();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new LatLng(lat * 1e-5, lng * 1e-5));
        }

        return path;
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
