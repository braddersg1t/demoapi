package bradley.scott.demoapi.service;

import java.util.List;
import org.springframework.http.ResponseEntity;

import bradley.scott.demoapi.dto.User;
import bradley.scott.demoapi.model.LocationEnum;

/**
 * Location Service Interface
 * 
 * @author scottbradley
 */
public interface LocationService {
	
	/**
	 * Returns ResponseEntity<List<User>> from an
	 * API end-point
	 * 
	 * @return ResponseEntity<List<Object>>
	 */
	public ResponseEntity<List<User>> getAllUsers();
	
	/**
	 * Returns ResponseEntity<List<User>> from an
	 * API end-point filtered by city parameter
	 * 
	 * @param city
	 * @return ResponseEntity<List<Object>>
	 */
	public ResponseEntity<List<User>> getUsersByCity(String city);
	
	/**
	 * Returns a filtered list of User objects. User coordinates must
	 * be <= distanceInMiles from location coordinates
	 * 
	 * @param users as List<User>
	 * @param distanceInMiles as double
	 * @param location as LocationEnum
	 * @return List<User>
	 */
	public List<User> filterUsersWithinDistanceOfLocation(List<User> users, double distanceInMiles, LocationEnum location);
	
	/**
	 * Determine if user coordinates are <= distanceInMiles from location
	 * coordinates
	 * 
	 * @param user as User
	 * @param distanceInMiles as distanceInMiles
	 * @param location as LocationEnum
	 * @return boolean
	 */
	public boolean userWithinDistanceOfLocation(User user, double distanceInMiles, LocationEnum location);
	
}
