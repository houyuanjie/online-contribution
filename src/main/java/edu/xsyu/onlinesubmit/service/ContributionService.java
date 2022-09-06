package edu.xsyu.onlinesubmit.service;

import edu.xsyu.onlinesubmit.dto.ContributionForm;
import edu.xsyu.onlinesubmit.entity.BytesFile;
import edu.xsyu.onlinesubmit.entity.Manuscript;
import edu.xsyu.onlinesubmit.entity.User;
import edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus;
import edu.xsyu.onlinesubmit.repository.FileRepository;
import edu.xsyu.onlinesubmit.repository.ManuscriptRepository;
import edu.xsyu.onlinesubmit.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContributionService {

    private final FileRepository fileRepository;
    private final PublicationRepository publicationRepository;
    private final ManuscriptRepository manuscriptRepository;

    @Autowired
    public ContributionService(FileRepository fileRepository, PublicationRepository publicationRepository, ManuscriptRepository manuscriptRepository) {
        this.fileRepository = fileRepository;
        this.publicationRepository = publicationRepository;
        this.manuscriptRepository = manuscriptRepository;
    }

    public Map<String, Object> submit(@NonNull User user, @NonNull ContributionForm form) {
        var map = new HashMap<String, Object>();

        var maybePublication = publicationRepository.findOneByName(form.getPublication());
        if (maybePublication.isPresent()) {
            var publication = maybePublication.get();

            try {
                // 解析文件
                var bytesFile = BytesFile.from(form.getFile());
                fileRepository.save(bytesFile);

                // 解析稿件
                var manuscript = new Manuscript();
                manuscript.setAuthor(form.getAuthor());
                manuscript.setTitle(form.getTitle());
                manuscript.setOrganization(form.getOrganization());
                manuscript.setSummary(form.getSummary());
                manuscript.setKeywords(form.getKeywords());
                manuscript.setReviewStatus(ManuscriptReviewStatus.NotReviewed);
                manuscript.setBytesFile(bytesFile);
                manuscript.setUser(user);
                manuscript.setPublication(publication);
                manuscriptRepository.save(manuscript);

                map.put("title", "提交成功");
                map.put("success", true);
                map.put("msg", "您的稿件提交成功, 请等待编辑审核");

                return map;
            } catch (IOException e) {
                map.put("title", "错误");
                map.put("success", false);
                map.put("msg", "服务器未能成功解析文件");
                return map;
            }
        } else {
            // 刊物未找到
            map.put("title", "错误");
            map.put("success", false);
            map.put("msg", "未找到您选择的期刊");
            return map;
        }
    }

}
