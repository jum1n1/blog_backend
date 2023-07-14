package com.sparta.blog_backend.security;

import org.springframework.security.core.GrantedAuthority;
import com.sparta.blog_backend.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public String getUsername(){
        return user.getUsername();
    }

    // UserDetailsImpl class를 사용하기 위해 정의한 것
    // GrantedAuthority : 인터페이스를 구현하는 객체의 모임
    // => 인증된 사용자에게 부여된 권한 정보
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
        // new ArrayList을 반환했으니 어떠한 권한도 가지지 않음을 나타냄(빈 권한 목록을 반환)
    }

    @Override
    public boolean isAccountNonExpired(){ // 계정이 만료되지 않았는지를 리턴
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){ // 계정이 잠겨잇지 않은지를 리턴
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){ // 계정의 패스워드가 만료되지 않았는지 리턴
        return true;
    }

    @Override
    public boolean isEnabled(){ // 사용 가능한 계정인지를 리턴
        return true;
    }


}
