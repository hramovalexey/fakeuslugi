package com.fakeuslugi.security;

import com.fakeuslugi.security.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDao userDao;
    private final static JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    public JwtTokenFilter(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("-----------------REQUEST-HEADER------------------");
        // TODO if log level == debug
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String s = headerNames.nextElement();
            log.debug(s +" " + httpServletRequest.getHeader(s));
        }
        log.debug("-----------------END-REQUEST-HEADER------------------");
        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = header.split(" ")[1].trim(); // TODO
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        User userDetails = userDao.findByUsername(jwtTokenUtil.getUsername(token));

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails == null ? Collections.emptyList() : userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        httpServletRequest.setAttribute("user", userDetails);
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
