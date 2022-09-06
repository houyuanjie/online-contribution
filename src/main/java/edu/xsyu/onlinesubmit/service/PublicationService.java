package edu.xsyu.onlinesubmit.service;

import edu.xsyu.onlinesubmit.entity.Manuscript;
import edu.xsyu.onlinesubmit.entity.Publication;
import edu.xsyu.onlinesubmit.repository.ManuscriptRepository;
import edu.xsyu.onlinesubmit.repository.PublicationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final ManuscriptRepository manuscriptRepository;

    public PublicationService(PublicationRepository publicationRepository, ManuscriptRepository manuscriptRepository) {
        this.publicationRepository = publicationRepository;
        this.manuscriptRepository = manuscriptRepository;
    }

    /**
     * 分页返回所有的期刊
     */
    public List<Publication> list(Integer page, Integer limit) {
        if (page != null && limit != null) {
            return publicationRepository.findAll(PageRequest.of(page - 1, limit)).toList();
        } else {
            return publicationRepository.findAll();
        }
    }

    public List<Publication> list(String category, Integer page, Integer limit) {
        if (category == null || "_ALL".equals(category)) {
            return list(page, limit);
        } else {
            if (page != null && limit != null) {
                return publicationRepository.findAllByCategory(category, PageRequest.of(page - 1, limit)).toList();
            } else {
                return publicationRepository.findAllByCategory(category);
            }
        }
    }

    /**
     * 根据期刊 id 分页返回稿件
     *
     * @deprecated 功能上由 listApprovedManuscripts 代替
     */
    @Deprecated
    public List<Manuscript> listManuscripts(Long publicationId, Integer page, Integer limit) {
        if (page != null && limit != null) {
            return manuscriptRepository.findAllByPublicationId(publicationId, PageRequest.of(page - 1, limit))
                    .toList();
        } else {
            return publicationRepository.findById(publicationId)
                    .map(Publication::getManuscripts)
                    .map(List::copyOf)
                    .orElse(List.of());
        }
    }

    /**
     * 根据期刊 id 分页返回已通过审核的稿件
     */
    public List<Manuscript> listApprovedManuscripts(Long publicationId, Integer page, Integer limit) {
        if (page != null && limit != null) {
            return manuscriptRepository.findApprovedByPublicationId(publicationId, PageRequest.of(page - 1, limit))
                    .toList();
        } else {
            return publicationRepository.findById(publicationId)
                    .map(Publication::getManuscripts)
                    .map(List::copyOf)
                    .orElse(List.of());
        }
    }
}
