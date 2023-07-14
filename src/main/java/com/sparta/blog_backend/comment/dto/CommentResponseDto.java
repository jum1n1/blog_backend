package com.sparta.blog_backend.comment.dto;

import com.sparta.blog_backend.comment.entity.Comment;
import com.sparta.blog_backend.user.dto.ApiResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends ApiResponseDto {

    private String body;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    public CommentResponseDto(Comment comment) {
        super();
        this.body = comment.getBody();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }

}
