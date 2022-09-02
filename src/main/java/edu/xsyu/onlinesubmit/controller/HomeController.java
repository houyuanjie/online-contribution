package edu.xsyu.onlinesubmit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * 应将处理页面请求的映射写在前面,
     * 这类映射一般以 String 为返回类型, 代表文件名
     * index 相当于 /WEB-INF/jsp/index.jsp
     */
    @GetMapping({"/", "/home"})
    public String index() {
        return "index";
    }

    /**
     * 要向前端返回数据时,
     * 推荐使用 ResponseEntity<Map<String, Object>> 类型,
     * 将数据放置在 data 域
     */
    @GetMapping("/welcome")
    public ResponseEntity<Map<String, Object>> welcome() {
        return ResponseEntity.ok(
                Map.of(
                        "msg", "你好, 欢迎访问在线投稿网站",
                        "code", 200,
                        "data", Map.of(
                                "content-path", "online-submit"
                        )
                )
        );
    }

}
