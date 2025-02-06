package kr.or.ddit.yguniv.security.conf.resource.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.or.ddit.yguniv.security.conf.PersonVoWrapper;
import kr.or.ddit.yguniv.security.conf.resource.dao.SecuredResourceMapper;
import kr.or.ddit.yguniv.vo.PersonVO;

public class CusUserDetailsService implements UserDetailsService {

    @Autowired
    private SecuredResourceMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonVO person = mapper.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new PersonVoWrapper(person);
    }
}


