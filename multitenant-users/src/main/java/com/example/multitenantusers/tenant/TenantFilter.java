package com.example.multitenantusers.tenant;

import com.example.multitenantusers.security.JwtAuthenticationFilter;
import com.example.multitenantusers.security.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor

@Component
@Order(1)
class TenantFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String tenant = jwtTokenProvider.getTenant((HttpServletRequest) request);
        TenantContext.setCurrentTenant(tenant);

        System.err.println("Current Tenant is " + tenant);
        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.setCurrentTenant("");
        }
    }
}