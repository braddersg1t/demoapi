package bradley.scott.demoapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Wrapper object for location
 * coordinates (latitude, longitude).
 * 
 * @author scottbradley
 */
@Getter @Setter @NoArgsConstructor
public class LatLng {

	private double latitude;
	private double longitude;
	
	public LatLng(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
}
