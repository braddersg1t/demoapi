package bradley.scott.demoapi.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import bradley.scott.demoapi.dto.User;
import bradley.scott.demoapi.model.LocationEnum;

@SpringBootTest
public class LocationServiceTests {
	
	@Mock
	private RestTemplate restTemplateMock;
	
	@Mock
	private GpsServiceImpl gpsServiceImplMock;
	
	@InjectMocks
	@Autowired
	private LocationServiceImpl locationServiceImpl;
	
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
	 *  Method Tested: getAllUsers
	 *  Test to return users when target end-point
	 *  is assumed to be ok. Just rodding the code
	 *  through really!
	 */
	@SuppressWarnings("unchecked")
	@Test
	void givenEndpointIsValid_returnAllTestUsers() {
		ResponseEntity<List<User>> userResponse = new ResponseEntity<List<User>>(testUsersWithin50, HttpStatus.OK);
		Mockito.when(restTemplateMock.exchange(
				Mockito.anyString(),
				Mockito.any(HttpMethod.class),
				Mockito.<HttpEntity<List<User>>>any(),
	            Mockito.any(ParameterizedTypeReference.class))
	        ).thenReturn(userResponse);
		
		ResponseEntity<List<User>> response = locationServiceImpl.getAllUsers();
		assertNotNull(response.getBody());
		
		assertTrue(response.getBody().size() == 5, "Result is not 5");
	}//givenEndpointIsValid_returnAllTestUsers
	
	/**
	 *  Method Tested: getUsersByCity
	 *  Test to return expected users when all
	 *  data is correct. Just rodding the code
	 *  through really!
	 */
	@SuppressWarnings("unchecked")
	@Test
	void givenCityIsValidAndEndpointIsValid_returnAllTestUsers() {
		ResponseEntity<List<User>> userResponse = new ResponseEntity<List<User>>(testLondonUsers, HttpStatus.OK);
		Mockito.when(restTemplateMock.exchange(
				Mockito.anyString(),
				Mockito.any(HttpMethod.class),
				Mockito.<HttpEntity<List<User>>>any(),
	            Mockito.any(ParameterizedTypeReference.class))
	        ).thenReturn(userResponse);
				
		ResponseEntity<List<User>> response = locationServiceImpl.getUsersByCity(LocationEnum.LONDON.getName());
		assertNotNull(response.getBody());
		
		assertTrue(response.getBody().size() == 3, "Result is not 3");
	}//givenCityIsValidAndEndpointIsValid_returnAllTestUsers
	
	/**
	 *  Method Tested: filterUsersWithinDistanceOfLocation
	 *  Test tto return all users that are within 50
	 *  miles of location
	 */
	@Test
	void givenUsersWithin50MilesOfLocation_andLimitIs50_returnAllTestUsers() {
		Mockito.doReturn(30.0)
		.when(gpsServiceImplMock)
		.returnDistanceInMiles(
				Mockito.any(), 
				Mockito.any());

		List<User> result = locationServiceImpl.filterUsersWithinDistanceOfLocation(testUsersWithin50, 50, LocationEnum.LONDON);
		assertTrue(result.size() == 5, "Result is not 5");
	}//givenUsersWithin50MilesOfLocation_andLimitIs50_returnAllTestUsers
	
	/**
	 *  Method Tested: filterUsersWithinDistanceOfLocation
	 *  Test to return an empty list when all users
	 *  supplied are outside of 50 miles from a
	 *  location
	 */
	@Test
	void givenUsersOutside50MilesOfLocation_andLimitIs50_returnEmptyList() {
		Mockito.doReturn(70.0)
		.when(gpsServiceImplMock)
		.returnDistanceInMiles(
				Mockito.any(), 
				Mockito.any());

		List<User> result = locationServiceImpl.filterUsersWithinDistanceOfLocation(testUsersWithin50, 50, LocationEnum.LONDON);
		assertTrue(result.size() == 0, "Result is not 0");
	}//givenUsersOutside50MilesOfLocation_andLimitIs50_returnEmptyList
	
	/**
	 *  Method Tested: filterUsersWithinDistanceOfLocation
	 *  Test an empty list is returned when users
	 *  parameter is null
	 */
	@Test
	void givenUsersNull_returnEmptyList() {
		Mockito.doReturn(70.0)
		.when(gpsServiceImplMock)
		.returnDistanceInMiles(
				Mockito.any(), 
				Mockito.any());

		List<User> result = locationServiceImpl.filterUsersWithinDistanceOfLocation(null, 50, LocationEnum.LONDON);
		assertTrue(result.size() == 0, "Result is not 0");
	}//givenUsersNull_returnEmptyList
	
	/**
	 *  Method Tested: userWithinDistanceOfLocation
	 *  Test to return true when is user is up
	 *  to 50 miles from a location
	 */
	@Test
	void givenUserWithin50MilesOfLocation_andLimitIs50_returnTrue() {
		Mockito.doReturn(30.0)
		.when(gpsServiceImplMock)
		.returnDistanceInMiles(
				Mockito.any(), 
				Mockito.any());
		
		boolean result = locationServiceImpl.userWithinDistanceOfLocation(new User(), 50, LocationEnum.LONDON);
		assertTrue(result, "Result is false and should be true");
	}//givenUserWithin50MilesOfLocation_andLimitIs50_returnTrue
	
	/**
	 *  Method Tested: userWithinDistanceOfLocation
	 *  Test to return false when a user is
	 *  more than 50 miles from a location
	 */
	@Test
	void givenUserOutside50MilesOfLocation_andLimitIs50_returnFalse() {
		Mockito.doReturn(70.0)
		.when(gpsServiceImplMock)
		.returnDistanceInMiles(
				Mockito.any(), 
				Mockito.any());
		
		boolean result = locationServiceImpl.userWithinDistanceOfLocation(new User(), 50, LocationEnum.LONDON);
		assertFalse(result, "Result is true and should be false");
	}//givenUserOutside50MilesOfLocation_andLimitIs50_returnFalse
	
	/**
	 *  Method Tested: userWithinDistanceOfLocation
	 *  Test to return true when a user is exactly
	 *  50 miles from a location
	 */
	@Test
	void givenUser50MilesOfLocation_andLimitIs50_returnTrue() {
		Mockito.doReturn(50.0)
		.when(gpsServiceImplMock)
		.returnDistanceInMiles(
				Mockito.any(), 
				Mockito.any());
		
		boolean result = locationServiceImpl.userWithinDistanceOfLocation(new User(), 50, LocationEnum.LONDON);
		assertTrue(result, "Result is false and should be true");
	}//givenUser50MilesOfLocation_andLimitIs50_returnTrue
	
	/**
	 * Method Tested: userWithinDistanceOfLocation
	 * Test to exercise the exception handling in the
	 * userWithinDistanceOfLocation method
	 */
	@Test
	void givenUserWithinDistanceOfLocationCalled_throwRuntimeException() {
		Mockito.when(gpsServiceImplMock.returnDistanceInMiles(Mockito.any(), Mockito.any())).thenThrow(RuntimeException.class);
		boolean result = locationServiceImpl.userWithinDistanceOfLocation(new User(), 50, LocationEnum.LONDON);
		assertFalse(result);
	}//givenUserWithinDistanceOfLocationCalled_throwRuntimeException
	
}
