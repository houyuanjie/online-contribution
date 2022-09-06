package edu.xsyu.onlinesubmit.controller.editor;

import edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus;
import edu.xsyu.onlinesubmit.repository.ManuscriptRepository;
import edu.xsyu.onlinesubmit.service.ManuscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/editor")
public class ExamineController {

    private final ManuscriptRepository manuscriptRepository;
    private final ManuscriptService manuscriptService;

    @Autowired
    public ExamineController(ManuscriptRepository manuscriptRepository, ManuscriptService manuscriptService) {
        this.manuscriptRepository = manuscriptRepository;
        this.manuscriptService = manuscriptService;
    }


    /**
     * 返回页面
     */
    @GetMapping("/examine")
    public String examine() {
        return "editor/examine";
    }

    /*
     * 返回待审核稿件列表
     */
    @GetMapping("/examine/list")
    public ResponseEntity<Map<String, Object>> examineList(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit) {
        var map = new HashMap<String, Object>();

        var count = manuscriptRepository.NotReviewedCount();

        if (count == 0) {
            map.put("code", 201);
            map.put("msg", "无数据");
        } else {
            map.put("code", 0);
            map.put("count", count);
            map.put("data", manuscriptService.notReviewedList(page, limit));
        }

        return ResponseEntity.ok(map);
    }

    @PostMapping("/examine/manuscript/id/{manuscriptId}")
    public ResponseEntity<Map<String, Object>> review(
            @PathVariable("manuscriptId") Long manuscriptId,
            @RequestParam("status") String status
    ) {
        var map = new HashMap<String, Object>();

        if (status == null) {
            map.put("code", 400);
            map.put("msg", "必须传入 status 参数为 'approved' 或 'rejected'");
        } else {
            var maybeManuscript = manuscriptRepository.findById(manuscriptId);
            if (maybeManuscript.isPresent()) {
                var manuscript = maybeManuscript.get();

                if ("approved".equals(status)) {
                    manuscript.setReviewStatus(ManuscriptReviewStatus.Approved);
                } else if ("rejected".equals(status)) {
                    manuscript.setReviewStatus(ManuscriptReviewStatus.Rejected);
                } else {
                    map.put("code", 400);
                    map.put("msg", "必须传入 status 参数为 'approved' 或 'rejected'");
                    return ResponseEntity.ok(map);
                }

                manuscriptRepository.save(manuscript);
                map.put("code", 0);
                map.put("msg", "成功 " + manuscript.getReviewStatus().name());
            } else {
                map.put("code", 404);
                map.put("msg", "未找到 id 为 " + manuscriptId + " 的稿件");
            }

        }

        return ResponseEntity.ok(map);
    }

}
