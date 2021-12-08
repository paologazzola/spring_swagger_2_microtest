package it.keuzzolo.microtest.dto;

import java.util.Calendar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppDto {
	
	private String name;
	
	private Calendar date;
	
	private String ip;
	
	private String hostname;
	
	private String version;

}
