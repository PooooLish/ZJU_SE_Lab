数据库将使用 **MySQL** 实现:

**user** 表，用于存放账户信息：

| 字段名         | 类型           | 描述                 | 备注        |
| -------------- |--------------| -------------------- |-----------|
| user_id        | unsigned int | 用户标识 id          | 主键，非空，自增  |
| passwd         | varchar(128) | 密码 MD5 加密        | 非空，有默认    |
| user_name      | varchar(128) | 姓名                 | 非空        |
| gender         | varchar(16)  | 性别                 | 非空，限定内容   |
| school_id      | bigint       | 学工号               | 非空，unique |
| id_type        | varchar(64)  | 类型 (身份证/护照等) | 非空，限定内容   |
| id_number      | varchar(128) | 身份证明信息         | 非空，unique |
| birthday       | date         | 出生年月日           | 非空，限定格式   |
| phone          | varchar(64)  | 地区 + 电话号        | 非空，unique |
| email          | varchar(128) | 默认学工号 + 学校    | 非空，unique |
| country        | varchar(128) | 国家或地区           | 非空        |
| nation         | varchar(128) | 民族 (中国大陆)      | 非空，限定内容   |
| political_role | varchar(128) | 政治身份 (中国大陆)  | 非空        |
| user_addr      | varchar(128) | 联系地址             | /         |
| birth_addr     | varchar(128) | 籍贯                 | /         |

+ `gender`： 限定`gender = 'male' or gender = 'female'`
+ `passwd`：默认值为`hi` + schoolId，初次登陆后强制修改密码。
+ `school_id`：一共 11 位，开头 1 / 2 / 3 分别代表管理员、教师、学生角色。随后三位表示入学 / 入职年份 (如 2024 对应 024)，最后 7 位为给定编号
+ `birthday`：格式为 yyyy-mm-dd
+ <font color=Red>**删除**：部门信息</font>

**student** 表，在 user 的基础之上，保存部分学生特有的信息：

| 字段名            | 类型           | 描述              | 备注                  |
| ----------------- |--------------| ----------------- | --------------------- |
| school_id         | bigint       | 学工号            | 外键 - user.school_id |
| college           | varchar(256) | 就读学院          | 非空                  |
| class             | varchar(128) | 行政班级          | 非空，限定格式        |
| major             | varchar(128) | 专业              | 非空                  |
| duration          | unsigned int | 学制年数          | 非空                  |
| schooling_type    | varchar(64)  | 学制类型 (全日制) | 非空，限定内容        |
| admission_time    | date         | 入学年月          | 非空，限定格式        |
| expect_graduate   | date         | 预期毕业年月      | 非空，限定格式        |
| current_education | varchar(128) | 在读学历          | 非空，限定内容        |
| gained_education  | varchar(128) | 已获最高学历      | 非空，限定内容        |
| graduated_school  | varchar(128) | 毕业院校          | 非空，限定内容        |
| curr_ranking      | varchar(64)  | 累计排名          | 非空，限定内容        |
| curr_grades       | float        | 累计平均绩点      | 非空                  |

+ `class`：班级格式为 班级名称 + 入学年份 + 编号，例如“计科2101”
+ `admission_time`：格式为 yyyy-mm
+ <font color=Red>**新增**：累计排名</font>，格式为 名次/总人数， 例如“3/100”
+ <font color=Red>**新增**：学院</font>
+ <font color=Red>**新增**：累计平均绩点</font>，格式为百分制

**student_rank** 表，用于记录学生每学期以及每学年的成绩和排名：

| 字段名        | 类型           | 描述         | 备注                  |
| ------------- |--------------| ------------ | --------------------- |
| school_id     | bigint       | 学工号       | 外键 - user.school_id |
| sem_or_year   | tinyint(1)   | 学年还是学期 | 非空，1 表示学年      |
| period        | varchar(128) | 排名阶段     | 非空                  |
| period_rank   | varchar(128) | 阶段排名     | 非空                  |
| period_grades | float        | 阶段平均绩点 | 非空                  |

+ <font color=Red>**整表新增**</font>

**teacher** 表，在 user 的基础之上，保存部分教师特有的信息：

| 字段名         | 类型           | 描述         | 备注                  |
| -------------- |--------------| ------------ | --------------------- |
| school_id      | bigint       | 学工号       | 外键 - user.school_id |
| job_title      | varchar(64)  | 职称         | 非空，限定内容        |
| admission_time | date         | 最早入职年月 | 非空，限定格式        |
| education      | varchar(128) | 学历         | 非空，限定内容        |
| past_education | text         | 教育经历     | /                     |
| past_work      | text         | 工作经历     | /                     |
| research       | text         | 研究方向     | /                     |
| teaching       | text         | 教学简介     | /                     |

