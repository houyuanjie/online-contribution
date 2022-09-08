package edu.xsyu.onlinesubmit.service;

import edu.xsyu.onlinesubmit.entity.Manuscript;
import edu.xsyu.onlinesubmit.entity.User;
import edu.xsyu.onlinesubmit.repository.ManuscriptRepository;
import edu.xsyu.onlinesubmit.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManuscriptService {

    private final UserRepository userRepository;
    private final ManuscriptRepository manuscriptRepository;

    public ManuscriptService(ManuscriptRepository manuscriptRepository, UserRepository userRepository) {
        this.manuscriptRepository = manuscriptRepository;
        this.userRepository = userRepository;
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

    /**
     * 分页返回特定用户的稿件
     */
    public List<Manuscript> manuscriptsListByUserId(Long userId, Integer page, Integer limit) {
        if (page != null && limit != null) {
            return manuscriptRepository.findAllByUserId(userId, PageRequest.of(page - 1, limit))
                    .toList();
        } else {
            return userRepository.findById(userId)
                    .map(User::getManuscripts)
                    .map(List::copyOf)
                    .orElse(List.of());
        }
    }

}
