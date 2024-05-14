package com.tutorial.springaudit.audit;

import com.tutorial.springaudit.security.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Long> {

//    @Override
//    public Optional<String> getCurrentAuditor() {
//        return Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(e -> e.getAuthentication())
//                .map(Authentication::getName);
//    }

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getId());
    }

}