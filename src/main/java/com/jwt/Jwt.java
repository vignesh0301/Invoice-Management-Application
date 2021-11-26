package com.jwt;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;

import com.user.UserDAO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Jwt {
	
	public Jwt(){
		
	}
	
	
	//Generating JWT Token
	public String generateToken(int companyId) {
		String secret = "jhaljhKJHkjsJkskjHKHHJJHHhkhhjhHJKHKAHKHKjkhkjhjkhHKJHHKJhjjkk";

		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), 
		                            SignatureAlgorithm.HS256.getJcaName());

		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
		        .claim("companyId",companyId)
		        .setSubject("user")
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .signWith(hmacKey)
		        .compact();
		return jwtToken;

	}
	
	//Checking the Jwt token
	public Claims decodeToken(String jwtToken) {
		
		String secret = "jhaljhKJHkjsJkskjHKHHJJHHhkhhjhHJKHKAHKHKjkhkjhjkhHKJHHKJhjjkk";

		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), 
		                            SignatureAlgorithm.HS256.getJcaName());
		Jws<Claims> jwt = Jwts.parserBuilder()
	            .setSigningKey(hmacKey)
	            .build()
	            .parseClaimsJws(jwtToken);
		return jwt.getBody();
	    
	}
	
	//Getting customer id
	public int getCustomerId(String jwtToken) {
		Claims claim = decodeToken(jwtToken);
		if(claim.get("companyId")!=null) return (int) claim.get("companyId"); 
		return 0;
	}
	

}