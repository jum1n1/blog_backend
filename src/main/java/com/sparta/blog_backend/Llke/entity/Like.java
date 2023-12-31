package com.sparta.blog_backend.Llke.entity;

import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.comment.entity.Comment;
import com.sparta.blog_backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="blog_id",nullable = false)
    private Blog blog;

    @ManyToOne
    @JoinColumn(name="comment_id",nullable = false)
    private Comment comment;

    public Like(User user, Blog blog) {
        this.user = user;
        this.blog = blog;
    }

    public Like(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    // 좋아요 여부 반환
    public static boolean isLiked(Optional<Like> isLike) {
        return isLike.isPresent();
    }
}
