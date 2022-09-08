package edu.xsyu.onlinesubmit.controller.admin;

import edu.xsyu.onlinesubmit.dto.PublicationDto;
import edu.xsyu.onlinesubmit.dto.UserDto;
import edu.xsyu.onlinesubmit.entity.BytesFile;
import edu.xsyu.onlinesubmit.entity.Publication;
import edu.xsyu.onlinesubmit.entity.User;
import edu.xsyu.onlinesubmit.enumeration.Role;
import edu.xsyu.onlinesubmit.repository.FileRepository;
import edu.xsyu.onlinesubmit.repository.PublicationRepository;
import edu.xsyu.onlinesubmit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin/manage")
public class ManageController {

    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManageController(UserRepository userRepository, PublicationRepository publicationRepository, FileRepository fileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.publicationRepository = publicationRepository;
        this.fileRepository = fileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 用户管理页面
     */
    @GetMapping("/user")
    public String user() {
        return "/admin/manage/user";
    }

    @GetMapping("/user/list")
    public ResponseEntity<Map<String, Object>> userList(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit
    ) {
        var map = new HashMap<String, Object>();

        var count = userRepository.count();

        if (count == 0) {
            map.put("code", 201);
            map.put("msg", "无数据");
        } else {
            map.put("code", 0);
            map.put("count", count);
            map.put("data", userRepository.findAll(PageRequest.of(page - 1, limit)).toList());
        }

        return ResponseEntity.ok(map);
    }

    /**
     * 新增用户的接口
     */
    @PostMapping("/user/add")
    public ResponseEntity<Map<String, Object>> addUser(
            @RequestBody UserDto userDto
    ) {
        var map = new HashMap<String, Object>();

        if (userDto == null) {
            map.put("code", 400);
            map.put("msg", "表单有误(如果您是用户, 这一般不是由于您的操作造成)");
        } else {
            var user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(Role.from(userDto.getUserRole() != null ? userDto.getUserRole() : "USER"));
            user.setName(userDto.getName());
            user.setGender(userDto.getGender());
            user.setAge(userDto.getAge());
            user.setEducationalBackground(userDto.getEducationalBackground());
            user.setGraduatedSchool(userDto.getGraduatedSchool() != null ? userDto.getGraduatedSchool() : "");
            user.setEmail(userDto.getEmail() != null ? userDto.getEmail() : "");
            user.setPhone(userDto.getPhone());
            user.setContactAddress(userDto.getContactAddress() != null ? userDto.getContactAddress() : "");
            user.setZipCode(userDto.getZipCode() != null ? userDto.getZipCode() : "");
            userRepository.save(user);

            map.put("code", 0);
            map.put("msg", "成功新建用户");
        }

        return ResponseEntity.ok(map);
    }


    /**
     * 修改信息的端口
     */
    @PostMapping("/user/edit/info/{userId}")
    public ResponseEntity<Map<String, Object>> editInfo(
            @PathVariable("userId") Long userId,
            @RequestParam("name") String name,
            @RequestParam("gender") String gender,
            @RequestParam("age") Integer age,
            @RequestParam("educationalBackground") String educationalBackground,
            @RequestParam("graduatedSchool") String graduatedSchool,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("contactAddress") String contactAddress,
            @RequestParam("zipCode") String zipCode
    ) {
        var map = new HashMap<String, Object>();

        var maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            user.setName(name);
            user.setGender(gender);
            user.setAge(age);
            user.setEducationalBackground(educationalBackground);
            user.setGraduatedSchool(graduatedSchool);
            user.setEmail(email);
            user.setPhone(phone);
            user.setContactAddress(contactAddress);
            user.setZipCode(zipCode);
            userRepository.save(user);

            map.put("code", 0);
            map.put("msg", "修改成功");
        } else {
            map.put("code", 404);
            map.put("msg", "未找到 id 为 " + userId + " 的用户");
        }

        return ResponseEntity.ok(map);
    }

    /**
     * 修改密码的端口
     */
    @PostMapping("/user/edit/password/{userId}")
    public ResponseEntity<Map<String, Object>> editPassword(
            @PathVariable("userId") Long userId,
            @RequestParam("newPassword") String newPassword
    ) {
        var map = new HashMap<String, Object>();

        var maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            var oldEncodedPassword = user.getPassword();

            if (passwordEncoder.matches(newPassword, oldEncodedPassword)) {
                map.put("msg", "密码未更改");
            } else {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                map.put("msg", "已更改密码");
            }

            map.put("code", 0);
        } else {
            map.put("code", 404);
            map.put("msg", "找不到 id 为 " + userId + " 的用户");
        }

