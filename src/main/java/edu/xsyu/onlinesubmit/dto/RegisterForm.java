package edu.xsyu.onlinesubmit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 注册页面表单
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    private String name;
    private String gender;
    private Integer age;
    private String educationalBackground;
    private String graduatedSchool;
    private String email;
    private String phone;
    private String contactAddress;
    private String zipCode;
    private String username;
    private String password;
}
