package DeviceMng.devicemng.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class LoginTokenService {
    private String secretKey="";

    public LoginTokenService(){
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // xac dinh thuat toan de ky JWT
            this.secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String createLoginToken(String username) {
        Map<String, Object> data = new HashMap<>();
        return Jwts.builder()      // khoi tao jwt
                .claims()           // tao payload cho token
                .add(data)           // them cac thong tin payload
                .subject(username)    // chi dinh danh tinh cua token
                .issuedAt(new Date(System.currentTimeMillis()))// thiet lap thoi diem tao token
                .expiration(new Date(System.currentTimeMillis()+60*60*30))//thoi diem het han cua token
                .and()               // ket thuc payload va chuyen sang buoc ky token
                .signWith(getKey())   // ky token banwg thuat toan ma hoa
                .compact();     //ket thuc xay dung token va tra ve token duoi dang chuoi
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        final String username = extractUserName(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpored(jwtToken));
    }

    private boolean isTokenExpored(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractUserName(jwtToken, Claims::getExpiration);
    }

    public String extractUserName(String jwtToken) {
        return extractUserName(jwtToken, Claims::getSubject);
    }

    private <T> T extractUserName(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build().parseClaimsJws(token).getPayload();
    }
}
