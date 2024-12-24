create database if not exists AcademicSys;
use AcademicSys;

create table if not exists user
(
    user_id        int primary key auto_increment,
    password       varchar(128) not null,
    user_name      varchar(128) not null,
    gender         varchar(128) not null,
    school_id      bigint       not null unique,
    id_type        varchar(64)  not null,
    id_number      varchar(128) not null,
    birthday       date         not null,
    phone          varchar(64)  not null unique,
    email          varchar(128) not null unique,
    country        varchar(128) not null,
    nation         varchar(128) not null,
    political_role varchar(128) not null,
    user_addr      varchar(128),
    birth_addr     varchar(128),
    user_photo     varchar(128)
    );

create table if not exists student
(
    school_id           bigint primary key,
    foreign key (school_id) references user (school_id),
    college             varchar(256) not null,
    class_name          varchar(128) not null,
    major               varchar(128) not null,
    duration            int          not null,
    schooling_type      varchar(128) not null,
    admission_time      date         not null,
    expected_graduation date         not null,
    current_education   varchar(128) not null,
    gained_education    varchar(128) not null,
    graduated_school    varchar(128) not null,
    current_ranking     varchar(64)  not null,
    current_grades      float        not null
);

create table if not exists student_rank
(
    rank_id       int primary key auto_increment,
    school_id     bigint       not null,
    foreign key (school_id) references student (school_id),
    sem_or_year   tinyint(1)   not null,
    period_time   varchar(128) not null,
    period_rank   varchar(128) not null,
    period_grades float        not null
    );

create table if not exists teacher
(
    school_id      bigint primary key,
    foreign key (school_id) references user (school_id),
    job_title      varchar(64)  not null,
    admission_time date         not null,
    education      varchar(128) not null,
    past_education text         not null,
    past_work      text         not null,
    research       text         not null,
    teaching       text         not null
    );

create table if not exists take_office
(
    take_office_id int primary key auto_increment,
    school_id      bigint       not null,
    foreign key (school_id) references teacher (school_id),
    department     varchar(128) not null,
    work_position  varchar(128) not null,
    start_time     date         not null,
    end_time       date
);

create table if not exists take_course_time
(
    turn       bigint primary key,
    start_time varchar(128) not null,
    end_time   varchar(128) not null
);

create table if not exists course
(
    course_id         int primary key auto_increment,
    course_name       varchar(128) not null,
    credit            float        not null,
    course_department varchar(128) not null,
    category          varchar(128) not null,
    course_start_time varchar(128) not null,
    course_end_time   varchar(128) not null,
    location          varchar(128) not null,
    semester          varchar(64)  not null,
    max_students      int          not null,
    num_students      int          not null,
    test_start_time   varchar(128) not null,
    test_end_time     varchar(128) not null,
    grade_cal         varchar(128) not null,
    target            varchar(128) not null,
    description       text         not null
    );

create table if not exists course_teacher
(
    course_teacher_id int primary key auto_increment,
    course_id         int        not null,
    foreign key (course_id) references course (course_id),
    school_id         bigint     not null,
    foreign key (school_id) references user (school_id),
    ta_or_tc          tinyint(1) not null
    );

create table if not exists course_student
(
    course_student_id int primary key auto_increment,
    course_id         int          not null,
    foreign key (course_id) references course (course_id),
    student_id        bigint       not null,
    foreign key (student_id) references student (school_id),
    enroll_date       date         not null,
    status            varchar(128) not null,
    grade             decimal(5, 2),
    ord_grade         decimal(5, 2),
    exam_grade        decimal(5, 2),
    is_pass           tinyint(1)   not null default 1,
    feedback          text,
    retake            tinyint(1)   not null,
    retake_grade      decimal(5, 2)
    );

create table if not exists course_change
(
    change_id       int primary key auto_increment,
    course_id       int          not null,
    foreign key (course_id) references course (course_id),
    student_id      bigint       not null,
    foreign key (student_id) references student (school_id),
    change_date     date         not null,
    change_type     varchar(128) not null,
    change_reason   text,
    previous_status varchar(128) not null,
    new_status      varchar(128) not null,
    approved_by     bigint       not null,
    foreign key (approved_by) references user (school_id),
    approval_date   date         not null,
    record_status   varchar(64)  not null
    );

create table if not exists textbook
(
    textbook_id      int primary key auto_increment,
    book_title       varchar(256) not null,
    author           varchar(256) not null,
    edition          varchar(128) not null,
    isbn             varchar(128) not null unique,
    publisher        varchar(128) not null,
    publication_year year         not null,
    course_id        int,
    foreign key (course_id) references course (course_id),
    price            int          not null,
    book_description text,
    book_photo       varchar(128),
    stock            int          not null,
    booked           int          not null
    );

