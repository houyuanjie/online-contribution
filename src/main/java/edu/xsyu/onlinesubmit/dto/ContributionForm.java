package edu.xsyu.onlinesubmit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.StringJoiner;

/**
 * 提交表单
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributionForm {
    private String category;
    private String publication;
    private String title;
    private String author;
    private String organization;
    private String keywords;
    private String summary;
    private MultipartFile file;

    @Override
    public String toString() {
        return new StringJoiner(", ", "ContributionForm { ", " }")
                .add("category: '" + category + "'")
                .add("publication: '" + publication + "'")
                .add("title: '" + title + "'")
                .add("author: '" + author + "'")
                .add("organization: '" + organization + "'")
                .add("keywords: '" + keywords + "'")
                .add("summary: '" + summary + "'")
                .add("file: MultipartFile { name: '" + file.getOriginalFilename() + "' }")
                .toString();
    }

}
