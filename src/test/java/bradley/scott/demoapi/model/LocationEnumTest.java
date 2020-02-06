package bradley.scott.demoapi.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocationEnumTest {

	private static final String LONDON = "London";
	private static final String UNKNOWN_CITY = "Wrongsville";
	
	@Test
	void givenCityValid_locationEnumIsReturned() {
		LocationEnum result = LocationEnum.fromLocationName(LONDON);
		assertTrue(result.getName().equalsIgnoreCase(LONDON));
	}//givenCityValid_locationEnumIsReturned
	
	@Test
	void givenCityInvalid_illegalArguementExceptionThrown() {
		Exception exception  = assertThrows(
				IllegalArgumentException.class,
				() -> LocationEnum.fromLocationName(UNKNOWN_CITY));
		
		assertTrue(exception.getMessage().equals("No location data found for location name: " + UNKNOWN_CITY));
		
	}//givenCityInvalid_illegalArguementExceptionThrown
	
}
