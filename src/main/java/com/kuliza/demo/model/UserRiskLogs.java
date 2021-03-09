package com.kuliza.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_risk_logs")
public class UserRiskLogs {

    @Id
    @Column(name = "session_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long session_id;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "risk_id")
    private Long risk_id;

    @Column(name = "risk_title")
    private String risk_title;

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
}
