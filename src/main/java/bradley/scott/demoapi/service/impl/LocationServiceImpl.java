package bradley.scott.demoapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import bradley.scott.demoapi.dto.User;
import bradley.scott.demoapi.model.LatLng;
import bradley.scott.demoapi.model.LocationEnum;
import bradley.scott.demoapi.service.LocationService;

/**
 * Location based service methods
 * 
 * @author scottbradley
 */
@Service
public class LocationServiceImpl implements LocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	@Autowired
    private RestTemplate restTemplate;
	
	@Autowired
	private GpsServiceImpl gpsService;
	
	@Value("${rest.base.url}")
    private String baseUrl;
	
	@Value("${rest.users.endpoint}")
    private String userEndpoint;
	
	@Value("${rest.city.endpoint}")
    private String cityEndpoint;
	
	
	/**
	 * Get all users from using 3rd
	 * party API end-point
	 * 
	 * @return ResponseEntity<List<Object>>
	 */
	public ResponseEntity<List<User>> getAllUsers() {
		
		ResponseEntity<List<User>> response = null;
		
		StringBuilder urlBuilder = new StringBuilder().append(baseUrl)
			.append(userEndpoint);
			
		LOGGER.debug("Attempting to call: {}", urlBuilder);
		response = restTemplate.exchange(urlBuilder.toString(),
						HttpMethod.GET, null,
						new ParameterizedTypeReference<List<User>>() {});
		
		return response;
		
	}//getAllUsers
	
	/**
	 * Get users by city parameter using 3rd party
	 * API end-point.
	 * End-point called does not appear
	 * to take coordinates into consideration.
	 * 
	 * @param city as String
	 * @return ResponseEntity<List<Object>>
	 */
	public ResponseEntity<List<User>> getUsersByCity(String city) {
		
		ResponseEntity<List<User>> response = null;
		
		StringBuilder urlBuilder = new StringBuilder().append(baseUrl)
				.append(cityEndpoint)
				.append("/")
				.append(city)
				.append(userEndpoint);
			
		LOGGER.debug("Attempting to call: {}", urlBuilder);		
		response = restTemplate.exchange(urlBuilder.toString(),
	                    HttpMethod.GET, null,
	                    new ParameterizedTypeReference<List<User>>() {});

		return response;
		
	}//getUsersByCity
	
	/**
	 * Filter a list of users returning ones whos coordinates are
	 * less than less than or equal to distanceInMiles parameter
	 * 
	 * @param users as User
	 * @param distanceInMiles as double
	 * @param location as LocationEnum
	 * @return List<User> 
	 */
	public List<User> filterUsersWithinDistanceOfLocation(List<User> users, double distanceInMiles, LocationEnum location) {
		
		List<User> results = new ArrayList<>();
		
		//users should not be null but decided to check anyway
		if(users != null) {
			for(User user : users) {
				if(userWithinDistanceOfLocation(user, distanceInMiles, location)) {
					results.add(user);
				}
			}
		}
		
		return results;
		
	}//filterUsersWithinDistanceOfLocation
	
	/**
	 * Check to see if user coordinates are less than or equal
	 * to distanceInMiles parameter
	 * 
	 * @param user as User
	 * @param distanceInMiles as double
	 * @param location as LocationEnum
	 * @return boolean
	 */
	public boolean userWithinDistanceOfLocation(User user, double distanceInMiles, LocationEnum location) {
		
		boolean result = false;
		
		try {
			double distance = gpsService.returnDistanceInMiles(
					new LatLng(user.getLatitude(), user.getLongitude()), 
					new LatLng(location.getLatitude(), location.getLongitude()));
			
			result = distance <= distanceInMiles;
		}
		catch(Exception e) {
			/*
			 * log the exception and carry on as this method may be
			 * called as part of a loop
			 */
			LOGGER.error(e.getMessage());
		}
		
		return result;
		
	}//userWithinDistanceOfLocation
	
}
