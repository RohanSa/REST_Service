package com.employee.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class AuthorizationService {

	public boolean checkIfAuthorized(String encodedString) throws UnsupportedEncodingException {

		if (encodedString == null)
			return false;

		System.out.println("EncodedString is :  " + encodedString);
		
		encodedString  = encodedString.replaceFirst("Basic"
				+ " ", "");
		byte[] decodedStringBytes = Base64.getDecoder().decode(encodedString);

		String decodedString = new String(decodedStringBytes, "UTF-8");

		System.out.println("Decoded String is : "+ decodedString);
		
		
		String[] credentials = decodedString.split(":");

		String username = credentials[0];
		String password = credentials[1];

		if ("username".equalsIgnoreCase(username) && "password".equals(password))
			return true;

		return false;
	}

}
