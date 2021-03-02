package com.llb.fllbwebsite.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostNotFoundExceptionResponse {
    private String postNotFound;
}
