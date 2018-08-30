package com.haulmont.db.classes;

import com.haulmont.testtask.StudentsView;

import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {

    private String dbUrl = "jdbc:hsqldb:file:/src/main/java/com/haulmont/db/";
    private String user = "SA";
    private String password = "";
    private GroupDAO groupDAO = new GroupDAO();

    private Connection connection;

    public StudentDAO() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection(dbUrl, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Student> getAll() throws SQLException {

        final String SELECT_ALL_STUDENTS = "SELECT * FROM STUDENTS, GROUPS WHERE GROUP_ID = GROUPS.ID";
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(SELECT_ALL_STUDENTS);

        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Student student = new Student();
                student.setId(rs.getLong(1));
                student.setName(rs.getString(2));
                student.setSurName(rs.getString(3));
                student.setSecondName(rs.getString(4));
                student.setBirthday(rs.getDate(5));

                Long groupsId = rs.getLong(6);

                ArrayList<Group> groups = groupDAO.getAll();

                for(Group group: groups)
                    if(group.getId() == groupsId) {
                        student.setGroupId(group);
                        break;
                    }

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public void update(Student student) {

        try {
            PreparedStatement ps = connection.prepareStatement("update STUDENTS set name = ? , SURNAME = ?, SECONDNAME = ?, BIRTHDAY = ?, GROUP_ID = ? WHERE ID = ?");
            ps.setString(1, student.getName());
            ps.setString(2, student.getSurName());
            ps.setString(3, student.getSecondName());
            ps.setDate(4, student.getBirthday());
            ps.setLong(5,student.getGroupId().getId());
            ps.setLong(6, Long.parseLong(StudentsView.id));

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(Long id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM STUDENTS WHERE ID = " + id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insert(Student student) {

        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("INSERT INTO STUDENTS (NAME, SURNAME,SECONDNAME,BIRTHDAY,GROUP_ID)" +
                    "values (?, ?, ?, ?, ?)");

            ps.setString(1, student.getName());
            ps.setString(2, student.getSurName());
            ps.setString(3, student.getSecondName());
            ps.setDate(4, student.getBirthday());
            ps.setLong(5, student.getGroupId().getId());

            ps.executeUpdate();

            connection.commit();

            System.out.println("В базу данных добавлены значения " + student.getBirthday().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ArrayList<Student> getBySurname(String surName) {

        final String SELECT_ALL_STUDENTS = "SELECT * FROM STUDENTS, GROUPS WHERE GROUP_ID = GROUPS.ID " +
                "AND surname = ?";
        ArrayList<Student> students = new ArrayList<>();

        try {

            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_STUDENTS);

            ps.setString(1, surName);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Student student = new Student();
                student.setId(rs.getLong(1));
                student.setName(rs.getString(2));
                student.setSurName(rs.getString(3));
                student.setSecondName(rs.getString(4));
                student.setBirthday(rs.getDate(5));

                Long groupsId = rs.getLong(6);

                ArrayList<Group> groups = groupDAO.getAll();

                for(Group group: groups)
                    if(group.getId() == groupsId) {
                        student.setGroupId(group);
                        break;
                    }

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public ArrayList<Student> getByGroup(String group) {

        final String SELECT_ALL_STUDENTS = "SELECT * FROM STUDENTS, GROUPS WHERE GROUP_ID = GROUPS.ID " +
                "AND GROUP_NUMBER = ?";
        ArrayList<Student> students = new ArrayList<>();

        try {

            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_STUDENTS);

            ps.setString(1, group);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Student student = new Student();
                student.setId(rs.getLong(1));
                student.setName(rs.getString(2));
                student.setSurName(rs.getString(3));
                student.setSecondName(rs.getString(4));
                student.setBirthday(rs.getDate(5));

                Long groupsId = rs.getLong(6);

                ArrayList<Group> groups = groupDAO.getAll();

                for(Group group1: groups)
                    if(group1.getId() == groupsId) {
                        student.setGroupId(group1);
                        break;
                    }

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
}
