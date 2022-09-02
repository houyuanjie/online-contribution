package edu.xsyu.onlinesubmit.repository;

import edu.xsyu.onlinesubmit.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT publication FROM Publication publication WHERE publication.name = :name")
    Optional<Publication> findOneByName(@Param("name") String name);

}
