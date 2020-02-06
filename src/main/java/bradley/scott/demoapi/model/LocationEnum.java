package bradley.scott.demoapi.model;

import lombok.Getter;

/**
 * Enum for storing city centre coordinates
 * for internal system use.
 * 
 * @author scottbradley
 */
@Getter
public enum LocationEnum {
	LEEDS("Leeds", 53.800755, -1.549077),
	LONDON("London", 51.507351, -0.127758);
	
	private String name = null;
	private double latitude = 0;
	private double longitude = 0;
	
	private LocationEnum(final String name, final double latitude, final double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}//LocationEnum
	
	
	public static LocationEnum fromLocationName(String name) {
        for (LocationEnum e : LocationEnum.values()) {
            if (e.getName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No location data found for location name: " + name);
    }//fromLocationName
	
}
