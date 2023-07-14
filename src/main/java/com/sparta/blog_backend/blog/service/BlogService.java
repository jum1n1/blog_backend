package com.sparta.blog_backend.blog.service;

import com.sparta.blog_backend.Llke.entity.Like;
import com.sparta.blog_backend.Llke.repository.LikeRepository;
import com.sparta.blog_backend.blog.dto.BlogListResponseDto;
import com.sparta.blog_backend.blog.dto.BlogRequestDto;
import com.sparta.blog_backend.blog.dto.BlogResponseDto;
import com.sparta.blog_backend.blog.entity.Blog;
import com.sparta.blog_backend.blog.repository.BlogRepository;
import com.sparta.blog_backend.user.entity.User;
import com.sparta.blog_backend.user.entity.UserRoleEnum;
import com.sparta.blog_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    // 1. 전체 목록 조회
    public BlogListResponseDto getBlogList() {
        List<BlogResponseDto> blogList = blogRepository.findAll().stream()
                .map(BlogResponseDto::new)
                .collect(Collectors.toList());;

        return new BlogListResponseDto(blogList);
    }

    // 2. 게시글 작성
    public BlogResponseDto createBlog(BlogRequestDto blogRequestDto, User user) {
    // 게시글 생성 
    Blog blog = new Blog(blogRequestDto);
    blog.setUser(user);
    // 게시글 저장
    blogRepository.save(blog);
    return new BlogResponseDto(blog);
    }

    // 3. 선택 게시글 조회
    public BlogResponseDto getBlog(Long id){
        Blog blog = findBlog(id);
        return new BlogResponseDto(blog);
    }

    // 4. 게시글 수정
    @Transactional // 엡데이트를 할려면 트렌젝션 환경을 만들어 줘야함=> 따로 save 안해도 됨!
    public BlogResponseDto updateBlog(Long id, BlogRequestDto blogRequestDto, User user) {
        Blog blog = findBlog(id);

        if(!(user.getRole().equals(UserRoleEnum.ADMIN) || blog.getUser().equals(user))){
            throw new RejectedExecutionException();
        }

        blog.setTitle(blogRequestDto.getTitle());
        blog.setContent(blogRequestDto.getContent());

        return new BlogResponseDto(blog);
    }

    // 5. 게시글 삭제
    public void deleteBlog(Long id, User user) {
        Blog blog = findBlog(id);

        if(!(user.getRole().equals(UserRoleEnum.ADMIN) || blog.getUser().equals(user))){
            throw new RejectedExecutionException();
        }
        blogRepository.delete(blog);
    }

    // 3. 선택 게시글 조회 - 메서드
    public Blog findBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> // orElseThrow : 값이 없을 경우
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }

    // 좋아요 누르기
    @Transactional
    public void upLike(Long blog_id, Long user_id) {
        Blog blog = blogRepository.findById(blog_id).orElseThrow(()->
                new IllegalArgumentException("없는 게시글입니다.")
        );
        User user = userRepository.findById(user_id).orElseThrow(() ->
                new IllegalArgumentException("로그인 정보가 없습니다.")
        );

        Optional<Like> isLike = likeRepository.findByBlogAndUser(blog,user);

        isLike.ifPresentOrElse(
                // isLike가 있는 경우
                like -> {
                    likeRepository.delete(like);
                    blog.subLikeCount(like);
                    blog.updateLikeCount();
                },
                // 없어서 새로 만들어야 하는 경우
                () ->{
                    Like like = new Like(user,blog);

                    blog.mappingFeedLike(like);
                    user.mappingLike(like);
                    blog.updateLikeCount();

                    likeRepository.save(like);
                }
        );
    }

    // 종아요 조회
    public boolean isLiked(Long blog_id, Long user_id) {
        Blog blog = blogRepository.findById(blog_id).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다"));

        User user = userRepository.findById(user_id).orElse(new User());
        Optional<Like> isLike = likeRepository.findByBlogAndUser(blog,user);
        boolean isLiked = Like.isLiked(isLike);
        return isLiked;
    }
}
