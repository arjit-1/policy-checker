package com.kuliza.demo.repository;

import java.util.*;

import com.kuliza.demo.model.UserPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPolicyRepository extends JpaRepository<UserPolicy,Long> {

    @Query("SELECT up from UserPolicy up where up.user_name=:un")
    List<UserPolicy> getEveryPolicy(@Param("un") String name);

}