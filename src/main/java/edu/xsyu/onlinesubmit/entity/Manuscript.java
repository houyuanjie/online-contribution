package edu.xsyu.onlinesubmit.entity;

import edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 稿件
 */
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manuscript extends BaseEntity {

    /**
     * 作者名
     */
    @Column(nullable = false)
    @NotBlank
    private String author;

    /**
     * 题目
     */
    @Column(nullable = false)
    @NotBlank
    private String title;

    /**
     * 单位
     */
    @Column
    private String organization;

    /**
     * 摘要
     */
    @Column(nullable = false)
    @NotBlank
    private String summary;

    /**
     * 关键字
     */
    @Column(nullable = false)
    @NotBlank
    private String keywords;

    /**
     * 稿件审核状态
     */
    @Column
    @Enumerated
    private ManuscriptReviewStatus reviewStatus;

    /**
     * 投稿账户
     */
    @ManyToOne
    private User user;

    /**
     * 所属刊物
     */
    @ManyToOne
    private Publication publication;

}
