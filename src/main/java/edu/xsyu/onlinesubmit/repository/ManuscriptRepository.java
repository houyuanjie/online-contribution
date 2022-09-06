package edu.xsyu.onlinesubmit.repository;

import edu.xsyu.onlinesubmit.entity.Manuscript;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManuscriptRepository extends JpaRepository<Manuscript, Long> {

    @Query("SELECT manuscript FROM Manuscript manuscript INNER JOIN Publication publication ON manuscript.publication = publication WHERE publication.id = :publicationId")
    Page<Manuscript> findAllByPublicationId(@Param("publicationId") Long publicationId, Pageable pageable);

    /**
     * @deprecated 功能上由 approvedCountByPublicationId 替代
     */
    @Query("SELECT COUNT(manuscript) FROM Manuscript manuscript INNER JOIN Publication publication ON manuscript.publication = publication WHERE publication.id = :publicationId")
    @Deprecated
    Integer countByPublicationId(@Param("publicationId") Long publicationId);

    @Query("SELECT manuscript FROM Manuscript manuscript INNER JOIN Publication publication ON manuscript.publication = publication WHERE publication.id = :publicationId AND manuscript.reviewStatus = edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus.Approved")
    Page<Manuscript> findApprovedByPublicationId(@Param("publicationId") Long publicationId, Pageable pageable);

    @Query("SELECT COUNT(manuscript) FROM Manuscript manuscript INNER JOIN Publication publication ON manuscript.publication = publication WHERE publication.id = :publicationId AND manuscript.reviewStatus = edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus.Approved")
    Integer approvedCountByPublicationId(@Param("publicationId") Long publicationId);

    @Query("SELECT COUNT(manuscript) FROM Manuscript manuscript WHERE manuscript.reviewStatus = edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus.NotReviewed")
    Integer NotReviewedCount();

    @Query("SELECT manuscript FROM Manuscript manuscript WHERE manuscript.reviewStatus = edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus.NotReviewed")
    List<Manuscript> findAllNotReviewed();

    @Query("SELECT manuscript FROM Manuscript manuscript WHERE manuscript.reviewStatus = edu.xsyu.onlinesubmit.enumeration.ManuscriptReviewStatus.NotReviewed")
    Page<Manuscript> findAllNotReviewed(Pageable pageable);


}
