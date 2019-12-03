package com.ysh.fenu.comm.util;

import com.ysh.fenu.comm.security.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -3301605591108950415L;

    private Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    //Id
    public String getIdFromToken(String token){ return String.valueOf( this.getAllClaimsFromToken(token).get("id") ); }

    //User Id
    public String getUserIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //Ip
    public String getIpFromToken(String token){ return String.valueOf( this.getAllClaimsFromToken(token).get("ip") ); }

    //생성 시간
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    //token 종료 시간
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        if(token == null || token.isEmpty()){
            return null;
        }
        else {
            try {
                return Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
            } catch(Exception ex){
                return null;
            }
        }
    }

    public JwtUser getJwtUser(String token){
        Claims claims = this.getAllClaimsFromToken(token);
        return new JwtUser(Integer.valueOf(claims.get("userId").toString()), String.valueOf(claims.get("name")), String.valueOf(claims.get("ip")));
    }

    /*
    * 토큰 생성
    */
    public String generateToken(JwtUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getUserId());
        claims.put("name", userDetails.getName());
        claims.put("ip", userDetails.getIp());
        return doGenerateToken(claims, userDetails.getUserId());
    }

    private String doGenerateToken(Map<String, Object> claims, int subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf( subject ))
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /*
    * 토큰 새로고침
    * validate 합격 시 --> 생성시간, 종료시간 새로정의
    * */
    public String refreshExpirateToken(String token) {
        if(token == null || "".equals(token)) return null;
        else {
            final Date createdDate = clock.now();
            final Date expirationDate = calculateExpirationDate(createdDate);

            final Claims claims = getAllClaimsFromToken(token);
            claims.setIssuedAt(createdDate);
            claims.setExpiration(expirationDate);

            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        }
    }

    public Boolean validateToken(String token, String currentIp) {
        if("".equals(token)) return false;
        else {
            return (
                    this.isTokenExpired(token)
                    && this.isTokenIpValidate(token, currentIp));
        }
    }

    public Boolean isTokenIpValidate(String token, String currentIp){
        Claims claims = this.getAllClaimsFromToken(token);
        System.out.println("istoken Ip Valid =>" + claims);
        try {
            return currentIp.equals( claims.get("ip") );
        }
        catch(Exception ex){
            return false;
        }
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
