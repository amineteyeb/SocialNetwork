package User.Config;

import User.jwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
    @Autowired
    private jwtService jwtService;
    @Autowired
    private final  UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String JwtToken ;
        final String Useremail;
        if (authHeader==null || !authHeader.startsWith("Bearer ") ){
            filterChain.doFilter(request,response);
        }
         JwtToken=authHeader.substring(7);
        User.jwtService jwstService;
        Useremail= jwtService.extractUserEmail(JwtToken);//to do extract usermailtoken
        if (Useremail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(Useremail);
            if (jwtService.isTokenValid(JwtToken,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
