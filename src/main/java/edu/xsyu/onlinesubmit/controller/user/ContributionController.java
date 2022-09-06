package edu.xsyu.onlinesubmit.controller.user;

import edu.xsyu.onlinesubmit.dto.ContributionForm;
import edu.xsyu.onlinesubmit.repository.UserRepository;
import edu.xsyu.onlinesubmit.service.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class ContributionController {

    private final UserRepository userRepository;
    private final ContributionService contributionService;

    @Autowired
    public ContributionController(UserRepository userRepository, ContributionService contributionService) {
        this.userRepository = userRepository;
        this.contributionService = contributionService;
    }

    @GetMapping("/contribution")
    public String contribution() {
        return "user/contribution";
    }

    @PostMapping("/contribution/submit")
    public String submit(
            @ModelAttribute ContributionForm form,
            ModelMap map
    ) {
        if (form == null) {
            map.addAttribute("title", "错误");
            map.addAttribute("success", false);
            map.addAttribute("msg", "表单错误 (如果您是用户, 此错误一般不是由您的操作造成)");
        } else {
            // form != null
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            var username = authentication.getName();
            var maybeUser = userRepository.findOneByUsername(username);
            if (maybeUser.isPresent()) {
                var user = maybeUser.get();
                // 调用服务层
                var result = contributionService.submit(user, form);
                map.addAllAttributes(result);
            } else {
                map.addAttribute("title", "未认证");
                map.addAttribute("success", false);
                map.addAttribute("msg", "未认证错误 (如果您是用户, 此错误一般不是由您的操作造成)");
            }
        }

        return "user/contribution-result";
    }

}
