use academicsys;

insert into user(password, user_name, gender,
                 school_id, id_type, id_number,
                 birthday, phone, email, country,
                 nation, political_role, user_addr,
                 birth_addr, user_photo)
VALUES ('5536e62811f95b3e1a5fbede705f5218', '张三',
        'male', '10240114514', '身份证', '123456789', '1999-01-01', '330721199901011111',
        '123@qq.com', '中国', '汉族', 'masses', '北京市', '北京市', 'default.jpg');

insert into user(password, user_name, gender,
                 school_id, id_type, id_number,
                 birthday, phone, email, country,
                 nation, political_role, user_addr,
                 birth_addr, user_photo)
VALUES ('5536e62811f95b3e1a5fbede705f5218', '李四',
        'female', '20240114514', '身份证', '330721198901012222',
        '1989-01-01', '223456789', '123@123.com',
        '中国', '汉族', 'party_member', '上海市', '上海市', 'default.jpg');

insert into user(password, user_name, gender,
                 school_id, id_type, id_number,
                 birthday, phone, email, country,
                 nation, political_role, user_addr,
                 birth_addr, user_photo)
VALUES ('5536e62811f95b3e1a5fbede705f5218', '王五',
        'male', '30240114514', '身份证', '330721200101013333',
        '2001-01-01', '323456789', '123@zju.com',
        '中国', '汉族', 'masses', '杭州市', '杭州市', 'default.jpg');

insert into teacher(school_id, job_title, admission_time, education, past_education, past_work, research, teaching)
VALUES ('20240114514', '教授', '2010-01-01', '博士', '博士', '博士', '计算机', '计算机');

insert into student(school_id, college, class_name, major, duration, schooling_type, admission_time, expected_graduation,
                    current_education, gained_education, graduated_school, current_ranking, current_grades)
VALUES ('30240114514', '计算机学院', '计算机2101班', '计算机科学与技术', '4', '全日制', '2018-09-01', '2022-07-01',
        '大学本科', '高中', '杭州市学军中学', '1/100', '90.2');

insert into take_office(school_id, department, work_position, start_time, end_time) VALUES ('20240114514', '计算机学院', '教授', '2010-01-01', '2011-01-01');
insert into take_office(school_id, department, work_position, start_time, end_time) VALUES ('20240114514', '计算机学院', '副院长', '2011-01-01', '2012-01-01');
insert into take_office(school_id, department, work_position, start_time, end_time) VALUES ('20240114514', '求是学院', '院长', '2012-01-01', '2013-01-01');
insert into take_office(school_id, department, work_position, start_time, end_time) VALUES ('20240114514', '浙江大学', '校长', '2013-01-01', '2014-01-01');