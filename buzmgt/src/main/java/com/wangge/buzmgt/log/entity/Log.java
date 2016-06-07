package com.wangge.buzmgt.log.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by barton on 16-6-7.
 */
@Table(name = "sys_log")
@Entity
public class Log {
    public enum EventType {
        UPDATE, DELETE, SAVE
    }

    @Id
    @GeneratedValue
    private Long id;

    private String eventUsername; // 操作人

    private String address; // ip+port;

    private Date eventDate; // 操作时间

    @Enumerated(EnumType.STRING)
    private EventType event;

    @Lob
    @Column(name = "origin_content", columnDefinition = "CLOB")
    private String origin; // 修改前的值

    @Lob
    @Column(name = "now_content", columnDefinition = "CLOB")
    private String now; // 修改后的值

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventUsername() {
        return eventUsername;
    }

    public void setEventUsername(String eventUsername) {
        this.eventUsername = eventUsername;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }
}
