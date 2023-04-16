package com.example.demo.Util;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;

public class GoogleMapsUtil {
    public static Integer getDrivingDistance(String address1, String address2) throws ApiException, InterruptedException, IOException {
        // Set up the API context with your Google API key
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBz9FJj_9urRqrsxd7_PUomb9u0WK-itS8")
                .build();

        // Send the distance matrix API request for driving mode
        DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(context)
                .origins(address1)
                .destinations(address2)
                .mode(TravelMode.DRIVING);
        DistanceMatrix response = request.await();

        // Extract the distance in meters and convert to kilometers
        int distanceInMeters = (int) response.rows[0].elements[0].distance.inMeters;
        double distanceInKm = distanceInMeters / 1000.0;

        return (int) distanceInKm;
    }
}
