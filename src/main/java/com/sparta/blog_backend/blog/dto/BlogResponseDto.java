package com.sparta.blog_backend.blog.dto;

import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.comment.dto.CommentResponseDto;
import com.sparta.blog_backend.user.dto.ApiResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class BlogResponseDto extends ApiResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime craeteAt;
    private LocalDateTime modifiedAt;
    private String username;
    private List<CommentResponseDto> comments;

    private int likeCounts;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.craeteAt = blog.getCreateAt();
        this.modifiedAt = blog.getModifiedAt();
        this.username = blog.getUser().getUsername();
        this.comments = blog.getComments().stream()
                .map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed())
                .toList();
        this.likeCounts = blog.getLikeList().size();
    }

}
