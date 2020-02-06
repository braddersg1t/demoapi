package bradley.scott.demoapi.service.impl;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

import bradley.scott.demoapi.model.LatLng;
import bradley.scott.demoapi.service.GpsService;

/**
 * Coordinate (Latitude, Longitude) functions
 * 
 * @author scottbradley
 */
@Service
public class GpsServiceImpl implements GpsService {
	
	private static final double METRES_TO_MILES_MULTIPLIER = 0.000621371192;
	private static final DecimalFormat DF2 = new DecimalFormat("#.##");
	
	/**
	 * Return the distance in miles between source and destination coordinates
	 * rounded up to two decimal places
	 * 
	 * @param srcLocation as LatLng
	 * @param destLocation as LatLng
	 * 
	 * @return double
	 */
	public double returnDistanceInMilesRoundedUp(LatLng srcLocation, LatLng destLocation) {
		double distance = returnDistanceInMiles(srcLocation, destLocation);
		DF2.setRoundingMode(RoundingMode.UP);
		distance = Double.parseDouble(DF2.format(distance));
		return distance;
	}//returnDistanceInMilesRoundedUp
	
	/**
	 * Return the distance in miles between source and destination coordinates
	 * 
	 * @param srcLocation as LatLng
	 * @param destLocation as LatLng
	 * 
	 * @return double
	 */
	public double returnDistanceInMiles(LatLng srcLocation, LatLng destLocation) {
		
		double distance = returnDistanceInMetres(srcLocation, destLocation);
		distance = distance * METRES_TO_MILES_MULTIPLIER;
	    return distance;
	}
	
	/**
	 * Return the distance in metres between source and destination coordinates
	 * 
	 * @param srcLocation as LatLng
	 * @param destLocation as LatLng
	 * 
	 * @return double
	 */
	public double returnDistanceInMetres(LatLng srcLocation, LatLng destLocation) {

	    final int R = 6371; // Radius of the earth

	    Double latDistance = convertDegrees2radians(destLocation.getLatitude() - srcLocation.getLatitude());
	    Double lonDistance = convertDegrees2radians(destLocation.getLongitude() - srcLocation.getLongitude());
	    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(convertDegrees2radians(srcLocation.getLatitude())) * Math.cos(convertDegrees2radians(destLocation.getLatitude()))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    double height = 0; //Removed height as a parameter and set to 0 as it is not available from dataset
	    distance = Math.pow(distance, 2) + Math.pow(height, 2);
	    distance = Math.sqrt(distance);
	    return distance;
	}

	/**
	 * Convert degrees to radians
	 * 
	 * @param deg as double
	 * @return double
	 */
	public double convertDegrees2radians(double deg) {
	    return (deg * Math.PI / 180.0);
	}
	
}
