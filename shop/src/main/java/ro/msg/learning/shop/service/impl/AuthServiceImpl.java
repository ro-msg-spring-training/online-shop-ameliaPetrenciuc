package ro.msg.learning.shop.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.dto.AuthResponse;
import ro.msg.learning.shop.entity.UserAccount;
import ro.msg.learning.shop.security.JwtUtil;
import ro.msg.learning.shop.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtUtil.generateToken(auth);
        String role = auth.getAuthorities().iterator().next().getAuthority();
        return new AuthResponse(token, username, role);
    }

    @Override
    public UserAccount register(UserAccount userAccount) {
        return null;
    }//soon
}
