package User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class jwtService {
    private static final String SECRET_KEY="786678d4fca85fc0aa29c01e20a79274910e10e14123de7088370cc29c0d98f1";
    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);
    }
    public <T> T extractClaim(String jwtToken, Function<Claims,T>claimsResolver){
        final Claims claims =extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String jwtToken){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken).getBody();
    }

    private Key getSignInKey() {
        byte[] KeyBytes  = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
    public String GenerateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String GenerateToken(UserDetails userDetails){
        return GenerateToken(new HashMap<>(),userDetails);
    }
    public Boolean isTokenValid(String jwtToken ,UserDetails userDetails){
        final String username = extractUserEmail(jwtToken);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken,  Claims::getExpiration);
    }
}
