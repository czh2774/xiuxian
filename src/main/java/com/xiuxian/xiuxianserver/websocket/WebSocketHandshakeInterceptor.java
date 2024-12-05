package com.xiuxian.xiuxianserver.websocket;

import com.xiuxian.xiuxianserver.util.JwtTokenUtil;
import com.xiuxian.xiuxianserver.service.CharacterProfileService;
import com.xiuxian.xiuxianserver.entity.CharacterProfile;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    
    private final JwtTokenUtil jwtTokenUtil;
    private final CharacterProfileService characterProfileService;
    private static final Logger log = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        
        String token = extractTokenFromRequest(request);
        if (token == null) {
            log.warn("WebSocket connection attempt without token");
            return false;
        }

        try {
            // 从token获取用户信息
            Long platformUserId = jwtTokenUtil.getClaim(token, "platformUserId", Long.class);
            
            // 获取用户当前的角色
            CharacterProfile character = characterProfileService.findByPlayerId(platformUserId);
            
            // 存储到WebSocket session
            attributes.put("platformUserId", platformUserId);
            attributes.put("currentCharacterId", character.getId());
            
            log.info("WebSocket handshake successful for user: {}, character: {}", 
                platformUserId, character.getId());
            return true;
            
        } catch (Exception e) {
            log.error("WebSocket handshake failed: ", e);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception exception) {
        // 握手后的处理
    }
    
    private String extractTokenFromRequest(ServerHttpRequest request) {
        List<String> token = request.getHeaders().get("Authorization");
        if (token != null && !token.isEmpty()) {
            String bearerToken = token.get(0);
            if (bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
        }
        return null;
    }
} 