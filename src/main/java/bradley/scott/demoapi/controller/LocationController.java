package bradley.scott.demoapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import bradley.scott.demoapi.dto.User;
import bradley.scott.demoapi.model.LocationEnum;
import bradley.scott.demoapi.service.impl.LocationServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller containing location based endpoints
 * 
 * @author scottbradley
 */
@RestController
@Api(value="Location Data Management")
public class LocationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private LocationServiceImpl locationService;
	
	/**
	 * Get listed users by city.
	 * 
	 * User coordinates will not be
	 * taken into account. Test data using
	 * London as the city has shown no user
	 * coordinates from this service are 
	 * actually near London.
	 * 
	 * NOTE: Exception handling process is debatable
	 * as SonarQube labels logging and re-throwing as
	 * a code smell. My intention was to get the actual
	 * exception in the log and return a nicer message
	 * with a relevant status code to the user.
	 * 
	 * @param city as String (City is case sensitive)
	 * @return List<User>
	 */
	@ApiOperation(value = "View a list of users by city")
	@GetMapping(value = "/city/{city}/users/listed", produces = "application/json")
	public List<User> getListedUsersForCity(@PathVariable String city) {
		
		List<User> result;
		
		try {
			ResponseEntity<List<User>> serviceResponse = locationService.getUsersByCity(city);
			result = serviceResponse.getBody();
		}
		catch(HttpClientErrorException e) {
			LOGGER.error(e.getMessage());
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND, "There was an error when trying to call 3rd party API", e);
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "It was not possible to complete the request due to an error occurring.", e);
		}
		
		return result;
			
	}//getListedUsersForCity
	
	/**
	 * Get users by city whose coordinates are
	 * within n miles of city coordinates.
	 * 
	 * NOTE: Exception handling process is debatable
	 * as SonarQube labels logging and re-throwing as
	 * a code smell. My intention was to get the actual
	 * exception in the log and return a nicer message
	 * with a relevant status code to the user.
	 * 
	 * @param city as String (City is case sensitive)
	 * @param miles as Double
	 * @return List<User>
	 */
	@ApiOperation(value = "View a list of users within specified distance (miles) from a city")
	@GetMapping(value = "/city/{city}/users/distance/{miles}", produces = "application/json")
	public List<User> getCityUsersByCoordinatesWithinLimit(@PathVariable String city, @PathVariable Double miles) {
		
		List<User> result;
		
		try {
			ResponseEntity<List<User>> response = locationService.getAllUsers();
			result = locationService.filterUsersWithinDistanceOfLocation(
						response.getBody(), 
						miles, 
						LocationEnum.fromLocationName(city)
					);
		}
		catch(HttpClientErrorException e) {
			LOGGER.error(e.getMessage());
			throw new ResponseStatusException(
			           HttpStatus.NOT_FOUND, "There was an error when trying to call 3rd party API", e);
		}
		catch(Exception e) {
			LOGGER.error(e.getMessage());
			throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "It was not possible to complete the request due to an error occurring.", e);
		}
		
		return result;
		
	}//getCityUsersByCoordinatesWithinLimit
	
}
