package com.haulmont.testtask;

import com.haulmont.db.classes.Group;
import com.haulmont.db.classes.GroupDAO;
import com.haulmont.db.classes.Student;
import com.haulmont.db.classes.StudentDAO;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.*;
import java.util.ArrayList;

public class StudentsView extends VerticalLayout implements View {


    Statement cs;
    ResultSet rs;

    Property itemProperty;
    Table table;

    public static String id;
    static String surName;
    static String name;
    static String secondName;
    static String birthDay;
    static String group;

    static Boolean editStudents = false;

    static StudentDAO studentDAO = new StudentDAO();
    static GroupDAO groupDAO = new GroupDAO();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        editStudents = false;
    }

    public StudentsView() {

        HorizontalLayout hl = new HorizontalLayout();

        HorizontalLayout top = new HorizontalLayout();
        top.setHeight("100");
        top.setSpacing(true);

        table = new Table("Студенты");
        table.setSelectable(true);
        table.setImmediate(true);
        table.setSizeFull();
        table.setHeight("400");

        table.addContainerProperty("ID", Long.class, null);
        table.addContainerProperty("Фамилия", String.class, null);
        table.addContainerProperty("Имя", String.class, null);
        table.addContainerProperty("Отчество", String.class, null);
        table.addContainerProperty("Дата Рождения", Date.class, null);
        table.addContainerProperty("Группа", String.class, null);

        table.setColumnCollapsingAllowed(true);

        table.setColumnCollapsed("ID", true);

        table.setHeight("320");
        //слушаем выбранную строку в таблице
        table.addItemClickListener((ItemClickEvent.ItemClickListener) itemClickEvent -> {
            itemProperty = itemClickEvent.getItem().getItemProperty("ID");
            id = itemProperty.getValue().toString();
            itemProperty = itemClickEvent.getItem().getItemProperty("Имя");
            surName = itemProperty.getValue().toString();
            itemProperty = itemClickEvent.getItem().getItemProperty("Фамилия");
            name = itemProperty.getValue().toString();
            itemProperty = itemClickEvent.getItem().getItemProperty("Отчество");
            secondName = itemProperty.getValue().toString();
            itemProperty = itemClickEvent.getItem().getItemProperty("Дата Рождения");
            birthDay = itemProperty.getValue().toString();
            itemProperty = itemClickEvent.getItem().getItemProperty("Группа");
            group = itemProperty.getValue().toString();
        });

        try {
            ArrayList<Student> students = studentDAO.getAll();
            int i = 0;

            if (students != null)
                for (Student student : students) {
                    table.addItem(new Object[]{student.getId(), student.getName(), student.getSurName(), student.getSecondName(), student.getBirthday(), student.getGroupId().getGroupNumber()}, i);
                    i++;
                }
        } catch (Exception e) {
            Notification.show("Ошибка при выгрузке таблицы студентов");
            e.printStackTrace();
        }

        Button button = new Button("Группы");
        button.addClickListener(clickEvent ->
                getUI().getNavigator().navigateTo(MainUI.GROUPVIEW));

        Button createButton = new Button("Создать");
        createButton.addClickListener(clickEvent -> {
            editStudents = false;
            getUI().getNavigator().navigateTo(MainUI.STUDENTEDITVIEW);
        });

        Button editStudent = new Button("Редактировать");
        editStudent.addClickListener(clickEvent -> {
            editStudents = true;
            getUI().getNavigator().navigateTo(MainUI.STUDENTEDITVIEW);
        });

        Button deleteButton = new Button("Удалить");
        deleteButton.addClickListener(clickEvent -> {

            table.removeItem(table.getValue());

            studentDAO.delete(Long.parseLong(id));
        });

        TextField searchSurname = new TextField("Поиск по фамилии: ");

        Button searchSurnameBtn = new Button("Поиск");
        searchSurnameBtn.addClickListener(clickEvent -> {
            try {
                ArrayList<Student> students;
                table.removeAllItems();
                if(searchSurname.getValue().equals(""))
                    students = studentDAO.getAll();
                else
                    students = studentDAO.getBySurname(searchSurname.getValue());
                int i = 0;
                for(Student student: students) {
                    table.addItem(new Object[]{student.getId(), student.getName(), student.getSurName(), student.getSecondName(), student.getBirthday(),student.getGroupId().getGroupNumber()}, i);
                    i++;
                }
            } catch (Exception e) {
                Notification.show("Ошибка");
                e.printStackTrace();
            }
        });

        TextField searchGroup = new TextField("Поиск по группе: ");
        Button searchGroupBtn = new Button("Поиск");
        searchGroupBtn.addClickListener(clickEvent -> {
            try {
                ArrayList<Student> students;
                table.removeAllItems();
                if(searchGroup.getValue().equals(""))
                    students = studentDAO.getAll();
                else
                    students = studentDAO.getByGroup(searchGroup.getValue());
                int i = 0;
                for(Student student: students) {
                    table.addItem(new Object[]{student.getId(), student.getName(), student.getSurName(), student.getSecondName(), student.getBirthday(),student.getGroupId().getGroupNumber()}, i);
                    i++;
                }
            } catch (Exception e) {
                Notification.show("Ошибка");
                e.printStackTrace();
            }
        });

        top.addComponents(searchSurname,searchSurnameBtn,searchGroup,searchGroupBtn);
        hl.addComponents(button, createButton, editStudent, deleteButton);
        addComponents(table, hl, top);

    }
}