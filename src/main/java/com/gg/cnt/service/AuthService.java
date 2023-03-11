package com.gg.cnt.service;

import com.gg.cnt.dto.req.LoginReq;
import com.gg.cnt.dto.req.RegisterReq;
import com.gg.cnt.dto.res.LoginRes;
import com.gg.cnt.errors.InvalidCredentialException;
import com.gg.cnt.model.User;
import com.gg.cnt.model.UserRole;
import com.gg.cnt.security.CustomUserDetails;
import com.gg.cnt.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;
    private final UserRoleService userRoleService;

    public Long register(final RegisterReq req) {
        log.info("Register user");

        User user = userService.save(
                User.builder()
                        .username(req.getUsername())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .name(req.getName())
                        .build());

        userRoleService.save(UserRole.builder()
                .userId(user.getId())
                .roleId(req.getRoleId())
                .build());
        return user.getId();
    }

    public LoginRes login(LoginReq req) {
        String username = req.getUsername();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username,
                req.getPassword()
        );
        CustomUserDetails customUserDetails;

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialException();
        }

        String roles = customUserDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = tokenProvider.createToken(customUserDetails.getId(), username, roles);

        return LoginRes.builder()
                .accessToken(accessToken)
                .roles(Set.of(roles.split(",")))
                .build();
    }
}
