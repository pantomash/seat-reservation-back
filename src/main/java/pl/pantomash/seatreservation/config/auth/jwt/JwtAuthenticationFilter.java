package pl.pantomash.seatreservation.config.auth.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.pantomash.seatreservation.config.auth.UserPrincipalDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.pantomash.seatreservation.config.auth.jwt.JwtProperties.TOKEN_HEADER;
import static pl.pantomash.seatreservation.config.auth.jwt.JwtProperties.TOKEN_PREFIX;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserPrincipalDetailsService userPrincipalDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(TOKEN_HEADER);

        if (isHeaderValid(header)) {
            String token = header.replace("Bearer ", "");
            UserDetails userDetails = userPrincipalDetailsService
                    .loadUserByUsername(jwtUtils.extractLogin(token));

            if (isTokenValid(token, userDetails)) {
                setSecurityContextAuth(userDetails);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isHeaderValid(String header) {
        return header != null && header.startsWith(TOKEN_PREFIX);
    }

    private Boolean isTokenValid(String token, UserDetails userDetails) {
        return jwtUtils.validateToken(token, userDetails);
    }

    private void setSecurityContextAuth(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
