package com.haulmont.testtask;

import com.haulmont.db.classes.Group;
import com.haulmont.db.classes.GroupDAO;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.*;
import java.util.ArrayList;

public class GroupsView extends VerticalLayout implements View {

    Connection con;
    PreparedStatement ps;
    Statement cs;
    ResultSet rs;
    String dbUrl = "jdbc:hsqldb:file:tmp/testdb";
    Property itemProperty;

    Table table;

    public static String id;
    static String group;
    static String faculty;

    static Boolean edit = false;

    GroupDAO groupDAO = new GroupDAO();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        edit = false;

        try {
            ArrayList<Group> groups = groupDAO.getAll();
            int i = 0;
            for (Group group: groups) {
                table.addItem(new Object[]{group.getId(), group.getGroupNumber(), group.getFaculty()}, i);
                i++;
            }
        } catch (Exception e) {
            Notification.show("Ошибка при выгрузке из базы данных!");
        }
    }

    public GroupsView() {

        setSizeFull();

        setCaption("Группы");

        HorizontalLayout hl = new HorizontalLayout();

        table = new Table("Группы");
        table.setSizeFull();
        table.setSelectable(true);
        table.addContainerProperty("ID", Long.class, null);
        table.addContainerProperty("Группа", String.class, null);
        table.addContainerProperty("Факультет", String.class, null);

        table.setColumnCollapsingAllowed(true);
        table.setColumnCollapsed("ID", true);

        Button switchTable = new Button("Студенты");
        switchTable.addClickListener(clickEvent ->
                getUI().getNavigator().navigateTo(""));

        Button createGroup = new Button("Создать");
        createGroup.addClickListener(clickEvent ->
                getUI().getNavigator().navigateTo(MainUI.GROUPCREATEVIEW));

        Button editGroup = new Button("Редактировать");
        editGroup.addClickListener(clickEvent -> {
            edit = true;
            getUI().getNavigator().navigateTo(MainUI.GROUPCREATEVIEW);
        });

        //слушаем выбранную строку в таблице
        table.addListener((ItemClickEvent.ItemClickListener) itemClickEvent -> {
            itemProperty = itemClickEvent.getItem().getItemProperty("ID");
            id = itemProperty.getValue().toString();
            itemProperty = itemClickEvent.getItem().getItemProperty("Группа");
            group = itemProperty.getValue().toString();
            itemProperty = itemClickEvent.getItem().getItemProperty("Факультет");
            faculty = itemProperty.getValue().toString();
        });

        Button deleteGroup = new Button("Удалить");
        deleteGroup.addClickListener(clickEvent ->  {
                if(groupDAO.delete(Long.parseLong(id)))
                    table.removeItem(table.getValue());
        });

        try {
            ArrayList<Group> groups = groupDAO.getAll();
            int i = 0;
            for(Group group: groups){
                table.addItem(new Object[]{group.getId(), group.getGroupNumber(), group.getFaculty()}, i);
                i++;
            }
        } catch (Exception e) {
            Notification.show("Ошибка");
        }

        addComponents(table,hl);
        hl.addComponents(switchTable,createGroup,editGroup,deleteGroup);
    }
}