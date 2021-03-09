package com.kuliza.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_testing_logs")
public class UserTestingLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testing_id")
    private Long testing_id;

    @Column(name = "policy_name")
    private String policy_name;

    @Column(name = "risk_id")
    private String risk_id;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name="user_name")
    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getTesting_id() {
        return testing_id;
    }

    public void setTesting_id(Long testing_id) {
        this.testing_id = testing_id;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getRisk_id() {
        return risk_id;
    }

    public void setRisk_id(String risk_id) {
        this.risk_id = risk_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
