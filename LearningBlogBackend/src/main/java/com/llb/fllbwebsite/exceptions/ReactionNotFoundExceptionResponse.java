package com.llb.fllbwebsite.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReactionNotFoundExceptionResponse {
    private String like;
}
