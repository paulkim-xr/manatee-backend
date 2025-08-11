package com.rathon.manatee.auth.config.passwordless;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameOnlyAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        UsernameOnlyAuthenticationToken token = (UsernameOnlyAuthenticationToken) authentication;
        String username = token.getPrincipal().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernameOnlyAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernameOnlyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