        return ResponseEntity.ok(map);
    }

    /**
     * 删除用户的端口
     */
    @PostMapping("/user/delete/{userId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("userId") Long userId) {
        var map = new HashMap<String, Object>();

        var maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            userRepository.delete(user);

            map.put("code", 0);
            map.put("msg", "已删除");
        } else {
            map.put("code", 404);
            map.put("msg", "找不到 id 为 " + userId + " 的用户");
        }

        return ResponseEntity.ok(map);
    }

    /**
     * 期刊管理页面
     */
    @GetMapping("/publication")
    public String publication(
            @RequestParam(value = "category", required = false) String category,
            ModelMap map
    ) {
        map.addAttribute("category", Objects.requireNonNullElse(category, "_ALL"));
        return "/admin/manage/publication";
    }

    /**
     * 新增期刊的接口
     */
    @PostMapping("/publication/add")
    public ResponseEntity<Map<String, Object>> addPublication(
            @RequestBody PublicationDto publicationDto
    ) {
        var map = new HashMap<String, Object>();

        if (publicationDto == null) {
            map.put("code", 400);
            map.put("msg", "表单有误(如果您是用户, 这一般不是由于您的操作造成)");
        } else {
            var publication = new Publication();
            publication.setName(publicationDto.getName());
            publication.setCategory(publicationDto.getCategory());
            publication.setLanguage(publicationDto.getLanguage() != null ? publicationDto.getLanguage() : "");
            publication.setInfo(publicationDto.getInfo() != null ? publicationDto.getInfo() : "");
            publication.setOrganizer(publicationDto.getOrganizer() != null ? publicationDto.getOrganizer() : "");
            publication.setIssn(publicationDto.getIssn() != null ? publicationDto.getIssn() : "");
            publication.setPublicationFrequency(publicationDto.getPublicationFrequency() != null ? publicationDto.getPublicationFrequency() : "");
            publicationRepository.save(publication);

            map.put("code", 0);
            map.put("msg", "成功新建期刊");
        }

        return ResponseEntity.ok(map);
    }

    /**
     * 修改期刊的端口
     */
    @PostMapping("/publication/edit/info/{publicationId}")
    public ResponseEntity<Map<String, Object>> editPublication(
            @PathVariable("publicationId") Long publicationId,
            @RequestParam("name") String name,
            @RequestParam("issn") String issn,
            @RequestParam("category") String category,
            @RequestParam("organizer") String organizer,
            @RequestParam("publicationFrequency") String publicationFrequency,
            @RequestParam("language") String language,
            @RequestParam("info") String info
    ) {
        var map = new HashMap<String, Object>();

        if (name == null || name.isBlank()) {
            map.put("code", 400);
            map.put("msg", "刊物名称不能为空");
        } else if (category == null || category.isBlank()) {
            map.put("code", 400);
            map.put("msg", "刊物类型不能为空");
        } else {
            var maybePublication = publicationRepository.findById(publicationId);
            if (maybePublication.isPresent()) {
                var publication = maybePublication.get();
                publication.setName(name);
                publication.setIssn(issn);
                publication.setCategory(category);
                publication.setOrganizer(organizer);
                publication.setPublicationFrequency(publicationFrequency);
                publication.setLanguage(language);
                publication.setInfo(info);
                publicationRepository.save(publication);

                map.put("code", 0);
                map.put("msg", "修改成功");
            } else {
                map.put("code", 404);
                map.put("msg", "未找到 id 为 " + publicationId + " 的期刊");
            }
        }

        return ResponseEntity.ok(map);
    }

    @PostMapping("/publication/set/cover-picture/{publicationId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> setCoverPicture(
            @PathVariable("publicationId") Long publicationId,
            @RequestParam("coverPicture") MultipartFile coverPicture
    ) {
        var map = new HashMap<String, Object>();

        var maybePublication = publicationRepository.findById(publicationId);
        if (maybePublication.isPresent()) {
            var publication = maybePublication.get();

            try {
                var newCoverPicture = BytesFile.from(coverPicture);
                if (newCoverPicture.equals(publication.getCoverPicture())) {
                    map.put("code", 0);
                    map.put("msg", "未更改封面图");
                } else {
                    fileRepository.delete(publication.getCoverPicture());
                    fileRepository.save(newCoverPicture);
                    publication.setCoverPicture(newCoverPicture);
                    publicationRepository.save(publication);

                    map.put("code", 0);
                    map.put("msg", "已更改封面图");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            map.put("code", 404);
            map.put("msg", "未找到 id 为 " + publicationId + " 的期刊");
        }

        return ResponseEntity.ok(map);
    }

    /**
     * 删除期刊的端口
     */
    @PostMapping("/publication/delete/{publicationId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deletePublication(@PathVariable("publicationId") Long publicationId) {
        var map = new HashMap<String, Object>();

        var maybePublication = publicationRepository.findById(publicationId);
        if (maybePublication.isPresent()) {
            var publication = maybePublication.get();
            publicationRepository.delete(publication);

            map.put("code", 0);
            map.put("msg", "已删除");
        } else {
            map.put("code", 404);
            map.put("msg", "找不到 id 为 " + publicationId + " 的期刊");
        }

        return ResponseEntity.ok(map);
    }

    /**
     * 根据期刊 id 返回动态 jsp 页面
     */
    @GetMapping("/publication/content/id/{publicationId}")
    public String publicationContent(@PathVariable("publicationId") Long publicationId, ModelMap map) {
        var maybePublication = publicationRepository.findById(publicationId);
        var publication = maybePublication.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        map.addAttribute("publication", publication);
        return "/admin/manage/publication-content";
    }
}
