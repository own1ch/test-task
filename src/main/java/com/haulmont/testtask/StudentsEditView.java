package com.haulmont.testtask;

import com.haulmont.db.classes.Group;
import com.haulmont.db.classes.GroupDAO;
import com.haulmont.db.classes.Student;
import com.haulmont.db.classes.StudentDAO;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StudentsEditView extends VerticalLayout implements View {

    Map<String, Long> hashMap = new HashMap<String,Long>();

    TextField surName;
    TextField firstName;
    TextField secondName;
    TextField birthDay;
    ComboBox comboBox;

    Long temp;

    GroupDAO groupDAO = new GroupDAO();
    StudentDAO studentDAO = new StudentDAO();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if(StudentsView.editStudents) {
            surName.setValue(StudentsView.surName);
            firstName.setValue(StudentsView.name);
            secondName.setValue(StudentsView.secondName);
            birthDay.setValue(StudentsView.birthDay);
            comboBox.setValue(StudentsView.group);
        }
        else {
            surName.setValue("");
            firstName.setValue("");
            secondName.setValue("");
            birthDay.setValue("");
        }

    }

    public StudentsEditView() {

        surName = new TextField("Фамилия");
        firstName = new TextField("Имя");
        secondName = new TextField("Отчество");
        birthDay = new TextField("Дата рождения(в формате ГГГГ-ММ-ДД) *");
        comboBox = new ComboBox("Группа *");

        ArrayList<Group> groups = groupDAO.getAll();

        for(Group group: groups) {
            comboBox.addItems(group.getGroupNumber());
            hashMap.put(group.getGroupNumber(), group.getId());
        }

        HorizontalLayout hl = new HorizontalLayout();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Button ok = new Button("ОК");
        ok.addClickListener(clickEvent -> {
            Student student = new Student();
            student.setName(firstName.getValue());
            student.setSurName(surName.getValue());
            student.setSecondName(secondName.getValue());

            for (Group group : groups)
                if (group.getGroupNumber().equals(comboBox.getValue())) {
                    student.setGroupId(group);
                    break;
                }

            try {
                Date utilDate = format.parse(birthDay.getValue());

                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); //преобразование даты из util  в sql

                student.setBirthday(sqlDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(!StudentsView.editStudents) {
                studentDAO.insert(student);
            } else
                studentDAO.update(student);

            Page.getCurrent().reload();
            getUI().getNavigator().navigateTo("");
        });

        Button cancel = new Button("Отмена");
        cancel.addClickListener(clickEvent ->
                getUI().getNavigator().navigateTo(""));

        Label notification = new Label("Поля со звёздочкой должны быть заполнены обязательно!");

        hl.addComponents(ok,cancel);
        addComponents(surName,firstName,secondName,birthDay,comboBox,notification, hl);
    }
}