package com.kuliza.demo.repository;

import com.kuliza.demo.model.Risk_Details;
import com.kuliza.demo.model.UserPolicyLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPolicyLogsRepository extends JpaRepository<UserPolicyLogs, Long> {

    @Query("SELECT upl from UserPolicyLogs upl where upl.policy_name=:un")
    List<UserPolicyLogs> findByPolicyName(@Param("un") String name);
}
