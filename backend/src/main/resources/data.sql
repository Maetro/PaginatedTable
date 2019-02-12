insert into role (created_by, created_date, name) values ('test', CURRENT_TIMESTAMP(), 'Role 1');
insert into role (created_by, created_date, name) values ('test', CURRENT_TIMESTAMP(), 'Role 2');
insert into user (created_by, created_date, birthdate, name, number_of_children, score, surname, user_status) values
('test', CURRENT_TIMESTAMP(), parsedatetime('17-09-1989 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'Rylan', 0, 99, 'Melia', 'ACTIVE');
insert into user (created_by, created_date, birthdate, name, number_of_children, score, surname, user_status) values
('test', CURRENT_TIMESTAMP(), parsedatetime('18-09-1989 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'Natan', 1, 50, 'Reeve', 'ACTIVE');
insert into user (created_by, created_date, birthdate, name, number_of_children, score, surname, user_status) values
('test', CURRENT_TIMESTAMP(), parsedatetime('21-09-1989 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'Arisha', 2, 47, 'Price', 'PENDING');
insert into user (created_by, created_date, birthdate, name, number_of_children, score, surname, user_status) values
('test', CURRENT_TIMESTAMP(), parsedatetime('20-09-1989 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'Tiya', 0, 70, 'Parker', 'ACTIVE');
insert into user (created_by, created_date, birthdate, name, number_of_children, score, surname, user_status) values
('test', CURRENT_TIMESTAMP(), parsedatetime('10-09-1989 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'Evangeline', 1, 20, 'Padilla', 'DELETED');
insert into user (created_by, created_date, birthdate, name, number_of_children, score, surname, user_status) values
('test', CURRENT_TIMESTAMP(), parsedatetime('22-09-1989 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 'Steve', 3, 81, 'Parker', 'PENDING');
insert into user_role (user_id, role_id) values(1, 1);
insert into user_role (user_id, role_id) values(2, 1);
insert into user_role (user_id, role_id) values(3, 1);
insert into user_role (user_id, role_id) values(4, 1);
insert into user_role (user_id, role_id) values(4, 2);
insert into user_role (user_id, role_id) values(5, 2);
insert into user_role (user_id, role_id) values(6, 2);