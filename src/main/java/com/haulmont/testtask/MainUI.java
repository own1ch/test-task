package com.haulmont.testtask;

import com.haulmont.db.classes.*;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    protected static final String GROUPVIEW = "group";
    protected static final String GROUPCREATEVIEW = "groupCreate";
    protected static final String STUDENTSCREATEVIEW = "studentsCreate";
    protected static final String STUDENTEDITVIEW = "studentsEdit";
    boolean flag = true;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("test");

        Navigator navigator = new Navigator(this,this);

        navigator.addView("", new StudentsView());
        navigator.addView(GROUPVIEW, new GroupsView());
        navigator.addView(GROUPCREATEVIEW, new GroupsEditView());
        navigator.addView(STUDENTSCREATEVIEW, new StudentsEditView());
        navigator.addView(STUDENTEDITVIEW, new StudentsEditView());
    }
}