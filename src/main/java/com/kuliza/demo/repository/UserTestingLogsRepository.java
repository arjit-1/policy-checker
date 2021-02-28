package com.kuliza.demo.repository;

import com.kuliza.demo.model.UserTestingLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTestingLogsRepository extends JpaRepository<UserTestingLogs,Long> {
}
