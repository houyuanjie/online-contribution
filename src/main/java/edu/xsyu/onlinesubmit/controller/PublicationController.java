package edu.xsyu.onlinesubmit.controller;

import edu.xsyu.onlinesubmit.entity.BytesFile;
import edu.xsyu.onlinesubmit.entity.Publication;
import edu.xsyu.onlinesubmit.repository.PublicationRepository;
import edu.xsyu.onlinesubmit.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationRepository publicationRepository;

    @Autowired
    public PublicationController(PublicationService publicationService, PublicationRepository publicationRepository) {
        this.publicationService = publicationService;
        this.publicationRepository = publicationRepository;
    }

    /**
     * 返回页面
     */
    @GetMapping("/publication")
    public String publication() {
        return "publication";
    }

    /**
     * 根据期刊名返回封面图
     */
    @GetMapping("/publication/cover-picture/name/{publicationName}")
    @Procedure(MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> publicationCoverPictureByName(@PathVariable("publicationName") String publicationName) {
        var maybePublication = publicationRepository.findOneByName(publicationName);
        return ResponseEntity.of(
                maybePublication.map(Publication::getCoverPicture)
                        .map(BytesFile::getBytes)
        );
    }

    /**
     * 根据期刊 id 返回封面图
     */
    @GetMapping("/publication/cover-picture/id/{publicationId}")
    @Procedure(MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> publicationCoverPictureById(@PathVariable("publicationId") Long publicationId) {
        var maybePublication = publicationRepository.findById(publicationId);
        return ResponseEntity.of(
                maybePublication.map(Publication::getCoverPicture)
                        .map(BytesFile::getBytes)
        );
    }

    /**
     * 根据期刊 id 返回动态 jsp 页面
     */
    @GetMapping("/publication/content/id/{publicationId}")
    public String publicationContent(@PathVariable("publicationId") Long publicationId, ModelMap map) {
        var maybePublication = publicationRepository.findById(publicationId);
        var publication = maybePublication.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        map.addAttribute("publication", publication);
        return "publication-content";
    }

    @GetMapping("/publication/content/id/{publicationId}/manuscript/list")
    public ResponseEntity<Map<String, Object>> manuscriptByPublicationId(@PathVariable("publicationId") Long publicationId, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        var map = new HashMap<String, Object>();
        var manuscripts = publicationService.listManuscripts(publicationId, page, limit);
        var count = manuscripts.size();

        if (count == 0) {
            map.put("code", 201);
            map.put("msg", "无数据");
        } else {
            map.put("code", 0);
            map.put("count", count);
            map.put("data", publicationService.listManuscripts(publicationId, page, limit));
        }

        return ResponseEntity.ok(map);
    }


    @GetMapping("/publication/list")
    public ResponseEntity<Map<String, Object>> publicationList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        var map = new HashMap<String, Object>();

        if (publicationRepository.count() == 0) {
            map.put("code", 201);
            map.put("msg", "无数据");
        } else {
            map.put("code", 0);
            map.put("count", publicationRepository.count());
            map.put("data", publicationService.list(page, limit));
        }

        return ResponseEntity.ok(map);
    }

}
