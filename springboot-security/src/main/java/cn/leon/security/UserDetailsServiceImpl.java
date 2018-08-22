package cn.leon.security;

import cn.leon.entity.UserEntity;
import cn.leon.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userService.getByUsername(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = createAuthorities(userEntity.getRoles());

        return new User(userEntity.getUsername(), userEntity.getPassword(), simpleGrantedAuthorities);
    }


    /**
     * 权限字符串转化
     * "USER,ADMIN" -> SimpleGrantedAuthority("USER") + SimpleGrantedAuthority("ADMIN")
     *
     * @param roleStr 权限字符串
     */
    private List<SimpleGrantedAuthority> createAuthorities(String roleStr) {

        String[] roles = roleStr.split(",");

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return simpleGrantedAuthorities;
    }
}
