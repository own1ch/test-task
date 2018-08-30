package com.haulmont.db.classes;

import com.haulmont.testtask.GroupsView;
import com.vaadin.ui.Notification;

import java.sql.*;
import java.util.ArrayList;

public class GroupDAO {

    private String dbUrl = "jdbc:hsqldb:file:/src/main/java/com/haulmont/db/";
    private String user = "SA";
    private String password = "";

    private Connection connection;

    public GroupDAO() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Group> getAll() {

        final String SELECT_ALL_GROUPS = "SELECT ID, GROUP_NUMBER, FACULTY FROM GROUPS";
        ArrayList<Group> groups = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_GROUPS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Group group = new Group();
                group.setId(rs.getLong(1));
                group.setGroupNumber(rs.getString(2));
                group.setFaculty(rs.getString(3));
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public void update(Group group) {

        PreparedStatement statement;

        try {

            statement = connection.prepareStatement("UPDATE GROUPS set GROUP_NUMBER = ?, FACULTY = ?" +
                    "WHERE ID = ?");

            statement.setString(1, group.getGroupNumber());
            statement.setString(2, group.getFaculty());
            statement.setLong(3, Long.parseLong(GroupsView.id));

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(Long id) {

        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("DELETE FROM GROUPS WHERE ID = " + id);

            ps.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            Notification.show("В данной группе есть студенты!");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean insert(Group group) {

        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("INSERT INTO GROUPS (GROUP_NUMBER, FACULTY)" +
                    "values (?, ?)");

            ps.setString(1, group.getGroupNumber());
            ps.setString(2, group.getFaculty());

            ps.executeUpdate();

            connection.commit();

            System.out.println("В базу данных добавлены значения");
        } catch (SQLException e) {
            Notification.show("Все поля должны быть заполнены!");
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
