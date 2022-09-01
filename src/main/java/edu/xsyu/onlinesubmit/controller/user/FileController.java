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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user/file")
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
    @GetMapping("/name/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) {
        var maybeFile = fileRepository.findBytesFileByFileName(fileName);
        return maybeFile.map(file ->
                        ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                                .body(file.getBytes())
                )
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 以文件在数据库中的 id 下载文件
     */
    @GetMapping("/id/{fileId}")
    public ResponseEntity<byte[]> download(@PathVariable("fileId") Long id) {
        var maybeFile = fileRepository.findById(id);
        return maybeFile.map(file ->
                        ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                                .body(file.getBytes())
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("[upload]" + Arrays.toString(file.getBytes()));
        var bytesFile = BytesFile.from(file);
        fileRepository.save(bytesFile);
        var location = servletContext.getContextPath() + "/user/file/id/" + bytesFile.getId();

        var map = new HashMap<String, Object>();
        map.put("msg", "上传成功");
        map.put("code", HttpStatus.CREATED.value());
        map.put("data", Map.of("location", location));

        return ResponseEntity.ok(map);
    }

}
