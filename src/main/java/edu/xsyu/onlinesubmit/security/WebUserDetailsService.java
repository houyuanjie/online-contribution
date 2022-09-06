package edu.xsyu.onlinesubmit.security;

import edu.xsyu.onlinesubmit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class WebUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public WebUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var maybeUser = userRepository.findOneByUsername(username);
        if (maybeUser.isPresent()) {
            return new WebUserDetails(maybeUser.get());
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

}
