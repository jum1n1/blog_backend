package com.sparta.blog_backend.Llke.repository;

import com.sparta.blog_backend.Llke.entity.Like;
import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.comment.entity.Comment;
import com.sparta.blog_backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByBlogAndUser(Blog blog, User user);

    Optional<Like> findByCommentAndUser(Comment comment, User user);
}
