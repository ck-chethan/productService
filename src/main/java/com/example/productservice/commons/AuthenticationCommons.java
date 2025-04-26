package com.example.productservice.commons;

import com.example.productservice.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationCommons {

    public RestTemplate restTemplate;

    public AuthenticationCommons(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String token) {
        ResponseEntity<UserDto> userDtoResponse =  restTemplate.postForEntity("http://localhost:8181/users/validate/" + token, null, UserDto.class);
        if (userDtoResponse.getStatusCode().is2xxSuccessful()) {
            return userDtoResponse.getBody();
        } else {
            return null;
        }
    }
}
