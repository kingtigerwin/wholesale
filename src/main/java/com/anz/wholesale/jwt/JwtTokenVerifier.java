package com.anz.wholesale.jwt;

import com.anz.wholesale.exceptions.WholesaleErrorDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.json.JsonMapper;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        log.debug("doFilterInternal() started");
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.info("No authorizationHeader or header not startWith Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");

        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Token is invalid or expired", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new JsonMapper().writeValue(response.getOutputStream(),
                    new WholesaleErrorDto("INVALID_TOKEN", "Token is invalid or expired"));
            return;
        }
        Claims body = claimsJws.getPayload();
        String username = body.getSubject();

        var authorities = (List<Map<String, String>>) body.get("authorities");
        Set<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(map -> new SimpleGrantedAuthority(map.get("authority")))
                .collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                grantedAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request, response);
    }
}
