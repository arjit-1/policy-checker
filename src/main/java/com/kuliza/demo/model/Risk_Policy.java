package com.kuliza.demo.model;

import javax.persistence.*;

@Entity
@Table(name="risk_policy")
public class Risk_Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="row_id")
    private Long row_id;

    @Column(name="policy_name")
    private String policy_name;

    @Column(name="risk_id")
    private Long risk_id;

    public Long getRow_id() {
        return row_id;
    }

    public void setRow_id(Long row_id) {
        this.row_id = row_id;
    }

    public String getPolicy_name() {
        return policy_name;
    }

    public void setPolicy_name(String policy_name) {
        this.policy_name = policy_name;
    }

    public Long getRisk_id() {
        return risk_id;
    }

    public void setRisk_id(Long risk_id) {
        this.risk_id = risk_id;
    }
}
