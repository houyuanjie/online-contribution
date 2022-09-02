package edu.xsyu.onlinesubmit.repository;

import edu.xsyu.onlinesubmit.entity.Manuscript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManuscriptRepository extends JpaRepository<Manuscript, Long> {
}
