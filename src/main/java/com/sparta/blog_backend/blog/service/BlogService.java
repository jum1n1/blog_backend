package com.sparta.blog_backend.blog.service;

import com.sparta.blog_backend.blog.dto.BlogRequestDto;
import com.sparta.blog_backend.blog.dto.BlogResponseDto;
import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // @RequiredArgsConstructor 애너테이션을 달면 아래 코드 작성할 필요 없음
    public BlogService(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    // 1. 전체 목록 조회
    public List<BlogResponseDto> getBlogList() {
        List<Blog> blogList = blogRepository.findAllByOrderByCreateAtDesc();
        List<BlogResponseDto> blogResponseDtoList = new ArrayList<>();
        for(Blog blog : blogList){
            blogResponseDtoList.add(new BlogResponseDto(blog));
        }
        return blogResponseDtoList;
    }

    // 2. 게시글 작성
    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto) {
    // 게시글 생성 
    Blog blog = new Blog(blogRequestDto);
    // 게시글 저장
    Blog saveBlog = blogRepository.save(blog);
    return new BlogResponseDto(saveBlog);
    }

    // 3. 선택 게시글 조회
    public BlogResponseDto getBlog(Long id){
        Blog blog = findBlog(id);
        return new BlogResponseDto(blog);
    }
    
    // 4. 게시글 수정
    @Transactional // 엡데이트를 할려면 트렌젝션 환경을 만들어 줘야함=> 따로 save 안해도 됨!
    public BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto) {
        Blog blog = findBlog(id);
        //비밀번호 체크
        blog.checkPassword(blogRequestDto.getPassword());
        //필드 업데이트
        blog.setTitle(blogRequestDto.getTitle());
        blog.setName(blogRequestDto.getName());
        blog.setContent(blogRequestDto.getContent());

        return new BlogResponseDto(blog);
    }

    // 5. 게시글 삭제
    public void deleteBlog(Long id, String password) {
        Blog blog = findBlog(id);
        // 비번 체크
        blog.checkPassword(password);
        // 삭제
        blogRepository.delete(blog);
    }

    // 3. 선택 게시글 조회 - 메서드
    private Blog findBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> // orElseThrow : 값이 없을 경우
                new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
    }
}
