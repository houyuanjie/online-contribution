package edu.xsyu.onlinesubmit.entity;

import edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

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
    @Enumerated(EnumType.STRING)
    private ManuscriptReviewStatus reviewStatus;
    /**
     * 稿件文件
     */
    @OneToOne
    private BytesFile bytesFile;
    /**
     * 投稿账户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    /**
     * 所属刊物
     */
    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manuscript that = (Manuscript) o;
        return author.equals(that.author) && title.equals(that.title) && Objects.equals(organization, that.organization) && summary.equals(that.summary) && keywords.equals(that.keywords) && reviewStatus == that.reviewStatus && Objects.equals(bytesFile, that.bytesFile) && Objects.equals(user, that.user) && Objects.equals(publication, that.publication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, organization, summary, keywords);
    }

}
