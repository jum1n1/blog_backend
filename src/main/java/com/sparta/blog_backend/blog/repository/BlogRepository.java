package com.sparta.blog_backend.blog.repository;

import com.sparta.blog_backend.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long> {
    List<Blog> findAllByOrderByCreateAtDesc();
}

