package edu.xsyu.onlinesubmit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
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
    @Column(length = 500)
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
    @NotBlank
    private String issn;

    /**
     * 发行频率
     */
    @Column
    private String publicationFrequency;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Manuscript> manuscripts;
    @OneToOne
    private BytesFile coverPicture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return name.equals(that.name) && category.equals(that.category) && Objects.equals(language, that.language) && Objects.equals(info, that.info) && Objects.equals(organizer, that.organizer) && Objects.equals(issn, that.issn) && Objects.equals(publicationFrequency, that.publicationFrequency) && Objects.equals(manuscripts, that.manuscripts) && Objects.equals(coverPicture, that.coverPicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, language, info, organizer, issn, publicationFrequency);
    }

}
