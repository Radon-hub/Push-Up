package org.radon.pushup.features.user.application.port.out;

import org.radon.pushup.features.user.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserRepository extends UserDetailsService {
    User createUser(User user);
    User signUp(User user);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