+ 教育经历、工作经历、研究方向和教学简介的默认内容为“待补充”

**take_office** 表，用于记录教师就职的部门 (可能有多个) ：

| 字段名            | 类型           | 描述      | 备注                  |
|----------------|--------------|---------|---------------------|
| take_office_id | bigint       | 就职记录 ID | 主键                  |
| school_id      | bigint       | 学工号     | 外键 - user.school_id |
| department     | varchar(128) | 部门      | 非空                  |
| work_position  | varchar(128) | 岗位      | 非空，限定格式             |
| start_time     | date         | 入职年月    | 非空，限定格式             |
| finish_time    | date         | 离职年月    | 非空，默认为“至今”          |

+ <font color=Red>**整表新增**</font>

**take_course_time** 表，用于记录选课时间 ：

| 字段名        | 类型           | 描述     | 备注                  |
| ------------- |--------------| -------- | --------------------- |
| turn     | bigint       | 轮次   | 主键，非空 |
| start_time    | varchar(128) | 开始时间     | 非空                  |
| end_time | varchar(128) | 结束时间     | 非空，限定格式        |
+ 时间形如："yyyy/mm/dd/hh/mm/ss" 例如："2024/5/26/14/38/13"

+ <font color=Red>**整表新增**</font>

**course**表，用于存放课程信息：

| 字段名            | 类型         | 描述         | 备注             |
| ----------------- | ------------ | ------------ | ---------------- |
| course_id         | unsigned int | 课程 id      | 主键，非空，自增 |
| course_name       | varchar(128) | 课程名称     | 非空             |
| credit            | float        | 学分         | 非空             |
| course_department | varchar(128) | 开课院系     | 非空             |
| category          | varchar(128) | 课程类型     | 非空             |
| course_start_time          | varchar(128) | 课程开始时间 | 非空             |
| course_end_time          | varchar(128) | 课程结束时间 | 非空             |
| location          | varchar(128) | 上课地点     | 非空             |
| semester          | varchar(64)  | 开课学期     | 非空             |
| max_students      | int          | 最大选课人数 | 非空             |
| num_students      | int          | 已选课人数   | 非空             |
| test_start_time         | varchar(128) | 考试开始时间     | /                |
| test_end_time         | varchar(128) | 考试结束时间     | /                |
| grade_cal         | Varchar(128) | 成绩计算方式 | 非空             |
| target            | varchar(128) | 面向对象     | 非空             |
| description       | text         | 课程描述     | 可能和AI模块联系 |

* <font color=Red>**新增**：开课对象、课程类型、课程考试开始结束时间</font>
* test_start_time 和 test_end_time形如： "yyyy/mm/dd/hh/mm/ss" 例如："2024/5/26/14/38/13"

**course_teacher** 表，用于存储教师和助教教学信息：

| 字段名            | 类型           | 描述         | 备注                       |
| ----------------- |--------------| ------------ |--------------------------|
| course_teacher_id | unsigned int | 记录 id      | 主键，非空，自增                 |
| school_id         | bigint       | 工号id       | 外键 - user.school_id   |
| course_id         | unsigned int | 课程 id      | 外键 - course.course_id |
| ta_or_tc          | tinyint(1)   | 助教还是老师 | 非空，限定内容                  |

* <font color=Red>**整表新增**</font>
* `ta_or_tc`: 'teacher' 或 ‘ta’

**course_student** 表，用于存储学生的选课信息：

| 字段名            | 类型         | 描述     | 备注                    |
| ----------------- | ------------ | -------- | ----------------------- |
| course_student_id | unsigned int | 记录 id  | 主键，非空，自增        |
| student_id        | bigint | 学生 id  | 外键 - user.school_id   |
| course_id         | unsigned int | 课程 id  | 外键 - course.course_id |
| enroll_date       | date         | 选课日期 | 非空                    |
| status            | varchar(128) | 选课状态 | 非空，限定内容          |
| grade             | decimal(5,2) | 成绩     | /                       |
| ord_grade         | decimal(5,2) | 平时成绩 | /                       |
| exam_grade        | decimal(5,2) | 考试成绩 | /                       |
| is_pass           | tinyint(1)   | 是否及格 | 非空，默认及格          |
| feedback          | text         | 教评反馈 |                         |
| retake            | tinyint(1)   | 是否重修 | 非空                    |
| retake_grade      | decimal(5,2) | 重修成绩 | /                       |

+ `course.status`：有“待定”“已选”“补选”“已退选”几个状态
+ <font color=Red>**新增**: 是否缓考、补考；缓考或补考成绩；是否及格</font>

**course_change** 表，用于存储学生的补退选课信息。

