package com.springproject.customerservice.configuration;

import com.springproject.customerservice.services.auth.jwt.UserService;
import com.springproject.customerservice.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



@Component
@RequiredArgsConstructor

// validate the  jwt for every api
public class JwtAuthenticationFilter extends OncePerRequestFilter { //extract the token from request

    private final JwtUtil jwtUtil;
    private final UserService userService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt= authHeader.substring(7); //Bearer is 7 digits
        userEmail = jwtUtil.extractUserName(jwt);

        if(StringUtils.isEmpty(userEmail)) {
            filterChain.doFilter(request, response);
            return;
        }

        // If userEmail is not empty, proceed with authentication logic
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
        if(jwtUtil.isTokenValid(jwt, userDetails)){
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request,response);
    }
}
