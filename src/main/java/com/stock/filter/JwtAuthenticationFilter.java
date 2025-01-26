package com.stock.filter;

import com.stock.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //  Если запрос не требует аутентификации (без аннотации @PreAuthorize("isAuthenticated()"))
        //  то пропускаем проверку токенов
        if (!requiresAuthentication(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = extractTokenFromRequest(request);

        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.isTokenValid(jwt)) {
                String email = jwtUtil.extractClaims(jwt).get("email").toString();
                var userDetails = userDetailsService.loadUserByUsername(email);
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Token without "Bearer " prefix
        }
        return null;
    }

    private boolean requiresAuthentication(HttpServletRequest request) throws Exception {
        HandlerExecutionChain handlerChain = requestMappingHandlerMapping.getHandler(request);
        if (handlerChain != null && handlerChain.getHandler() instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handlerChain.getHandler();
            PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            // Если контроллер помечен @PreAuthorize("isAuthenticated()"), требуем аутентификации
            return preAuthorize != null && preAuthorize.value().contains("isAuthenticated()");
        }
        return false;
    }

}