| 字段名          | 类型         | 描述                  | 备注                    |
| --------------- | ------------ | --------------------- | ----------------------- |
| change_id       | unsigned int | 变更记录唯一 id       | 主键，非空，自增        |
| student_id      | bigint | 学生唯一 id           | 外键 - user.school_id   |
| course_id       | unsigned int | 课程唯一 id           | 外键 - course.course_id |
| change_type     | varchar(128) | 变更类型（补选/退选） | 非空                    |
| change_date     | date         | 变更日期和时间        | 非空                    |
| change_reason   | text         | 变更原因              | /                       |
| previous_status | varchar(128) | 变更前状态            | /                       |
| new_status      | varchar(128) | 变更后状态            | /                       |
| approved_by     | unsigned int | 审批人 id             | 外键 - user.school_id   |
| approval_date   | date         | 审批日期和时间        | /                       |
| record_status   | varchar(64)  | 记录状态              | 非空，限定内容          |

+ `course_change.status`：有“待审批”，“已批准”，“已拒绝”等

**textbook** 表，用于存储教材信息：

| 字段名           | 类型         | 描述         | 备注                    |
| ---------------- | ------------ | ------------ | ----------------------- |
| textbook_id      | unsigned int | 教材唯一 id  | 主键，非空，自增        |
| book_title       | varchar(256) | 教材标题     | 非空                    |
| author           | varchar(256) | 作者         | 非空                    |
| edition          | varchar(128) | 版次         | /                       |
| isbn             | varchar(128) | 国际标准书号 | 非空，unique            |
| publisher        | varchar(128) | 出版社       | 非空                    |
| publication_year | year         | 出版年份     | 非空                    |
| course_id        | unsigned int | 相关课程 id  | 外键 - course.course_id |
| price            | unsigned int | 价格         | 非空                    |
| book_description | text         | 教材描述     | /                       |
| book_photo       | varchar(128) | 书的图片     | /                       |
| stock            | int          | 库存数量     | 非空                    |
| booked           | int          | 预定数量     | 非空                    |

+ `price`：单位为分，防止整型产生计算误差

**textbook_order** 表，用于存储学生订购教材的信息：

| 字段名          | 类型         | 描述        | 约束                        |
| --------------- | ------------ | ----------- | --------------------------- |
| order_id        | unsigned int | 订单唯一 id | 主键，非空，自增            |
| student_id      | unsigned int | 订购学生 id | 外键 - course.course_id     |
| textbook_id     | unsigned int | 订购教材 id | 外键 - textbook.textbook_id |
| quantity        | int          | 订购数量    | 非空                        |
| order_date      | date         | 订购日期    | 非空                        |
| status          | varchar(64)  | 订单状态    | 非空                        |
| total_price     | unsigned int | 总价格      | /                           |
| delivery_class  | varchar(256) | 配送班级    | /                           |
| tracking_number | varchar(128) | 物流跟踪号  | /                           |

+ `textbook_order.status`：有“待处理”“已发货”“已收货”几个状态
+ `total_price`：单位为分，使用整型防止浮点误差

**exam** 表，用于存储考试相关信息：

| 字段名          | 类型         | 描述            | 备注                    |
| --------------- | ------------ | --------------- | ----------------------- |
| exam_id         | unsigned int | 考试唯一 id     | 主键，非空，自增        |
| course_id       | unsigned int | 相关课程 id     | 外键 - course.course_id |
| exam_type       | varchar(128) | 考试类型        | 非空                    |
| exam_date       | date         | 考试日期        | 非空                    |
| exam_start_time | time         | 开始时间        | 非空                    |
| exam_end_time   | time         | 结束时间        | 非空                    |
| exam_location   | varchar(128) | 考试地点        | 非空                    |
| duration        | int          | 考试时长 (分钟) | 非空                    |
| examiner_id     | bigint | 监考教师 id     | 外键 - user.school_id   |
| exam_format     | varchar(64)  | 考试形式        | 非空                    |
| total_marks     | int          | 总分            | 非空                    |
| pass_marks      | int          | 及格分数线      | 非空                    |
| additional_info | text         | 额外信息        | /                       |

+ `exam_format`：有闭卷、有限开卷、开卷等
+ `total_marks`:  指卷面总分

**exam_student** 表, 用于收集学生本人考试信息

