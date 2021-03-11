package com.kuliza.demo.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "risk_details")
public class Risk_Details {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "risk_id")
    private Long risk_id;

    @Column(name = "risk_title")
    @NonNull
    private String risk_title;

    @Column(name = "risk_keyword")
    private String risk_keyword;

    @Column(name = "risk_regex")
    private String risk_regex;

    @Column(name = "risk_desc")
    private String risk_Description;

    @Column(name = "match_count")
    int match_count;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    userDetails user;

    public userDetails getUser() {
        return user;
    }

    public void setUser(userDetails user) {
        this.user = user;
    }
    
    public Risk_Details() {

    }

    public Risk_Details(Long risk_id, String risk_title, String risk_keyword, String risk_regex, String risk_Description, int match_count) {
        this.risk_id = risk_id;
        this.risk_title = risk_title;
        this.risk_keyword = risk_keyword;
        this.risk_regex = risk_regex;
        this.risk_Description = risk_Description;
        this.match_count = match_count;

    }

    public Long getRisk_id() {
        return risk_id;
    }

    public void setRisk_id(Long risk_id) {
        this.risk_id = risk_id;
    }

    public String getRisk_title() {
        return risk_title;
    }

    public void setRisk_title(String risk_title) {
        this.risk_title = risk_title;
    }

    public String getRisk_keyword() {
        return risk_keyword;
    }

    public void setRisk_keyword(String risk_keyword) {
        this.risk_keyword = risk_keyword;
    }

    public String getRisk_regex() {
        return risk_regex;
    }

    public void setRisk_regex(String risk_regex) {
        this.risk_regex = risk_regex;
    }

    public String getRisk_Description() {
        return risk_Description;
    }

    public void setRisk_Description(String risk_Description) {
        this.risk_Description = risk_Description;
    }

    public int getMatch_count() {
        return match_count;
    }

    public void setMatch_count(int match_count) {
        this.match_count = match_count;
    }


}
