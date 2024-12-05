package com.xiuxian.xiuxianserver.util;

import com.xiuxian.xiuxianserver.exception.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * JwtTokenUtil 用于处理 JWT Token 的生成、解析和验证。
 */
@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 检查 Token 是否有效（非过期且合法）。
     *
     * @param token 要验证的 JWT Token
     * @return 如果 Token 有效则返回 true，否则返回 false
     */
    public Boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Token 无效: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从 Token 中提取指定 Claim 值。
     *
     * @param token 要解析的 JWT Token
     * @param claimName 要提取的 Claim 名称
     * @param requiredType 要提取的 Claim 类型
     * @param <T> 返回类型
     * @return 提取的 Claim 值
     * @throws AuthenticationException 如果 Token 无效或解析失败
     */
    public <T> T getClaim(String token, String claimName, Class<T> requiredType) {
        logger.debug("提取 Claim - 名称: {}, 类型: {}", claimName, requiredType.getSimpleName());
        return getClaimFromToken(token, claims -> claims.get(claimName, requiredType));
    }

    /**
     * 解析 Token 并提取指定的 Claim。
     *
     * @param token 要解析的 JWT Token
     * @param claimsResolver 从 Claims 中提取信息的函数
     * @param <T> 返回类型
     * @return 从 Token 中解析出的 Claim
     * @throws AuthenticationException 如果 Token 无效或解析失败
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        logger.debug("开始解析 Token");
        if (token == null) {
            throw new IllegalArgumentException("Token 不能为空");
        }
        // 去掉 'Bearer ' 前缀以确保 Token 格式正确
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        try {
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (SecurityException e) {
            logger.error("无效的 JWT 签名: {}", token, e);
            throw new AuthenticationException("无效的 JWT 签名", e);
        } catch (ExpiredJwtException e) {
            logger.warn("JWT 已过期: {}", token, e);
            throw new AuthenticationException("JWT 已过期", e);
        } catch (MalformedJwtException e) {
            logger.error("JWT 格式无效: {}", token, e);
            throw new AuthenticationException("JWT 格式无效", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT 解析错误: {}", token, e);
            throw new AuthenticationException("JWT 解析错误", e);
        }
    }

    /**
     * 检查 Token 是否已过期。
     *
     * @param token 要检查的 JWT Token
     * @return 如果 Token 已过期则返回 true，否则返回 false
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaimFromToken(token, Claims::getExpiration);
        boolean isExpired = expiration.before(new Date());
        if (isExpired) {
            logger.warn("Token 已过期。过期时间: {}, 当前时间: {}", expiration, new Date());
        } else {
            logger.debug("Token 有效。过期时间: {}", expiration);
        }
        return isExpired;
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