| 字段名            | 类型         | 描述           | 备注                     |
| ----------------- | ------------ | -------------- | ------------------------ |
| exam_student_id   | unsigned int | 学生考试 id    | 主键，非空，递增         |
| exam_id           | unsigned int | 考试 id        | 外键 - exam.exam_id      |
| student_id        | unsigned int | 学生学工号     | 外键 - student.school_id |
| exam_location     | varchar(128) | 考试地点       | 非空                     |
| exam_sit          | varchar(128) | 考试座位       | 非空                     |
| exam_status       | varchar(128) | 考试状态       | 非空                     |
| exam_grade        | decimal(5,2) | 考试成绩       | /                        |
| defer_or_resit    | tinyint(1)   | 缓考或补考     | 非空                     |
| defer_resit_grade | decimal(5,2) | 缓考或补考成绩 |                          |

**exam_teacher** 表, 用于收集老师监考考试信息

| 字段名          | 类型         | 描述        | 备注                  |
| --------------- | ------------ | ----------- | --------------------- |
| exam_teacher_id | unsigned int | 老师监考 id | 主键，非空，递增      |
| exam_id         | unsigned int | 考试 id     | 外键 - exam.exam_id   |
| school_id       | bigint | 学工号      | 外键 - user.school_id |
| exam_role       | varchar(128) | 监考身份    | 非空                  |

**questionnaire** 表，用于收集课程教学评价：

| 字段名             | 类型           | 描述         | 备注                    |
|-----------------|--------------|------------|-----------------------|
| ques_id         | unsigned int | 问卷标识 id    | 主键，非空，递增              |
| ques_start_date | date         | 问卷开始时间     | 非空                    |
| ques_end_date   | date         | 问卷结束时间     | 非空                    |
| ques_status     | unsigned int | 问卷填写状态     | 非空，0为未填写              |
| ques_aspect1    | unsigned int | 问题1得分      | 非空                    |
| ques_aspect2    | unsigned int | 问题2得分      | 非空                    |
| ques_aspect3    | unsigned int | 问题3得分      | 非空                    |
| ques_aspect4    | unsigned int | 问题4得分      | 非空                    |
| ques_comment    | text         | 问题评论       | /                     |
| ques_grade      | decimal(5,2) | 平均得分       | 非空                    |
| student_id      | unsigned int | 学生学工号      | 外键 - user.school_id   |
| teacher_id      | unsigned int | 教师 / 助教学工号 | 外键 - user.school_id   |
| course_id       | unsigned int | 课程 id      | 外键 - course.course_id |

+ `ques_aspect`： 得分限定为 0~5 分

**forum_user** 表，用于存储论坛用户信息：

| 字段名     | 类型         | 描述            | 备注                  |
| ---------- | ------------ | --------------- | --------------------- |
| forum_id   | unsigned int | 论坛账户标识 id | 主键，非空，递增      |
| forum_name | varchar(128) | 论坛账户名      | 非空，unique          |
| school_id  | bigint | 学工号          | 外键 - user.school_id |
| reg_time   | timestamp    | 论坛注册时间    | 非空，自动生成        |

**forum_post** 表，用于存储论坛评价帖子：

| 字段名        | 类型         | 描述              | 备注                       |
| ------------- | ------------ | ----------------- | -------------------------- |
| post_id       | unsigned int | 帖子标识 id       | 主键，非空，自增           |
| forum_id      | unsigned int | 发帖者 id         | 外键 - forum_user.forum_id |
| post_title    | varchar(128) | 帖子标题          | 非空                       |
| is_modified   | tinyint(1)   | 是否编辑          | 非空， 默认0               |
| post_time     | timestamp    | 发布 / 编辑时间   | 非空，自动生成             |
| teacher_id    | bigint | 教师 / 助教学工号 | 外键 - user.school_id      |
| course_id     | unsigned int | 课程 id           | 外键 - course.course_id    |
| grade         | unsigned int | 打分              | 非空，限定内容             |
| content       | text         | 评价内容          | /                          |
| like_count    | unsigned int | 点赞数            | 非空，触发器生成           |
| dislike_count | unsigned int | 点踩数            | 非空，触发器生成           |
| favor_count   | unsigned int | 收藏数            | 非空，触发器生成           |
| is_top        | tinyint(1)   | 是否置顶          | 非空                       |

+ `forum_post.grade`：限定打分为 2 ~ 10 分

**forum_opt** 表，用于存储用户的点赞点踩等动作：

| 字段名       | 类型         | 描述        | 备注                       |
| ------------ | ------------ | ----------- | -------------------------- |
| forum_opt_id | unsigned int | 操作者 id   | 外键 - forum_user.forum_id |
| post_id      | unsigned int | 操作帖子 id | 外键 - forum_post.post_id  |
| opt_time     | timestamp    | 操作时间    | 非空，自动生成             |
| opt_type     | varchar(64)  | 操作内容    | 非空，限定内容             |

+ `opt_type`：有“点赞”“点踩”“收藏”三种操作。如要取消点赞等通过删除记录实现
