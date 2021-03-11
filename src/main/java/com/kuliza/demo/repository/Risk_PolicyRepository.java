package com.kuliza.demo.repository;

import com.kuliza.demo.model.Risk_Policy;
import com.kuliza.demo.model.UserPolicyLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface Risk_PolicyRepository extends JpaRepository<Risk_Policy,Long>{

    @Query("SELECT upl from Risk_Policy upl where upl.policy_name=:un")
    List<Risk_Policy> findByPolicyName(@Param("un") String name);

    @Query("Select r from Risk_Policy r where r.risk_id=:id")
    List<Risk_Policy> fetchPolicyName(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from Risk_Policy rd WHERE rd.risk_id = :id")
    void deleteId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from Risk_Policy rd WHERE rd.policy_name = :name")
    void deleteName(@Param("name") String name);


    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Risk_Policy p WHERE p.policy_name = :un")
    boolean fetchByPolicyName(@Param("un") String name);
}
