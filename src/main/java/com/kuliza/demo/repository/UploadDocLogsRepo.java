package com.kuliza.demo.repository;

import com.kuliza.demo.model.UploadDocLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UploadDocLogsRepo extends JpaRepository<UploadDocLogs, Long> {

    @Query("select d from UploadDocLogs d where d.user_name=:un")
    Set<UploadDocLogs> findAllDoc(@Param("un") String username);

}
