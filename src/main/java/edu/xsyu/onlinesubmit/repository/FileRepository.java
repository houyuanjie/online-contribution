package edu.xsyu.onlinesubmit.repository;

import edu.xsyu.onlinesubmit.entity.BytesFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<BytesFile, Long> {

    @Query("SELECT file FROM BytesFile file WHERE file.fileName = :fileName")
    Optional<BytesFile> findBytesFileByFileName(@Param("fileName") String fileName);

}
