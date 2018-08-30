package com.haulmont.db.classes;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Students")
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "name", nullable = false, length = 36)
    String name;

    @Column(name = "surname",nullable = false, length = 36)
    String surName;

    @Column(name = "secondname",nullable = false, length = 36)
    String secondName;

    @Column(name = "birthday", nullable = false)
    Date birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group groupId;

    public Student(String name, String surName, String secondName, Date birthday, Group group) {
        this.name = name;
        this.surName = surName;
        this.secondName = secondName;
        this.birthday = birthday;
        this.groupId = group;
    }

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Group getGroupId() {
        return groupId;
    }

    public void setGroupId(Group group) {
        this.groupId = group;
    }
}
