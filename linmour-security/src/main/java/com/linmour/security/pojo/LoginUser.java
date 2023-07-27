package com.linmour.security.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 这个是用来放回的类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private LoginVo loginVo;

    /**
     * 获取用户的权限列表，返回一个集合，其中每个元素都是用户所拥有的权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return loginVo.getPassword();
    }

    @Override
    public String getUsername() {
        return loginVo.getPhone();
    }

    /**
     * 判断用户账户是否过期，返回一个布尔值表示账户是否可用
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 判断用户账户是否被锁定，返回一个布尔值，表示账户是否未被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 判断用户的凭证（如密码）是否过期，返回一个布尔值，表示凭证是否可用
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 判断用户是否启用，返回一个布尔值，表示用户是否可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
