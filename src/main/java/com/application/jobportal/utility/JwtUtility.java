package com.application.jobportal.utility;

import com.application.jobportal.dto.LoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;


@Component
public class JwtUtility {

    final String SECRET_KEY = "this-is-a-job-portal-secret-key";

    @Autowired
    LoginDTO loginDTO;

    public JwtUtility(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public String generateToken(LoginDTO login){

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("email",login.getEmail());
        claims.put("password",login.getPassword());
        return Jwts.builder().addClaims(claims).signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();

    }

    public LoginDTO decodeToken(String token){

        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        loginDTO.setEmail((String) claims.get("email"));
        loginDTO.setPassword((String) claims.get("password"));
        return loginDTO;

    }
}