package com.sparta.blog_backend.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.blog_backend.blog.entity.Blog;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값이 들어갈 경우 아예 빼버림
public class BlogResponseDto {

    private Long id;
    private String title;
    private String name;
    private String content;
    private LocalDateTime craeteAt;
    private LocalDateTime modifiedAt;

    // 삭제시 사용
    private Boolean success;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.name = blog.getName();
        this.content = blog.getContent();
        this.craeteAt = blog.getCreateAt();
        this.modifiedAt = blog.getModifiedAt();
    }

    // 삭제시 사용
    public BlogResponseDto(Boolean success){
        this.success = success;
    }

}
