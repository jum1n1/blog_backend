package com.sparta.blog_backend.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ApiResult {

    private String msg;
    private int statusCode;

    @Builder
    public ApiResult(String msg, int statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }
}

