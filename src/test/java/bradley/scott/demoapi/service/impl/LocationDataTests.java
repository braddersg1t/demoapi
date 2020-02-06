package bradley.scott.demoapi.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bradley.scott.demoapi.dto.User;
import bradley.scott.demoapi.model.LocationEnum;

/**
 * Tests to ensure distance
 * checks and user filtering
 * work OK with some sample data
 * 
 * @author scottbradley
 */
@SpringBootTest
public class LocationDataTests {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private LocationServiceImpl locationService;
	
	List<User> londonUsersList;
	List<User> allUserList;
	
	@BeforeEach
	void setUp() {
		try {
			//data retrieved from live end-point
			londonUsersList = objectMapper.readValue(
					new File("src/test/resources/london-users-test-data.json"), 
					new TypeReference<List<User>>(){});
			
			//data retrieved from live end-point
			allUserList = objectMapper.readValue(
					new File("src/test/resources/all-users-test-data.json"), 
					new TypeReference<List<User>>(){});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Ensure test data loaded ok
	 */
	@Test
	void testLondonUsersDataPresent() {
		assertNotNull(londonUsersList);
		assertTrue(londonUsersList.size() == 6);
	}//testLondonUsersDataPresent
	
	/**
	 * Ensure test data loaded ok
	 */
	@Test
	void testAllUsersDataPresent() {
		assertNotNull(allUserList);
		assertTrue(allUserList.size() == 1000);
	}//testAllUsersDataPresent
	
	/**
	 * Method Tested: filterUsersWithinDistanceOfLocation
	 * I have examined sample data and found the service
	 * to return users by city did return users but none
	 * of the location coordinates were in the UK. So this
	 * test should fail as no users within 50 miles.
	 */
	@Test
	void londonUsersWithin50Miles_expectNoResults() {
		List<User> result = locationService.filterUsersWithinDistanceOfLocation(londonUsersList, 50, LocationEnum.LONDON);
		assertTrue(result.size() == 0);
	}//londonUsersWithin50Miles_expectNoResults
	
	/**
	 * Method Tested: filterUsersWithinDistanceOfLocation
	 * This test should filter 1000 users down to
	 * 3 that are within 50 miles of London
	 */
	@Test
	void allUsersWithin50MilesOfLondon_expectResults() {
		List<User> result = locationService.filterUsersWithinDistanceOfLocation(allUserList, 50, LocationEnum.LONDON);
		assertTrue(result.size() == 3);
	}//allUsersWithin50MilesOfLondon_expectResults
	
}
