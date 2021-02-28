package com.kuliza.demo.repository;

import com.kuliza.demo.model.UserPolicyLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPolicyLogsRepository extends JpaRepository<UserPolicyLogs,Long> {
}
