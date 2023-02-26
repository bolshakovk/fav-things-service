package com.bolshakovk.favthingsservice.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if(token!=null && jwtTokenProvider.validateToken(token)){
                Authentication authentication = jwtTokenProvider.getAuthentifocation(token);
                if ((authentication != null)){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (Exception e){
            SecurityContextHolder.clearContext();
            ((HttpServletResponse) servletResponse).sendError(123);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
