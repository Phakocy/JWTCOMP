package com.security.jwtcomp.controller;

import com.security.jwtcomp.model.AuthRequestDto;
import com.security.jwtcomp.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String greetings(){

        return "EKAROOOOOOOOOOOOOOOO OLOGI DE OOOOOOOOO";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String generateToken(@RequestBody AuthRequestDto authRequestDto) throws Exception{
       try{
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authRequestDto.getUserName(), authRequestDto.getPassword())
           );
       } catch (Exception ex){
           throw new Exception("Invalid username/password");
       }
      return jwtUtility.generateToken(authRequestDto.getUserName());
    }
}

