package bradley.scott.demoapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import bradley.scott.demoapi.model.LatLng;
import bradley.scott.demoapi.model.LocationEnum;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class GpsServiceTests {

	private static final double TEST_DEGREES_VALUE = 10;
	
	private static final LocationEnum LONDON_LOCATION = LocationEnum.LONDON;
	private static final LocationEnum LEEDS_LOCATION = LocationEnum.LEEDS;
	private static final double EXPECTED_LEEDS_LON_MILES = 169.27; //according to mapdevelopers.com (as the crow flies). How accurate? Might need margin of error for rounding
	private static final double EXPECTED_EXACT_LEEDS_LON_MILES = 169.27824707819786;
	private static final float MARGIN_OF_ERROR = 0.1f;
	private static final double EXPECTED_LEEDS_LON_METRES = 272426.9313698693;
	
	@Autowired
	GpsServiceImpl gpsService;
	
	/**
	 * Method Tested: convertDegrees2radians
	 * Test returns correct value
	 */
	@Test
	void givenParamValid_convertDegrees2radians_OK() {
		double expected = TEST_DEGREES_VALUE * Math.PI / 180.0;
		double actual = gpsService.convertDegrees2radians(TEST_DEGREES_VALUE);
		assertTrue(expected == actual, "Expected degrees 2 radians conversion value does not match actual");
	}//givenParamValid_convertDegrees2radians_OK
	
	/**
	 * Method Tested: returnDistanceInMilesRoundedUp
	 * Test given two valid locations, test that the distance (in miles) between them returned 
	 * is as expected.
	 * Rounding to 2dp may vary from benchmark data (mapdevelopers.com) so a margin of error of
	 * 0.1 miles has been added to the test.
	 */
	@Test
	void givenParamsValid_expectedMilesRoundedUp_IsReturned() {
		
		double result = gpsService.returnDistanceInMilesRoundedUp(
				new LatLng(LEEDS_LOCATION.getLatitude(), LEEDS_LOCATION.getLongitude()), 
				new LatLng(LONDON_LOCATION.getLatitude(), LONDON_LOCATION.getLongitude()));
		
		double resultDiff = EXPECTED_LEEDS_LON_MILES - result;
		double resultDiffPos = resultDiff * -1; //ensure value is positive		
		assertTrue(resultDiffPos <= MARGIN_OF_ERROR, "Rounded miles between Leeds and London is incorrect");
	}//givenParamsValid_expectedMilesRoundedUp_IsReturned
	
	/**
	 * Method Tested: returnDistanceInMiles
	 * Test given two valid locations, test that the distance (in miles) between them returned 
	 * is as expected.
	 */
	@Test
	void givenParamsValid_expectedMiles_IsReturned() {
		
		double result = gpsService.returnDistanceInMiles(
				new LatLng(LEEDS_LOCATION.getLatitude(), LEEDS_LOCATION.getLongitude()), 
				new LatLng(LONDON_LOCATION.getLatitude(), LONDON_LOCATION.getLongitude()));
		
		assertTrue(result == EXPECTED_EXACT_LEEDS_LON_MILES, "Exact miles between Leeds and London is incorrect");
	}//givenParamsValid_expectedMiles_IsReturned
	
	/**
	 * Method Tested: returnDistanceInMetres
	 * Test given two valid locations, test that the distance (in metres) between them returned 
	 * is as expected.
	 */
	@Test
	void givenParamsValid_expectedMetres_IsReturned() {
		
		double result = gpsService.returnDistanceInMetres(
				new LatLng(LEEDS_LOCATION.getLatitude(), LEEDS_LOCATION.getLongitude()), 
				new LatLng(LONDON_LOCATION.getLatitude(), LONDON_LOCATION.getLongitude()));
		
		assertTrue(result == EXPECTED_LEEDS_LON_METRES, "Exact metres between Leeds and London is incorrect");
	}//givenParamsValid_expectedMetres_IsReturned
	
}
