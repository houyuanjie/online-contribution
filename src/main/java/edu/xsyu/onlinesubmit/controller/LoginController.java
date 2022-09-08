package edu.xsyu.onlinesubmit.controller;

import edu.xsyu.onlinesubmit.dto.RegisterForm;
import edu.xsyu.onlinesubmit.entity.User;
import edu.xsyu.onlinesubmit.enumeration.Role;
import edu.xsyu.onlinesubmit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/modify-password")
    public String modifyPassword() {
        return "modify-password";
    }

    @PostMapping("/modifyPassword")
    public String modifyPassword(
            @RequestParam("username") String username,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            ModelMap map
    ) {
        var maybeUser = userRepository.findOneByUsername(username);
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                if (oldPassword.equals(newPassword)) {
                    map.addAttribute("title", "失败");
                    map.addAttribute("success", false);
                    map.addAttribute("msg", "未更改密码");
                } else {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    map.addAttribute("title", "成功");
                    map.addAttribute("success", true);
                    map.addAttribute("msg", "修改密码成功");
                }
            } else {
                map.addAttribute("title", "失败");
                map.addAttribute("success", false);
                map.addAttribute("msg", "旧密码不正确");
            }
        }

        return "modify-password-result";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute RegisterForm form,
            ModelMap map
    ) {
        if (form == null) {
            map.addAttribute("title", "错误");
            map.addAttribute("success", false);
            map.addAttribute("msg", "表单错误 (如果您是用户, 此错误一般不是由您的操作造成)");
        } else {
            // form != null
            var maybeUser = userRepository.findOneByUsername(form.getUsername());
            if (maybeUser.isPresent()) {
                map.addAttribute("title", "失败");
                map.addAttribute("success", false);
                map.addAttribute("msg", "该用户名已存在");
            } else {
                // 保存新用户
                var user = new User();
                user.setUsername(form.getUsername());
                user.setPassword(passwordEncoder.encode(form.getPassword()));
                user.setRole(Role.USER);
                user.setName(form.getName());
                user.setGender(form.getGender());
                user.setAge(form.getAge());
                user.setEducationalBackground(form.getEducationalBackground());
                user.setGraduatedSchool(form.getGraduatedSchool());
                user.setEmail(form.getEmail());
                user.setPhone(form.getPhone());
                user.setContactAddress(form.getContactAddress());
                user.setZipCode(form.getZipCode());
                userRepository.save(user);
                map.addAttribute("title", "注册成功");
                map.addAttribute("success", true);
                map.addAttribute("msg", "注册成功");
            }
        }

        return "register-result";
    }

}
