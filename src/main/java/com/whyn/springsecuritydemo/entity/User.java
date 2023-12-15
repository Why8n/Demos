package com.whyn.springsecuritydemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    private Long id;
    private String name;
    private String password;
    private String role;
    private String authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authorities = this.authority.split(",");
        String rolePrefix = "ROLE_";
        String roleAuthority = this.role;
        // 将 role 转成 ROLE_XXX
        if (null != roleAuthority && !roleAuthority.startsWith(rolePrefix)) {
            roleAuthority = (rolePrefix + roleAuthority).toUpperCase();
        }

        return Stream.concat(
                        Arrays.stream(authorities),
                        Stream.of(roleAuthority)
                ).filter(Predicate.not(String::isBlank))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // UserDetailsService#loadUserByUsername(username)
    // 其中的 username 就是 getUsername，本质是一个唯一的标识，此处可使用其他唯一性字段进行代替
    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
