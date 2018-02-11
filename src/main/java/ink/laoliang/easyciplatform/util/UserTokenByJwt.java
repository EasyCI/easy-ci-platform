package ink.laoliang.easyciplatform.util;

import ink.laoliang.easyciplatform.domain.User;
import ink.laoliang.easyciplatform.domain.UserRepository;
import ink.laoliang.easyciplatform.exception.IllegalUserTokenException;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class UserTokenByJwt {

    // Jwt 的 Token 有效时长（毫秒数）
    private final static long VALID_TIME = 1000 * 60 * 60 * 24 * 7; // 7 天

    // 为 Token 签名的 算法
    private final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    // 为 Token 签名的 Key
//    private final static Key SIGNING_KEY = MacProvider.generateKey(); // 此方式重启服务，签名就会改变
    private final static Key SIGNING_KEY = new SecretKeySpec("MyKey".getBytes(), SIGNATURE_ALGORITHM.getJcaName());

    /**
     * 为用户创建一个 token
     *
     * @param user
     * @return
     */
    public static String createToken(User user) {
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        long nowTime = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setId(email) // id 存放邮箱
                .setIssuer(username) // issuer 存放用户名
                .setSubject(password) // subject 存放密码
                .setIssuedAt(new Date(nowTime)) // 发布时间
                .setExpiration(new Date(nowTime + VALID_TIME)) // 过期时间
                .signWith(SIGNATURE_ALGORITHM, SIGNING_KEY);

        return builder.compact();
    }

    /**
     * 解析用户 Token
     *
     * @param userToken
     * @param userRepository
     */
    public static User parserToken(String userToken, UserRepository userRepository) {
        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(userToken)
                    .getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new IllegalUserTokenException("【Token】非法，Token 已过期，请重新登录");
        }

        User user = userRepository.findOne(body.getId());
        if (user == null) {
            throw new IllegalUserTokenException("【Token】非法，用户不存在或已被删除");
        }
        if (!user.getUsername().equals(body.getIssuer())) {
            throw new IllegalUserTokenException("【Token】非法，用户信息不匹配");
        }
        if (!user.getPassword().equals(body.getSubject())) {
            throw new IllegalUserTokenException("【Token】非法，用户密码不匹配");
        }
        return user;
    }
}
