package bradley.scott.demoapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * DTO to represent a user
 * 
 * @author scottbradley
 */
@Getter @Setter @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String ipAddress;
	
	/*
	 * NOTE: If there is an issue casting
	 * response, some lat, lng values in 3rd
	 * party API response are string
	 * and some are double.
	 */
	private double latitude;
	private double longitude;
	
	public User(long id, String firstName, String lastName, 
			String email, String ipAddress, double latitude, 
			double longitude) {
		
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.ipAddress = ipAddress;
		this.latitude = latitude;
		this.longitude = longitude;
	}
    
}
