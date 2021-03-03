package com.kuliza.demo.repository;

import com.kuliza.demo.model.UserPolicy;
import com.kuliza.demo.model.UserTestingLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTestingLogsRepository extends JpaRepository<UserTestingLogs,Long> {


}
