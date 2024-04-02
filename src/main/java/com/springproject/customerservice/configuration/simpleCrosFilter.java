package com.springproject.customerservice.configuration;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class simpleCrosFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        Map<String,String> map = new HashMap<>();
        String originHeader = request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "*");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus((HttpServletResponse.SC_OK));
        }else{
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig){}
}
