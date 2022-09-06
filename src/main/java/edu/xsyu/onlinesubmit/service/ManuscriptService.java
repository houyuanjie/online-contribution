package edu.xsyu.onlinesubmit.service;

import edu.xsyu.onlinesubmit.entity.Manuscript;
import edu.xsyu.onlinesubmit.repository.ManuscriptRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManuscriptService {
    private final ManuscriptRepository manuscriptRepository;

    public ManuscriptService(ManuscriptRepository manuscriptRepository) {
        this.manuscriptRepository = manuscriptRepository;
    }

    /**
     * 分页返回所有待审核的稿件
     */
    public List<Manuscript> notReviewedList(Integer page, Integer limit) {
        if (page != null && limit != null) {
            return manuscriptRepository.findAllNotReviewed(PageRequest.of(page - 1, limit)).toList();
        } else {
            return manuscriptRepository.findAllNotReviewed();
        }
    }

}
