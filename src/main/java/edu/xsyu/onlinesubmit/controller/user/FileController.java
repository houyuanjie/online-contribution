package edu.xsyu.onlinesubmit.controller.user;

import edu.xsyu.onlinesubmit.entity.BytesFile;
import edu.xsyu.onlinesubmit.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class FileController {

    private final ServletContext servletContext;
    private final FileRepository fileRepository;

    @Autowired
    public FileController(ServletContext servletContext, FileRepository fileRepository) {
        this.servletContext = servletContext;
        this.fileRepository = fileRepository;
    }

    /**
     * 以文件名下载文件
     */
    @GetMapping("/file/name/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) {
        var maybeFile = fileRepository.findBytesFileByFileName(fileName);
        return maybeFile.map(file -> {
                    var fileNameUtf8 = URLEncoder.encode(file.getFileName(), StandardCharsets.UTF_8);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream;charset=utf-8")
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileNameUtf8 + "\";filename*=UTF-8''" + fileNameUtf8)
                            .body(file.getBytes());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 以文件在数据库中的 id 下载文件
     */
    @GetMapping("/file/id/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable("fileId") Long id) {
        var maybeFile = fileRepository.findById(id);
        return maybeFile.map(file -> {
                    var fileNameUtf8 = URLEncoder.encode(file.getFileName(), StandardCharsets.UTF_8);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream;charset=utf-8")
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fileNameUtf8 + "\";filename*=UTF-8''" + fileNameUtf8)
                            .body(file.getBytes());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 文件上传接口 POST => /online-contribution/user/file/upload
     */
    @PostMapping("/file/upload")
    @Deprecated
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        var bytesFile = BytesFile.from(file);
        // fileRepository.save(bytesFile);
        // var id = bytesFile.getId();
        // var location = servletContext.getContextPath() + "/user/file/id/" + id;

        var map = new HashMap<String, Object>();
        map.put("msg", "此端口已废弃");
        map.put("code", HttpStatus.BAD_REQUEST.value());
        // map.put("data", Map.of("id", id, "location", location));

        return ResponseEntity.ok(map);
    }

}