create table if not exists textbook_order
(
    order_id        int primary key auto_increment,
    textbook_id     int          not null,
    foreign key (textbook_id) references textbook (textbook_id),
    student_id      bigint       not null,
    foreign key (student_id) references student (school_id),
    quantity        int          not null,
    order_date      date         not null,
    status          varchar(64)  not null,
    total_price     int,
    delivery_class  varchar(256) not null,
    tracking_number varchar(128)
    );

create table if not exists exam
(
    exam_id         int primary key auto_increment,
    course_id       int          not null,
    foreign key (course_id) references course (course_id),
    exam_date       date         not null,
    exam_start_time time         not null,
    exam_end_time   time         not null,
    exam_location   varchar(128) not null,
    exam_type       varchar(128) not null,
    duration        int          not null,
    exam_format     varchar(64)  not null,
    total_marks     int          not null,
    pass_marks      int          not null,
    additional_info text
    );

create table if not exists exam_student
(
    exam_student_id   int primary key auto_increment,
    exam_id           int          not null,
    foreign key (exam_id) references exam (exam_id),
    student_id        bigint       not null,
    foreign key (student_id) references student (school_id),
    exam_location     varchar(128) not null,
    exam_sit          varchar(128) not null,
    exam_status       varchar(128) not null,
    exam_grade        decimal(5, 2),
    defer_or_resit    tinyint(1)   not null,
    defer_resit_grade decimal(5, 2)
    );

create table if not exists exam_teacher
(
    exam_teacher_id int primary key auto_increment,
    exam_id         int          not null,
    foreign key (exam_id) references exam (exam_id),
    school_id       bigint       not null,
    foreign key (school_id) references teacher (school_id),
    exam_role       varchar(128) not null
    );

create table if not exists questionnaire
(
    ques_id         int primary key auto_increment,
    course_id       int    not null,
    foreign key (course_id) references course (course_id),
    teacher_id      bigint not null,
    foreign key (teacher_id) references teacher (school_id),
    student_id      bigint not null,
    foreign key (student_id) references student (school_id),
    ques_start_date date   not null,
    ques_end_date   date   not null,
    ques_status     int    not null,
    ques_comment    text,
    ques_aspect1    int    not null,
    ques_aspect2    int    not null,
    ques_aspect3    int    not null,
    ques_aspect4    int    not null,
    ques_grade      decimal(5, 2)
);

create table if not exists forum_user
(
    forum_id  int       not null primary key auto_increment,
    school_id bigint    not null,
    foreign key (school_id) references user (school_id),
    reg_time  timestamp not null default current_timestamp
);

create table if not exists forum_post
(
    post_id       int        not null primary key auto_increment,
    forum_id      int        not null,
    foreign key (forum_id) references forum_user (forum_id),
    post_time     timestamp  not null default current_timestamp,
    teacher_id    bigint     not null,
    foreign key (teacher_id) references teacher (school_id),
    course_id     int        not null,
    foreign key (course_id) references course (course_id),
    grade         int        not null,
    check ( grade <= 10 and grade >= 2 ),
    content       text,
    like_count    int        not null default 0,
    dislike_count int        not null default 0,
    favor_count   int        not null default 0,
    is_top        tinyint(1) not null default 0
);

create table if not exists forum_opt
(
    forum_opt_id int         not null primary key auto_increment,
    forum_id     int         not null,
    foreign key (forum_id) references forum_user (forum_id),
    post_id      int         not null,
    foreign key (post_id) references forum_post (post_id) on delete cascade,
    opt_time     timestamp   not null default current_timestamp,
    opt_type     varchar(64) not null,
    check ( opt_type = 'like' or opt_type = 'dislike' or opt_type = 'favor' )
    );


create trigger if not exists forum_opt_insert
    after insert
    on forum_opt
    for each row
begin
    if new.opt_type = 'like' then
update forum_post set like_count = like_count + 1 where post_id = new.post_id;
elseif new.opt_type = 'dislike' then
update forum_post set dislike_count = dislike_count + 1 where post_id = new.post_id;
elseif new.opt_type = 'favor' then
update forum_post set favor_count = favor_count + 1 where post_id = new.post_id;
end if;
end;


create trigger if not exists forum_opt_delete
    after delete
on forum_opt
    for each row
begin
    if old.opt_type = 'like' then
        update forum_post set like_count = like_count - 1 where post_id = old.post_id;
    elseif old.opt_type = 'dislike' then
        update forum_post set dislike_count = dislike_count - 1 where post_id = old.post_id;
    elseif old.opt_type = 'favor' then
        update forum_post set favor_count = favor_count - 1 where post_id = old.post_id;
    end if;
end;
