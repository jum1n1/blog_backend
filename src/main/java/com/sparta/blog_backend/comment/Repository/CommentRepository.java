package com.sparta.blog_backend.comment.Repository;

import com.sparta.blog_backend.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
