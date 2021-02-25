package com.kuliza.demo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="active_user_logs")
public class ActiveUserDetails {

    @Id
    String user_name;
}
