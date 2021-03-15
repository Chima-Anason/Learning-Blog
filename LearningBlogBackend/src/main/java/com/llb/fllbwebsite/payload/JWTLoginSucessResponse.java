package com.llb.fllbwebsite.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTLoginSucessResponse {

    private boolean sucess;
    private String token;
}
