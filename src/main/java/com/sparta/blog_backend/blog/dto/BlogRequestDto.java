package com.sparta.blog_backend.blog.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {
    private String title;
    private String name;
    private String content;
    private String password;
}
