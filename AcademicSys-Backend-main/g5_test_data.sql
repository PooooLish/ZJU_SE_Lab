use academicsys;

insert into user(password, user_name, gender,
                 school_id, id_type, id_number,
                 birthday, phone, email, country,
                 nation, political_role, user_addr,
                 birth_addr, user_photo)
VALUES ('670b14728ad9902aecba32e22fa4f6bd', '张管理员',
        'male', '10240114514', '身份证', '123456789', '1999-01-01', '330721199901011111',
        '123@qq.com', '中国', '汉族', 'masses', '北京市', '北京市', 'default.jpg');

insert into user(password, user_name, gender,
                 school_id, id_type, id_number,
                 birthday, phone, email, country,
                 nation, political_role, user_addr,
                 birth_addr, user_photo)
VALUES ('670b14728ad9902aecba32e22fa4f6bd', '李老师',
        'female', '20240114514', '身份证', '330721198901012222',
        '1989-01-01', '223456789', '123@123.com',
        '中国', '汉族', 'party_member', '上海市', '上海市', 'default.jpg');

insert into user(password, user_name, gender,
                 school_id, id_type, id_number,
                 birthday, phone, email, country,
                 nation, political_role, user_addr,
                 birth_addr, user_photo)
VALUES ('670b14728ad9902aecba32e22fa4f6bd', '赵老师',
        'female', '20240114515', '身份证', '330721198901011111',
        '1998-01-01', '223456523', '12376@123.com',
        '中国', '汉族', 'party_member', '上海市', '上海市', 'default.jpg');

insert into user(password, user_name, gender,
                 school_id, id_type, id_number,
                 birthday, phone, email, country,
                 nation, political_role, user_addr,
                 birth_addr, user_photo)
VALUES ('670b14728ad9902aecba32e22fa4f6bd', '王同学',
        'male', '30240114514', '身份证', '330721200101013333',
        '2001-01-01', '323456789', '123@zju.com',
        '中国', '汉族', 'masses', '杭州市', '杭州市', 'default.jpg');

insert into teacher(school_id, job_title, admission_time, education, past_education, past_work, research, teaching)
VALUES ('20240114514', '教授', '2010-09-01', '博士', '本科', '上海交通大学', '计算机网络', '计算机网络');

insert into teacher(school_id, job_title, admission_time, education, past_education, past_work, research, teaching)
VALUES ('20240114515', '教授', '2010-09-01', '博士', '本科', '上海交通大学', '计算机网络', '计算机网络');

insert into student(school_id, college, class_name, major, duration, schooling_type, admission_time, expected_graduation,
                    current_education, gained_education, graduated_school, current_ranking, current_grades)
VALUES ('30240114514', '计算机学院', '计算机2101班', '计算机科学与技术', '4', '全日制', '2018-09-01', '2022-07-01',
        '大学本科', '高中', '杭州市学军中学', '1/100', '90.2');

insert into course (course_name, credit, course_department, category, course_start_time,
                    course_end_time, location, semester, max_students,
                    num_students, test_start_time, test_end_time, grade_cal, target, description)
VALUES ('计算机网络', '3', '计算机学院', 'Science', '2021-09-01', '2022-01-01', 'West4-3', 'Summer', '120', '0', 'TBD', 'TBD', 'Junior', 'TBD', 'TBD');
insert into course (course_name, credit, course_department, category, course_start_time,
                    course_end_time, location, semester, max_students,
                    num_students, test_start_time, test_end_time, grade_cal, target, description)
VALUES ('计算机组成原理', '3', '计算机学院', 'Science', '2021-09-01', '2022-01-01', 'West4-3', 'Summer', '120', '0', 'TBD', 'TBD', 'Junior', 'TBD', 'TBD');
insert into course (course_name, credit, course_department, category, course_start_time,
                    course_end_time, location, semester, max_students,
                    num_students, test_start_time, test_end_time, grade_cal, target, description)
VALUES ('操作系统', '3', '计算机学院', 'Science', '2021-09-01', '2022-01-01', 'West4-3', 'Summer', '120', '0', 'TBD', 'TBD', 'Junior', 'TBD', 'TBD');
insert into course (course_name, credit, course_department, category, course_start_time,
                    course_end_time, location, semester, max_students,
                    num_students, test_start_time, test_end_time, grade_cal, target, description)
VALUES ('数据结构基础', '3', '计算机学院', 'Science', '2021-09-01', '2022-01-01', 'West4-3', 'Summer', '120', '0', 'TBD', 'TBD', 'Junior', 'TBD', 'TBD');
insert into course (course_name, credit, course_department, category, course_start_time,
                    course_end_time, location, semester, max_students,
                    num_students, test_start_time, test_end_time, grade_cal, target, description)
VALUES ('软件工程', '3', '计算机学院', 'Science', '2021-09-01', '2022-01-01', 'West4-3', 'Summer', '120', '0', 'TBD', 'TBD', 'Junior', 'TBD', 'TBD');
insert into course (course_name, credit, course_department, category, course_start_time,
                    course_end_time, location, semester, max_students,
                    num_students, test_start_time, test_end_time, grade_cal, target, description)
VALUES ('编译原理', '3', '计算机学院', 'Science', '2021-09-01', '2022-01-01', 'West4-3', 'Summer', '120', '0', 'TBD', 'TBD', 'Junior', 'TBD', 'TBD');


insert into course_teacher(course_id, school_id, ta_or_tc) VALUES ('6', '20240114514', '1');-- 李老师 --
insert into course_teacher(course_id, school_id, ta_or_tc) VALUES ('1', '20240114514', '1');-- 李老师 --
insert into course_teacher(course_id, school_id, ta_or_tc) VALUES ('2', '20240114514', '1');-- 李老师 --
insert into course_teacher(course_id, school_id, ta_or_tc) VALUES ('3', '20240114515', '1');-- 赵老师 --
insert into course_teacher(course_id, school_id, ta_or_tc) VALUES ('4', '20240114515', '1');-- 赵老师 --
insert into course_teacher(course_id, school_id, ta_or_tc) VALUES ('5', '20240114515', '1');-- 赵老师 --

insert into forum_post(forum_id, teacher_id, course_id, grade, content)
VALUES ('1', '20240114514', '1', '3', '课程设计不合理，老师垃圾');
insert into forum_post(forum_id, teacher_id, course_id, grade, content)
VALUES ('1', '20240114514', '2', '10', '这门课很好');
insert into forum_post(forum_id, teacher_id, course_id, grade, content)
VALUES ('1', '20240114514', '6', '10', '好课！推荐！');

insert into forum_post(forum_id, teacher_id, course_id, grade, content)
VALUES ('1', '20240114515', '3', '9', '老师太棒了吧！会帮助同学们复习，最后一节课会强调期末考试重点');
insert into forum_post(forum_id, teacher_id, course_id, grade, content)
VALUES ('1', '20240114515', '4', '4', '傻逼老师，上课一点也不认真，不推荐');
insert into forum_post(forum_id, teacher_id, course_id, grade, content)
VALUES ('1', '20240114515', '5', '10', '建议课程组改革一下上课内容，能讲授一点新技术');