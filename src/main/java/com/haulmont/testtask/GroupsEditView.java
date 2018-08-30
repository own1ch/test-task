package com.haulmont.testtask;

import com.haulmont.db.classes.Group;
import com.haulmont.db.classes.GroupDAO;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupsEditView extends VerticalLayout implements View {

    GroupDAO groupDAO = new GroupDAO();

    TextField group;
    TextField faculty;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if(GroupsView.edit) {
            group.setValue(GroupsView.group);
            faculty.setValue(GroupsView.faculty);
        } else {
            group.setValue("");
            faculty.setValue("");
        }
    }

    public GroupsEditView() {
        group = new TextField("Группа");
        faculty = new TextField("Факультет");

        HorizontalLayout hl = new HorizontalLayout();

        Button ok = new Button("ОК");
        ok.addClickListener(clickEvent -> {
            if(!GroupsView.edit)
                groupDAO.insert(new Group(group.getValue(), faculty.getValue()));
            else {
                groupDAO.update(new Group(group.getValue(), faculty.getValue()));
            }
            Page.getCurrent().reload();
            getUI().getNavigator().navigateTo(MainUI.GROUPVIEW);
        });

        Button cancel = new Button("Отмена");
        cancel.addClickListener(clickEvent ->
                getUI().getNavigator().navigateTo(MainUI.GROUPVIEW));

        hl.addComponents(ok,cancel);
        addComponents(group,faculty,hl);
    }
}