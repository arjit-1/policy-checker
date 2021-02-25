package com.kuliza.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRiskSessionRepository extends JpaRepository<UserRiskLogs,Long>
{

}
