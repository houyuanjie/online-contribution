package edu.xsyu.onlinesubmit.script;

import edu.xsyu.onlinesubmit.entity.User;
import edu.xsyu.onlinesubmit.enumeration.Role;
import edu.xsyu.onlinesubmit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DbSetup {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void run() {
        var user = new User();
        user.setUsername("user");
        user.setName("user");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.USER);
        user.setPhone("12345611111");
        userRepository.save(user);

        var admin = new User();
        admin.setUsername("admin");
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setRole(Role.ADMIN);
        admin.setPhone("12345622222");
        userRepository.save(admin);

        userRepository.findAll()
                .forEach(u -> System.out.println("[DbSetup::run] " + u.getUsername() + " saved"));

    }

}
