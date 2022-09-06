package edu.xsyu.onlinesubmit.repository;

import edu.xsyu.onlinesubmit.entity.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    @Query("SELECT publication FROM Publication publication WHERE publication.name = :name")
    Optional<Publication> findOneByName(@Param("name") String name);

    @Query("SELECT publication FROM Publication publication WHERE publication.category = :category")
    List<Publication> findAllByCategory(@Param("category") String category);

    @Query("SELECT publication FROM Publication publication WHERE publication.category = :category")
    Page<Publication> findAllByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT COUNT(publication) FROM Publication publication WHERE publication.category = :category")
    Integer countByCategory(@Param("category") String category);

}
