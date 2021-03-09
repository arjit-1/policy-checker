package com.kuliza.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "user_policy_logs")
public class UserPolicyLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long session_id;

    @Column(name = "date")
    private String date;

    @Column
    private String time;

    @Column(name = "policy_name")
    private String policy_name;

    @Column(name = "risk_acc")
    private String risk_acc;

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
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

    public String getRisk_acc() {
        return risk_acc;
    }

    public void setRisk_acc(String risk_acc) {
        this.risk_acc = risk_acc;
    }
}
