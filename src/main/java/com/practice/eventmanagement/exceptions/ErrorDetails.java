package com.practice.eventmanagement.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDetails {

	@JsonFormat(pattern = "MM-dd-yyyy hh:mm:ss")
	private Date timestamp;
	private String message;
	private String details;
	

}