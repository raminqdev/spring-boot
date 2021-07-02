package com.raminq.security.configuration.security;

import com.raminq.security.service.common.PropertiesService;
import com.raminq.security.domain.entity.security.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final Logger logger;
    private final PropertiesService propertiesService;

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer("ramin_q")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + propertiesService.getJwtExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, propertiesService.getJwtSecret())
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(propertiesService.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public Claims validateAndParseAccessToken(String token) {
        try {
            return Jwts.parser().setSigningKey(propertiesService.getJwtSecret()).parseClaimsJws(token).getBody();
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return null;
    }

}
