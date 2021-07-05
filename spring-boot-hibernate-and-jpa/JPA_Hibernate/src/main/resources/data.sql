insert into course(id, name, created_date, last_updated_date)
values (1000, 'JPA Course', current_date (), current_date());
insert into course(id, name, created_date, last_updated_date)
values (1001, 'Statics in 100 Steps', current_date(), current_date());
insert into course(id, name, created_date, last_updated_date)
values (1002, 'Algorithms', current_date(), current_date());
insert into course(id, name, created_date, last_updated_date)
values (1003, 'Dummy1', current_date(), current_date());
insert into course(id, name, created_date, last_updated_date)
values (1004, 'Dummy2', current_date(), current_date());
insert into course(id, name, created_date, last_updated_date)
values (1005, 'Dummy3', current_date(), current_date());
insert into course(id, name, created_date, last_updated_date)
values (1006, 'Dummy4', current_date(), current_date());


insert into passport(id, number)
values (4000, 'E123456');
insert into passport(id, number)
values (4001, 'N123456');
insert into passport(id, number)
values (4002, 'L123345');


insert into student(id, name, passport_id)
values (2000, 'Ramin', 4000);
insert into student(id, name, passport_id)
values (2001, 'Alex', 4001);
insert into student(id, name, passport_id)
values (2002, 'Mary', 4002);



insert into review(id, rating, description, course_id)
values (5000, 5, 'Grate course', 1001);
insert into review(id, rating, description, course_id)
values (5001, 4, 'Wonderful course', 1001);
insert into review(id, rating, description, course_id)
values (5002, 5, 'Awesome course', 1002);

insert into student_course(student_id, course_id)
values (2001, 1001);
insert into student_course(student_id, course_id)
values (2002, 1001);
insert into student_course(student_id, course_id)
values (2000, 1001);
insert into student_course(student_id, course_id)
values (2001, 1002);
