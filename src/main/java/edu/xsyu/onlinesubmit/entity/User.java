package edu.xsyu.onlinesubmit.entity;

import edu.xsyu.onlinesubmit.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * 用户
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    /**
     * 账户名
     */
    @Column(unique = true, nullable = false)
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @Column(nullable = false)
    @NotBlank
    private String password;

    /**
     * 安全角色
     */
    @Column
    @Enumerated
    private Role role;

    /**
     * 姓名
     */
    @Column(nullable = false)
    @NotBlank
    private String name;

    /**
     * 性别
     */
    @Column
    private String gender;

    /**
     * 年龄
     */
    @Column
    private Integer age;

    /**
     * 学历
     */
    @Column
    private String educationalBackground;

    /**
     * 毕业学校
     */
    @Column
    private String graduatedSchool;

    /**
     * 电子邮箱
     */
    @Column
    private String email;

    /**
     * 电话号码
     */
    @Column(nullable = false)
    @NotBlank
    private String phone;

    /**
     * 通信地址
     */
    @Column
    private String contactAddress;

    /**
     * 邮政编码
     */
    @Column
    private String zipCode;

    /**
     * 已投的稿件
     */
    @OneToMany
    private Set<Manuscript> manuscripts;

}
