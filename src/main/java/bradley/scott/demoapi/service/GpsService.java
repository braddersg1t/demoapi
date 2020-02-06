package bradley.scott.demoapi.service;

import bradley.scott.demoapi.model.LatLng;

/**
 * Gps Service Interface
 * 
 * @author scottbradley
 */
public interface GpsService {

	/**
	 * Return the distance in miles between two sets of
	 * location coordinates with a rounded up result.
	 * 
	 * @param srcLocation as LatLng
	 * @param destLocation as LatLng
	 * @return double
	 */
	public double returnDistanceInMilesRoundedUp(LatLng srcLocation, LatLng destLocation);
	
	/**
	 * Return the distance in miles between two sets of
	 * location coordinates.
	 * 
	 * @param srcLocation as LatLng
	 * @param destLocation as LatLng
	 * @return double
	 */
	public double returnDistanceInMiles(LatLng srcLocation, LatLng destLocation);
	
	/**
	 * Return the distance in metres between two sets of
	 * location coordinates.
	 * 
	 * @param srcLocation as LatLng
	 * @param destLocation as LatLng
	 * @return double
	 */
	public double returnDistanceInMetres(LatLng srcLocation, LatLng destLocation);
	
	/**
	 * Convert a degrees value to radians
	 * 
	 * @param deg as double
	 * @return double
	 */
	public double convertDegrees2radians(double deg);
	
}
