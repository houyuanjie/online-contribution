package edu.xsyu.onlinesubmit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BytesFile extends BaseEntity {

    /**
     * 文件名
     */
    @Column(unique = true, nullable = false)
    @NotBlank
    private String fileName;

    /**
     * 大小
     */
    @Column
    private Long size;

    /**
     * 文件类型
     */
    @Column
    private String contentType;

    /**
     * 字节
     */
    @Column
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    /**
     * 从 MultipartFile 的构造器
     */
    public static BytesFile from(MultipartFile multipartFile) throws IOException {
        return new BytesFile(multipartFile.getOriginalFilename(), multipartFile.getSize(), multipartFile.getContentType(), multipartFile.getBytes());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BytesFile bytesFile = (BytesFile) o;
        return fileName.equals(bytesFile.fileName) && Objects.equals(size, bytesFile.size) && Objects.equals(contentType, bytesFile.contentType) && Arrays.equals(bytes, bytesFile.bytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, size, contentType);
    }

}
