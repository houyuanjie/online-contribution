package edu.xsyu.onlinesubmit.controller.user;

import edu.xsyu.onlinesubmit.repository.ManuscriptRepository;
import edu.xsyu.onlinesubmit.repository.UserRepository;
import edu.xsyu.onlinesubmit.service.ManuscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class MyManuscriptsController {

    private final UserRepository userRepository;
    private final ManuscriptService manuscriptService;
    private final ManuscriptRepository manuscriptRepository;

    @Autowired
    public MyManuscriptsController(UserRepository userRepository, ManuscriptService manuscriptService, ManuscriptRepository manuscriptRepository) {
        this.userRepository = userRepository;
        this.manuscriptService = manuscriptService;
        this.manuscriptRepository = manuscriptRepository;
    }

    @GetMapping("/myManuscripts")
    public String contribution() {
        return "user/my-manuscripts";
    }

    @GetMapping("/myManuscripts/list")
    public ResponseEntity<Map<String, Object>> myManuscriptsList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit) {
        var map = new HashMap<String, Object>();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var maybeUser = userRepository.findOneByUsername(username);
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            var userId = user.getId();
            var count = manuscriptRepository.countByUserId(userId);

            if (count == 0) {
                map.put("code", 201);
                map.put("msg", "无数据");
            } else {
                map.put("code", 0);
                map.put("count", count);
                map.put("data", manuscriptService.manuscriptsListByUserId(userId, page, limit));
            }
        } else {
            map.put("code", 404);
            map.put("msg", "用户不存在");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * 删除稿件的端口
     */
    @PostMapping("/manuscript/delete/{manuscriptId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteManuscript(@PathVariable("manuscriptId") Long manuscriptId) {
        var map = new HashMap<String, Object>();

        var maybeManuscript = manuscriptRepository.findById(manuscriptId);
        if (maybeManuscript.isPresent()) {
            var manuscript = maybeManuscript.get();
            manuscriptRepository.delete(manuscript);
            map.put("code", 0);
            map.put("msg", "已删除");
        } else {
            map.put("code", 404);
            map.put("msg", "找不到 id 为 " + manuscriptId + " 的稿件");
        }

        return ResponseEntity.ok(map);
    }

}
