package edu.xsyu.onlinesubmit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.xsyu.onlinesubmit.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
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
    @Enumerated(EnumType.STRING)
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Manuscript> manuscripts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && password.equals(user.password) && role == user.role && name.equals(user.name) && Objects.equals(gender, user.gender) && Objects.equals(age, user.age) && Objects.equals(educationalBackground, user.educationalBackground) && Objects.equals(graduatedSchool, user.graduatedSchool) && Objects.equals(email, user.email) && phone.equals(user.phone) && Objects.equals(contactAddress, user.contactAddress) && Objects.equals(zipCode, user.zipCode) && Objects.equals(manuscripts, user.manuscripts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, name, gender, age, educationalBackground, graduatedSchool, email, phone, contactAddress, zipCode);
    }

}
