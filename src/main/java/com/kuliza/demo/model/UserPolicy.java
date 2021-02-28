package com.kuliza.demo.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="user_policy")
public class UserPolicy {

    @Id
    @Column(name="policy_name")
    private String policy_name;

    @Column(name="user_name")
    private String user_name;

    @Column(name="risk_Data")
    private String risk_Data;

    public String getRisk_Data() {
        return risk_Data;
    }

    public void setRisk_Data(String risk_Data) {
        this.risk_Data = risk_Data;
    }

    public UserPolicy(Long user_policyId, String policy_name, String user_name) {

        this.policy_name = policy_name;
        this.user_name = user_name;

    }

    public UserPolicy() {

    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
