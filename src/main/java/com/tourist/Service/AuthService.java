package com.tourist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourist.Configs.JwtTokenUtil;

@Service
public class AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
}
