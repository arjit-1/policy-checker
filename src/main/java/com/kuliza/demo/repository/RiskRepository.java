package com.kuliza.demo.repository;

import java.util.*;

import com.kuliza.demo.model.Risk_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRepository extends JpaRepository<Risk_Details, Long> {

    @Query("SELECT r from Risk_Details r where r.user.user_name=:un")
    List<Risk_Details> findAllRisk(@Param("un") String name);

    @Query("Select r from Risk_Details r where r.risk_id=:id")
    List<Risk_Details> findAllKeyWords(@Param("id") Long id);

    @Query(value = "DELETE from Risk_Details rd WHERE rd.risk_id = :id")
        // it will delete all the record with specific id
    void deleteByRiskId(@Param("id") Long id);

}
