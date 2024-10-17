package com.xiuxian.xiuxianserver.util;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 生成符合HS512要求的密钥
        System.out.println(java.util.Base64.getEncoder().encodeToString(key.getEncoded())); // 输出Base64编码的密钥
    }
}
