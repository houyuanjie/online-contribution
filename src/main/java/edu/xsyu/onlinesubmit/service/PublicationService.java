package edu.xsyu.onlinesubmit.service;

import edu.xsyu.onlinesubmit.entity.Manuscript;
import edu.xsyu.onlinesubmit.entity.Publication;
import edu.xsyu.onlinesubmit.repository.PublicationRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
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

    /**
     * 根据期刊 id 分页返回稿件
     */
    public Collection<Manuscript> listManuscripts(Long publicationId, Integer page, Integer limit) {
        var maybePublication = publicationRepository.findById(publicationId);
        var maybeManuscripts = maybePublication.map(Publication::getManuscripts);

        if (page != null && limit != null) {
            return maybeManuscripts.map(Collection::stream)
                    .map(stream -> stream.skip((long) (page - 1) * limit).limit(limit))
                    .orElse(Stream.empty())
                    .collect(Collectors.toUnmodifiableList());
        } else {
            return maybeManuscripts.orElse(Set.of());
        }
    }
}
