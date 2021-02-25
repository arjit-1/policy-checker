package com.kuliza.demo;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface RiskRepository extends JpaRepository<Risk_Details,Long> {

    @Query("SELECT r from Risk_Details r where r.user.user_name=:un")
    List<Risk_Details> findAllRisk(@Param("un") String name);

    @Query("Select r from Risk_Details r where r.risk_id=:id")
    List<Risk_Details> findAllKeyWords(@Param("id") Long id);
}
