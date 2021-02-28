package com.kuliza.demo.repository;

import com.kuliza.demo.model.UserRiskLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRiskLogsRepository extends JpaRepository<UserRiskLogs,Long>
{

}
