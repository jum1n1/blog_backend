package com.sparta.blog_backend.blog.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BlogListResponseDto{
    private List<BlogResponseDto> blogsList;
    public BlogListResponseDto(List<BlogResponseDto> blogList) {
        this.blogsList = blogList;
    }
}
