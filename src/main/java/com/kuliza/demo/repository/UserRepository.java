package com.kuliza.demo.repository;

import com.kuliza.demo.model.userDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<userDetails,String>
{
    @Query("Select u from userDetails u where u.user_name=?1")
    userDetails findByUser_name(String user_name);

}
