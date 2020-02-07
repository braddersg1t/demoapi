package bradley.scott.demoapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import bradley.scott.demoapi.dto.User;
import bradley.scott.demoapi.model.LocationEnum;
import bradley.scott.demoapi.service.impl.LocationServiceImpl;

/**
 * Location Controller Tests
 * 
 * @author scottbradley
 */
@SpringBootTest
public class LocationControllerTests {
	
	private static final String HTTP_CLIENT_ERROR_MESSAGE = "There was an error when trying to call 3rd party API";
	private static final String RUNTIME_ERROR_MESSAGE = "It was not possible to complete the request due to an error occurring.";

	@Mock
	private LocationServiceImpl mockLocationService;
	
	@InjectMocks
	@Autowired
	private LocationController locationController;
	
	private List<User> testLondonUsers;
	private List<User> testUsersWithin50;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		
		//add 3 test users to a list
		testLondonUsers = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			testLondonUsers.add(new User());
		}
		
		//add 5 test users to a list
		testUsersWithin50 = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			testUsersWithin50.add(new User());
		}
	}
	
	/**
	 * Method Tested: getListedUsersForCity
	 * Test to return users listed by city
	 */
	@Test
	void givenCityValid_returnLondonUsers() {
		ResponseEntity<List<User>> userResponse = new ResponseEntity<List<User>>(testLondonUsers, HttpStatus.OK);
		
		Mockito.when(mockLocationService.getUsersByCity(
				Mockito.anyString())
	        ).thenReturn(userResponse);
		
		List<User> result = locationController.getListedUsersForCity(LocationEnum.LONDON.getName());
		assertNotNull(result);
		assertTrue(result.size() == testLondonUsers.size(), "Result list size does not match expected list size");
		
	}//givenCityValid_returnLondonUsers
	
	/**
	 * Method Tested: getListedUsersForCity
	 * Test to exercise the HttpClientErrorException handling in the
	 * getListedUsersForCity method
	 */
	@Test
	void givenGetListedUsersForCityCalled_throwHttpClientErrorException_andReturnResponseStatusException() {
		
		try {
			Mockito.when(mockLocationService.getUsersByCity(Mockito.anyString())).thenThrow(HttpClientErrorException.class);
			locationController.getListedUsersForCity(LocationEnum.LONDON.getName());
		}
		catch(Exception e) {
			assertTrue(e instanceof ResponseStatusException);
			assertTrue(e.getMessage().contains(HTTP_CLIENT_ERROR_MESSAGE));
		}
	}//givenGetListedUsersForCityCalled_throwHttpClientErrorException_andReturnResponseStatusException
	
	/**
	 * Method Tested: getListedUsersForCity
	 * Test to exercise the exception handling in the
	 * getListedUsersForCity method
	 */
	@Test
	void givenGetListedUsersForCityCalled_throwRuntimeException_andReturnResponseStatusException() {
		
		try {
			Mockito.when(mockLocationService.getUsersByCity(Mockito.anyString())).thenThrow(RuntimeException.class);
			locationController.getListedUsersForCity(LocationEnum.LONDON.getName());
		}
		catch(Exception e) {
			assertTrue(e instanceof ResponseStatusException);
			assertTrue(e.getMessage().contains(RUNTIME_ERROR_MESSAGE));
		}
	}//givenGetListedUsersForCityCalled_throwRuntimeException_andReturnResponseStatusException
	
	/**
	 * Method Tested: getCityUsersByCoordinatesWithinLimit
	 * Test to return users within specified miles of location
	 */
	@Test
	void givenCityAndMilesValid_returnUsersWithin50Miles() {
		ResponseEntity<List<User>> userResponse = new ResponseEntity<List<User>>(testUsersWithin50, HttpStatus.OK);
		
		Mockito.when(mockLocationService.getAllUsers()).thenReturn(userResponse);
		Mockito.when(mockLocationService.filterUsersWithinDistanceOfLocation(
				Mockito.any(),
				Mockito.anyDouble(),
				Mockito.any())
			).thenReturn(testUsersWithin50);
		
		List<User> result = locationController.getCityUsersByCoordinatesWithinLimit(LocationEnum.LONDON.getName(), 50.0);
		assertNotNull(result);
		assertTrue(result.size() == testUsersWithin50.size(), "Result list size does not match expected list size");
		
	}//givenCityAndMilesValid_returnAllUsers
	
	/**
	 * Method Tested: getCityUsersByCoordinatesWithinLimit
	 * Test to exercise the HttpClientErrorException handling in the
	 * getCityUsersByCoordinatesWithinLimit method
	 */
	@Test
	void givenGetCityUsersByCoordinatesWithinLimitCalled_throwHttpClientErrorException_andReturnResponseStatusException() {
		
		try {
			Mockito.when(mockLocationService.getAllUsers()).thenThrow(HttpClientErrorException.class);
			locationController.getCityUsersByCoordinatesWithinLimit(LocationEnum.LONDON.getName(), 50.0);
		}
		catch(Exception e) {
			assertTrue(e instanceof ResponseStatusException);
			assertTrue(e.getMessage().contains(HTTP_CLIENT_ERROR_MESSAGE), e.getMessage());
		}
	}//givenGetCityUsersByCoordinatesWithinLimitCalled_throwHttpClientErrorException_andReturnResponseStatusException
	
	/**
	 * Method Tested: getCityUsersByCoordinatesWithinLimit
	 * Test to exercise the exception handling in the
	 * getCityUsersByCoordinatesWithinLimit method
	 */
	@Test
	void givenGetCityUsersByCoordinatesWithinLimitCalled_throwRuntimeException_andReturnResponseStatusException() {
		
		try {
			Mockito.when(mockLocationService.getAllUsers()).thenThrow(RuntimeException.class);
			locationController.getCityUsersByCoordinatesWithinLimit(LocationEnum.LONDON.getName(), 50.0);
		}
		catch(Exception e) {
			assertTrue(e instanceof ResponseStatusException);
			assertTrue(e.getMessage().contains(RUNTIME_ERROR_MESSAGE));
		}
	}//givenGetCityUsersByCoordinatesWithinLimitCalled_throwRuntimeException_andReturnResponseStatusException
	
}
