CREATE TABLE groups (id BIGINT IDENTITY, group_number varchar(36), faculty varchar(36), PRIMARY KEY(id));

CREATE TABLE students (id BIGINT IDENTITY PRIMARY KEY, surname varchar(36), name varchar(36), secondname varchar(36), birthday DATE not null );

ALTER TABLE students ADD group_id BIGINT not null;
ALTER TABLE students ADD foreign KEY (group_id) REFERENCES groups(id);