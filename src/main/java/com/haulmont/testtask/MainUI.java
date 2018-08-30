package com.haulmont.testtask;

import com.haulmont.db.classes.Group;
import com.haulmont.db.classes.GroupDAO;
import com.haulmont.db.classes.Student;
import com.haulmont.db.classes.StudentDAO;
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

    String dbUrl = "jdbc:hsqldb:file:tmp/testdb";
    static Connection con;
    protected static final String GROUPVIEW = "group";
    protected static final String STUDENTSVIEW = "students";
    protected static final String GROUPCREATEVIEW = "groupCreate";
    protected static final String STUDENTSCREATEVIEW = "studentsCreate";
    protected static final String GROUPEDITVIEW = "groupEdit";
    protected static final String STUDENTEDITVIEW = "studentsEdit";
    protected static final String TEST = "test";

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("test");

        Navigator navigator = new Navigator(this,this);

        StudentDAO studentDAO = new StudentDAO();

        try {
            ArrayList<Student> students = studentDAO.getAll();

            for(Student student: students)
                System.out.println(student.getName() + " " + student.getSurName() + " " + student.getSecondName() + " " + student.getBirthday() + " " + student.getGroupId().getGroupNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        navigator.addView("", new StudentsView());
        navigator.addView(GROUPVIEW, new GroupsView());
        navigator.addView(GROUPCREATEVIEW, new GroupsEditView());
        navigator.addView(STUDENTSCREATEVIEW, new StudentsEditView());
        navigator.addView(STUDENTEDITVIEW, new StudentsEditView());
    }
}