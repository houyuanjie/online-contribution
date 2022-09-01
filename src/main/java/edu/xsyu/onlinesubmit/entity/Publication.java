package edu.xsyu.onlinesubmit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * 刊物
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publication extends BaseEntity {

    /**
     * 名称
     */
    @Column(nullable = false)
    @NotBlank
    private String name;

    /**
     * 刊物类别
     */
    @Column(nullable = false)
    @NotBlank
    private String category;

    /**
     * 语种
     */
    @Column
    private String language;

    /**
     * 简介
     */
    @Column
    private String info;

    /**
     * 主办方
     */
    @Column
    private String organizer;

    /**
     * 国际标准期刊编号
     */
    @Column
    private String issn;

    /**
     * 发行频率
     */
    @Column
    private String publicationFrequency;

    @OneToMany
    private Set<Manuscript> manuscripts;

    @OneToOne
    private BytesFile picture;

}
