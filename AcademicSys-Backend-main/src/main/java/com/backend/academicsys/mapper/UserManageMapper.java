package com.backend.academicsys.mapper;

import com.backend.academicsys.controller.UserManageController;
import com.backend.academicsys.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserManageMapper {

    @Select("select * from user where school_id = #{schoolId}")
    User findUser(Long schoolId);

    @Update("update user set password = #{newPassword} where school_id = #{schoolId}")
    void updatePassword(Long schoolId, String newPassword);

    @Select("select * from user where email = #{email}")
    User findByEmail(String email);

    @Select("select * from user where phone = #{phone}")
    User findByPhone(String phone);


    @SelectProvider(type = UserManageMapperProvider.class, method = "queryStudent")
    List<StudentExtend> queryStudent(StudentExtend studentExtend,
                                     Date birthdayFrom, Date birthdayTo,
                                     Date admissionTimeFrom, Date admissionTimeTo,
                                     Date graduationTimeFrom, Date graduationTimeTo,
                                     Integer page, Integer pageSize, Boolean allowBlurredQuery);

    @SelectProvider(type = UserManageMapperProvider.class, method = "queryTeacher")
    List<UserManageController.TeacherQueryResult> queryTeacher(TeacherExtend teacherExtend,
                                                               Date admissionTimeFrom, Date admissionTimeTo,
                                                               Date birthdayFrom, Date birthdayTo, String department,
                                                               Integer i, Integer i1, Boolean allowBlurredQuery);

    @Select("select * from take_office where school_id = #{schoolId}")
    List<TakeOffice> findTakeOffice(Long schoolId);

    @SelectProvider(type = UserManageMapperProvider.class, method = "queryTeacherPublic")
    List<UserManageController.TeacherQueryResultPublic> queryTeacherPublic(TeacherPublic teacherPublic,
                                                                           Date admissionTimeFrom, Date admissionTimeTo,
                                                                           String department,
                                                                           Integer i, Integer i1, Boolean allowBlurredQuery);

    @Insert("insert into user(password, user_name, gender, " +
            "school_id, id_type, id_number, birthday, " +
            "phone, email, country, nation, political_role, " +
            "user_addr, birth_addr, user_photo) " +
            "values(#{password}, #{userName}, " +
            "#{gender}, #{schoolId}, " +
            "#{idType}, #{idNumber}, " +
            "#{birthday}, #{phone}, " +
            "#{email}, #{country}, " +
            "#{nation}, #{politicalRole}, " +
            "#{userAddr}, #{birthAddr}, " +
            "#{userPhoto})")
    void addStudentUser(StudentExtend studentExtend);

    @Insert("insert into student(school_id, admission_time, duration, " +
            "major, college, class_name, schooling_type, current_education, " +
            "gained_education, current_grades, current_ranking, " +
            "expected_graduation, graduated_school) " +
            "values(#{schoolId}, #{admissionTime}, " +
            "#{duration}, #{major}, " +
            "#{college}, #{className}, " +
            "#{schoolingType}, #{currentEducation}, " +
            "#{gainedEducation}, #{currentGrades}, " +
            "#{currentRanking}, #{expectedGraduation}," +
            "#{graduatedSchool})")
    void addStudent(StudentExtend studentExtend);

    @Insert("insert into user(password, user_name, gender, " +
            "school_id, id_type, id_number, birthday, " +
            "phone, email, country, nation, political_role, " +
            "user_addr, birth_addr, user_photo) " +
            "values(#{password}, #{userName}, " +
            "#{gender}, #{schoolId}, " +
            "#{idType}, #{idNumber}, " +
            "#{birthday}, #{phone}, " +
            "#{email}, #{country}, " +
            "#{nation}, #{politicalRole}, " +
            "#{userAddr}, #{birthAddr}, " +
            "#{userPhoto})")
    void addTeacherUser(TeacherExtend teacherExtend);

    @Insert("insert into teacher(school_id, job_title, admission_time, " +
            "education, past_education, past_work, research, teaching) " +
            "values(#{schoolId}, #{jobTitle}, " +
            "#{admissionTime}, #{education}, " +
            "#{pastEducation}, #{pastWork}, " +
            "#{research}, #{teaching})")
    void addTeacher(TeacherExtend teacherExtend);

    @Select("select * from student, user where student.school_id = user.school_id and student.school_id = #{schoolId}")
    StudentExtend findStudent(Long schoolId);

    @Select("select * from teacher, user where teacher.school_id = user.school_id and teacher.school_id = #{schoolId}")
    TeacherExtend findTeacher(Long schoolId);

    @Select("select * from take_office where school_id = #{schoolId} and department = #{department} and work_position = #{workPosition}")
    TakeOffice findTakeOfficeRecord(Long schoolId, String department, String workPosition);

    @InsertProvider(type = UserManageMapperProvider.class, method = "addTakeOffice")
    void addTakeOffice(TakeOffice takeOffice);

    @InsertProvider(type = UserManageMapperProvider.class, method = "updateTakeOffice")
    void updateTakeOffice(TakeOffice takeOffice);

    @Delete("delete from take_office where school_id = #{schoolId} and department = #{department} and work_position = #{workPosition}")
    void deleteTakeOffice(TakeOffice takeOffice);

    @Delete("delete from user where school_id = #{schoolId}")
    void deleteUser(Long schoolId);

    @Delete("delete from student where school_id = #{schoolId}")
    void deleteCourseChangeAdmin(Long schoolId);

    @Delete("delete from take_office where school_id = #{schoolId}")
    void deleteTakeOfficeBySchoolId(Long schoolId);

    @Delete("delete from exam_teacher where school_id = #{schoolId}")
    void deleteExamTeacher(Long schoolId);

    @Delete("delete from course_teacher where school_id = #{schoolId}")
    void deleteCourseTeacher(Long schoolId);

    @Delete("delete from questionnaire where teacher_id = #{schoolId}")
    void deleteQuestionnaireTeacher(Long schoolId);

    @Select("select forum_id from forum_user where school_id = #{schoolId}")
    Integer findForumId(Long schoolId);

    @Delete("delete from forum_post where teacher_id = #{schoolId}")
    void deleteForumTeacher(Long schoolId);

    @Delete("delete from forum_user where school_id = #{schoolId}")
    void deleteForumUser(Long schoolId);

    @Delete("delete from forum_opt where post_id in (select post_id from forum_post where teacher_id = #{schoolId})")
    void deleteForumOptionByPostIdTeacher(Long schoolId);

    @Delete("delete from forum_post where teacher_id = #{schoolId}")
    void deleteForumPostTeacher(Long schoolId);

    @Delete("delete from teacher where school_id = #{schoolId}")
    void deleteTeacherUser(Long schoolId);

    @Delete("delete from forum_opt where forum_id = #{forumId}")
    void deleteForumOption(Integer forumId);

    @Delete("delete from forum_opt where post_id in (select post_id from forum_post where forum_id = #{forumId})")
    void deleteForumOptionByPostId(Integer forumId);

    @Delete("delete from forum_post where forum_id = #{forumId}")
    void deleteForumPost(Integer forumId);

    @Delete("delete from forum_user where forum_id = #{forumId}")
    void deleteExamStudent(Long schoolId);

    @Delete("delete from course_student where student_id = #{schoolId}")
    void deleteCourseStudent(Long schoolId);

    @Delete("delete from questionnaire where student_id = #{schoolId}")
    void deleteQuestionnaireStudent(Long schoolId);

    @Delete("delete from student where school_id = #{schoolId}")
    void deleteStudentUser(Long schoolId);

    @Delete("delete from course_student where student_id = #{schoolId}")
    void deleteCourseChangeStudent(Long schoolId);

    @Delete("delete from student_rank where school_id = #{schoolId}")
    void deleteStudentRank(Long schoolId);

    @Delete("delete from textbook_order where student_id = #{schoolId}")
    void deleteTextBookOrder(Long schoolId);


    @Update("update user set email = #{email} where school_id = #{schoolId}")
    void updateEmail(Long schoolId, String email);

    @Update("update user set phone = #{phone} where school_id = #{schoolId}")
    void updatePhone(Long schoolId, String phone);

    @Update("update user set birth_addr = #{birthAddr} where school_id = #{schoolId}")
    void updateBirthAddress(Long schoolId, String birthAddr);

    @Update("update user set user_addr = #{userAddr} where school_id = #{schoolId}")
    void updateUserAddress(Long schoolId, String userAddr);

    @Update("update user set user_name = #{userName} where school_id = #{schoolId}")
    void updateUsername(Long schoolId, String userName);

    @Update("update user set gender = #{gender} where school_id = #{schoolId}")
    void updateGender(Long schoolId, String gender);

    @Update("update user set id_type = #{idType} where school_id = #{schoolId}")
    void updateIdType(Long schoolId, String idType);

    @Update("update user set id_number = #{idNumber} where school_id = #{schoolId}")
    void updateIdNumber(Long schoolId, String idNumber);

    @Update("update user set birthday = #{birthday} where school_id = #{schoolId}")
    void updateBirthday(Long schoolId, Date birthday);

    @Update("update user set country = #{country} where school_id = #{schoolId}")
    void updateCountry(Long schoolId, String country);

    @Update("update user set nation = #{nation} where school_id = #{schoolId}")
    void updateNation(Long schoolId, String nation);

    @Update("update user set political_role = #{politicalRole} where school_id = #{schoolId}")
    void updatePoliticalRole(Long schoolId, String politicalRole);

    @Update("update user set user_photo = #{userPhoto} where school_id = #{schoolId}")
    void updateUserPhoto(Long schoolId, String userPhoto);


    @Update("update teacher set past_education = #{pastEducation} where school_id = #{schoolId}")
    void updatePastEducation(Long schoolId, String pastEducation);

    @Update("update teacher set past_work = #{pastWork} where school_id = #{schoolId}")
    void updatePastWork(Long schoolId, String pastWork);

    @Update("update teacher set research = #{research} where school_id = #{schoolId}")
    void updateResearch(Long schoolId, String research);

    @Update("update teacher set teaching = #{teaching} where school_id = #{schoolId}")
    void updateTeaching(Long schoolId, String teaching);

    @Update("update teacher set job_title = #{jobTitle} where school_id = #{schoolId}")
    void updateJobTitle(Long schoolId, String jobTitle);

    @Update("update teacher set admission_time = #{admissionTime} where school_id = #{schoolId}")
    void updateTeaAdmissionTime(Long schoolId, Date admissionTime);

    @Update("update teacher set education = #{education} where school_id = #{schoolId}")
    void updateEducation(Long schoolId, String education);

    @Update("update student set college = #{college} where school_id = #{schoolId}")
    void updateCollege(Long schoolId, String college);

    @Update("update student set major = #{major} where school_id = #{schoolId}")
    void updateMajor(Long schoolId, String major);

    @Update("update student set class_name = #{className} where school_id = #{schoolId}")
    void updateClassName(Long schoolId, String className);

    @Update("update student set duration = #{duration} where school_id = #{schoolId}")
    void updateDuration(Long schoolId, Integer duration);

    @Update("update student set schooling_type = #{schoolingType} where school_id = #{schoolId}")
    void updateSchoolingType(Long schoolId, String schoolingType);

    @Update("update student set admission_time = #{admissionTime} where school_id = #{schoolId}")
    void updateSTUAdmissionTime(Long schoolId, Date admissionTime);

    @Update("update student set expected_graduation = #{expectedGraduation} where school_id = #{schoolId}")
    void updateExpectedGraduation(Long schoolId, Date expectedGraduation);

    @Update("update student set current_education = #{currentEducation} where school_id = #{schoolId}")
    void updateCurrentEducation(Long schoolId, String currentEducation);

    @Update("update student set gained_education = #{gainedEducation} where school_id = #{schoolId}")
    void updateGainedEducation(Long schoolId, String gainedEducation);

    @Update("update student set graduated_school = #{graduatedSchool} where school_id = #{schoolId}")
    void updateGraduatedSchool(Long schoolId, String graduatedSchool);

    @Update("update student set current_ranking = #{currentRanking} where school_id = #{schoolId}")
    void updateCurrentRanking(Long schoolId, String currentRanking);

    @Update("update student set current_grades = #{currentGrades} where school_id = #{schoolId}")
    void updateCurrentGrades(Long schoolId, Float currentGrades);

    @Select("select * from user where id_type = #{idType} and id_number = #{idNumber}")
    User findByIdNumber(String idType, String idNumber);

    @Insert("insert into user(password, user_name, gender, " +
            "school_id, id_type, id_number, birthday, " +
            "phone, email, country, nation, political_role, " +
            "user_addr, birth_addr, user_photo) " +
            "values(#{password}, #{userName}, " +
            "#{gender}, #{schoolId}, " +
            "#{idType}, #{idNumber}, " +
            "#{birthday}, #{phone}, " +
            "#{email}, #{country}, " +
            "#{nation}, #{politicalRole}, " +
            "#{userAddr}, #{birthAddr}, " +
            "#{userPhoto})")
    void addAdminUser(User user);

    @Select("select * from user where id_type = #{idType} and id_number = #{idNumber}")
    User findUserById(String idType, String idNumber);

    @SelectProvider(type = UserManageMapperProvider.class, method = "queryAdmin")
    List<User> queryAdmin(User user, Integer i, Integer pageSize, Date birthdayFrom, Date birthdayTo, Boolean allowBlurredQuery);


    public class UserManageMapperProvider {
        public String queryStudent(StudentExtend studentExtend,
                                   Date birthdayFrom, Date birthdayTo,
                                   Date admissionTimeFrom, Date admissionTimeTo,
                                   Date graduationTimeFrom, Date graduationTimeTo,
                                   Integer page, Integer pageSize, Boolean allowBlurredQuery) {
            StringBuilder sql = new StringBuilder(
                    "select * from student, user where student.school_id = user.school_id"
            );
            if (studentExtend.getSchoolId() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.school_id = #{studentExtend.schoolId}");
                } else {
                    sql.append(" and user.school_id like concat('%', #{studentExtend.schoolId}, '%')");
                }
            }
            if (studentExtend.getUserName() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.user_name = #{studentExtend.userName}");
                } else {
                    sql.append(" and user.user_name like concat('%', #{studentExtend.userName}, '%')");
                }
            }
            if (studentExtend.getGender() != null) {
                sql.append(" and user.gender = #{studentExtend.gender}");
            }
            if (studentExtend.getIdNumber() != null && studentExtend.getIdType() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.id_number = #{studentExtend.idNumber} and user.id_type = #{studentExtend.idType}");
                } else {
                    sql.append(" and user.id_number like concat('%', #{studentExtend.idNumber}, '%') and user.id_type = #{studentExtend.idType}");
                }
            }
            if (studentExtend.getPhone() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.phone = #{studentExtend.phone}");
                } else {
                    sql.append(" and user.phone like concat('%', #{studentExtend.phone}, '%')");
                }
            }
            if (studentExtend.getEmail() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.email = #{studentExtend.email}");
                } else {
                    sql.append(" and user.email like concat('%', #{studentExtend.email}, '%')");
                }
            }
            if (studentExtend.getCountry() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.country = #{studentExtend.country}");
                } else {
                    sql.append(" and user.country like concat('%', #{studentExtend.country}, '%')");
                }
            }
            if (studentExtend.getNation() != null) {
                sql.append(" and user.nation = #{studentExtend.nation}");
            }
            if (studentExtend.getPoliticalRole() != null) {
                sql.append(" and user.political_role = #{studentExtend.politicalRole}");
            }
            if (studentExtend.getAdmissionTime() != null) {
                sql.append(" and student.admission_time = #{studentExtend.admissionTime}");
            }
            if (studentExtend.getDuration() != null) {
                sql.append(" and student.duration = #{studentExtend.duration}");
            }
            if (studentExtend.getMajor() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and student.major = #{studentExtend.major}");
                } else {
                    sql.append(" and student.major like concat('%', #{studentExtend.major}, '%')");
                }
            }
            if (studentExtend.getCollege() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and student.college = #{studentExtend.college}");
                } else {
                    sql.append(" and student.college like concat('%', #{studentExtend.college}, '%')");
                }
            }
            if (studentExtend.getClassName() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and student.class_name = #{studentExtend.className}");
                } else {
                    sql.append(" and student.class_name like concat('%', #{studentExtend.className}, '%')");
                }
            }
            if (studentExtend.getSchoolingType() != null) {
                sql.append(" and student.schooling_type = #{studentExtend.schoolingType}");
            }
            if (studentExtend.getCurrentEducation() != null) {
                sql.append(" and student.current_education = #{studentExtend.currentEducation}");
            }
            if (studentExtend.getGainedEducation() != null) {
                sql.append(" and student.gained_education = #{studentExtend.gainedEducation}");
            }
            if (admissionTimeFrom != null) {
                sql.append(" and student.admission_time >= #{admissionTimeFrom}");
            }
            if (admissionTimeTo != null) {
                sql.append(" and student.admission_time <= #{admissionTimeTo}");
            }
            if (graduationTimeFrom != null) {
                sql.append(" and student.expected_graduation >= #{graduationTimeFrom}");
            }
            if (graduationTimeTo != null) {
                sql.append(" and student.expected_graduation <= #{graduationTimeTo}");
            }
            if (birthdayFrom != null) {
                sql.append(" and user.birthday >= #{birthdayFrom}");
            }
            if (birthdayTo != null) {
                sql.append(" and user.birthday <= #{birthdayTo}");
            }
            sql.append(" order by user.school_id");
            if (page != null && pageSize != null) {
                sql.append(" limit #{page}, #{pageSize}");
            }
            return sql.toString();
        }

        public String queryTeacher(TeacherExtend teacherExtend,
                                   Date admissionTimeFrom, Date admissionTimeTo,
                                   Date birthdayFrom, Date birthdayTo, String department,
                                   Integer i, Integer i1, Boolean allowBlurredQuery) {
            StringBuilder sql = new StringBuilder(
                    "select user_id, user_name, gender, teacher.school_id, id_number, id_type," +
                            " phone, email, country, birthday, nation, political_role, user_photo, birth_addr, user_addr, job_title," +
                            " admission_time, education, past_education, past_work, research, teaching"
            );
            if (department != null) {
                sql.append(
                        " from teacher, user, take_office" +
                                " where teacher.school_id = user.school_id and teacher.school_id = take_office.school_id"
                );
            } else {
                sql.append(
                        " from teacher, user" +
                                " where teacher.school_id = user.school_id"
                );
            }
            if (teacherExtend.getSchoolId() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.school_id = #{teacherExtend.schoolId}");
                } else {
                    sql.append(" and user.school_id like concat('%', #{teacherExtend.schoolId}, '%')");
                }
            }
            if (teacherExtend.getUserName() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.user_name = #{teacherExtend.userName}");
                } else {
                    sql.append(" and user.user_name like concat('%', #{teacherExtend.userName}, '%')");
                }
            }
            if (teacherExtend.getGender() != null) {
                sql.append(" and user.gender = #{teacherExtend.gender}");
            }
            if (teacherExtend.getIdNumber() != null && teacherExtend.getIdType() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.id_number = #{teacherExtend.idNumber} and user.id_type = #{teacherExtend.idType}");
                } else {
                    sql.append(" and user.id_number like concat('%', #{teacherExtend.idNumber}, '%') and user.id_type = #{teacherExtend.idType}");
                }
            }
            if (teacherExtend.getPhone() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.phone = #{teacherExtend.phone}");
                } else {
                    sql.append(" and user.phone like concat('%', #{teacherExtend.phone}, '%')");
                }
            }
            if (teacherExtend.getEmail() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.email = #{teacherExtend.email}");
                } else {
                    sql.append(" and user.email like concat('%', #{teacherExtend.email}, '%')");
                }
            }
            if (teacherExtend.getCountry() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.country = #{teacherExtend.country}");
                } else {
                    sql.append(" and user.country like concat('%', #{teacherExtend.country}, '%')");
                }
            }
            if (teacherExtend.getNation() != null) {
                sql.append(" and user.nation = #{teacherExtend.nation}");
            }
            if (teacherExtend.getPoliticalRole() != null) {
                sql.append(" and user.political_role = #{teacherExtend.politicalRole}");
            }
            if (teacherExtend.getJobTitle() != null) {
                sql.append(" and teacher.job_title = #{teacherExtend.jobTitle}");
            }
            if (teacherExtend.getEducation() != null) {
                sql.append(" and teacher.education = #{teacherExtend.education}");
            }
            if (admissionTimeFrom != null) {
                sql.append(" and teacher.admission_time >= #{admissionTimeFrom}");
            }
            if (admissionTimeTo != null) {
                sql.append(" and teacher.admission_time <= #{admissionTimeTo}");
            }
            if (birthdayFrom != null) {
                sql.append(" and user.birthday >= #{birthdayFrom}");
            }
            if (birthdayTo != null) {
                sql.append(" and user.birthday <= #{birthdayTo}");
            }
            if (department != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and take_office.department = #{department}");
                } else {
                    sql.append(" and take_office.department like concat('%', #{department}, '%')");
                }
            }
            sql.append(" group by teacher.school_id");
            sql.append(" order by teacher.school_id");
            if (i != null && i1 != null) {
                sql.append(" limit #{i}, #{i1}");
            }
            return sql.toString();
        }

        public String queryTeacherPublic(TeacherPublic teacherPublic,
                                         Date admissionTimeFrom,
                                         Date admissionTimeTo,
                                         String department,
                                         Integer i, Integer i1, Boolean allowBlurredQuery) {
            StringBuilder sql = new StringBuilder(
                    "select user_id, user_name, gender, teacher.school_id," +
                            " phone, email, country, nation, political_role, user_photo, job_title," +
                            " admission_time, education, past_education, past_work, research, teaching"
            );
            if (department != null) {
                sql.append(
                        " from teacher, user, take_office" +
                                " where teacher.school_id = user.school_id and teacher.school_id = take_office.school_id"
                );
            } else {
                sql.append(
                        " from teacher, user" +
                                " where teacher.school_id = user.school_id"
                );
            }
            if (teacherPublic.getSchoolId() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.school_id = #{teacherPublic.schoolId}");
                } else {
                    sql.append(" and user.school_id like concat('%', #{teacherPublic.schoolId}, '%')");
                }
            }
            if (teacherPublic.getUserName() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.user_name = #{teacherPublic.userName}");
                } else {
                    sql.append(" and user.user_name like concat('%', #{teacherPublic.userName}, '%')");
                }
            }
            if (teacherPublic.getGender() != null) {
                sql.append(" and user.gender = #{teacherPublic.gender}");
            }
            if (teacherPublic.getPhone() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.phone = #{teacherPublic.phone}");
                } else {
                    sql.append(" and user.phone like concat('%', #{teacherPublic.phone}, '%')");
                }
            }
            if (teacherPublic.getEmail() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.email = #{teacherPublic.email}");
                } else {
                    sql.append(" and user.email like concat('%', #{teacherPublic.email}, '%')");
                }
            }
            if (teacherPublic.getCountry() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user.country = #{teacherPublic.country}");
                } else {
                    sql.append(" and user.country like concat('%', #{teacherPublic.country}, '%')");
                }
            }
            if (teacherPublic.getNation() != null) {
                sql.append(" and user.nation = #{teacherPublic.nation}");
            }
            if (teacherPublic.getPoliticalRole() != null) {
                sql.append(" and user.political_role = #{teacherPublic.politicalRole}");
            }
            if (teacherPublic.getJobTitle() != null) {
                sql.append(" and teacher.job_title = #{teacherPublic.jobTitle}");
            }
            if (teacherPublic.getEducation() != null) {
                sql.append(" and teacher.education = #{teacherPublic.education}");
            }
            if (admissionTimeFrom != null) {
                sql.append(" and teacher.admission_time >= #{admissionTimeFrom}");
            }
            if (admissionTimeTo != null) {
                sql.append(" and teacher.admission_time <= #{admissionTimeTo}");
            }
            if (department != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and take_office.department = #{department}");
                } else {
                    sql.append(" and take_office.department like concat('%', #{department}, '%')");
                }
            }
            sql.append(" group by teacher.school_id");
            sql.append(" order by teacher.school_id");
            if (i != null && i1 != null) {
                sql.append(" limit #{i}, #{i1}");
            }
            return sql.toString();
        }

        public String queryAdmin(User user, Integer i, Integer pageSize, Date birthdayFrom, Date birthdayTo, Boolean allowBlurredQuery) {
            StringBuilder sql = new StringBuilder(
                    "select * from user where school_id like '1%'"
            );
            if (user.getSchoolId() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and school_id = #{user.schoolId}");
                } else {
                    sql.append(" and school_id like concat('%', #{user.schoolId}, '%')");
                }
            }
            if (user.getUserName() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and user_name = #{user.userName}");
                } else {
                    sql.append(" and user_name like concat('%', #{user.userName}, '%')");
                }
            }
            if (user.getGender() != null) {
                sql.append(" and gender = #{user.gender}");
            }
            if (user.getIdNumber() != null && user.getIdType() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and id_number = #{user.idNumber} and id_type = #{user.idType}");
                } else {
                    sql.append(" and id_number like concat('%', #{user.idNumber}, '%') and id_type = #{user.idType}");
                }
            }
            if (user.getPhone() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and phone = #{user.phone}");
                } else {
                    sql.append(" and phone like concat('%', #{user.phone}, '%')");
                }
            }
            if (user.getEmail() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and email = #{user.email}");
                } else {
                    sql.append(" and email like concat('%', #{user.email}, '%')");
                }
            }
            if (user.getCountry() != null) {
                if (!allowBlurredQuery) {
                    sql.append(" and country = #{user.country}");
                } else {
                    sql.append(" and country like concat('%', #{user.country}, '%')");
                }
            }
            if (user.getNation() != null) {
                sql.append(" and nation = #{user.nation}");
            }
            if (user.getPoliticalRole() != null) {
                sql.append(" and political_role = #{user.politicalRole}");
            }
            if (birthdayFrom != null) {
                sql.append(" and birthday >= #{birthdayFrom}");
            }
            if (birthdayTo != null) {
                sql.append(" and birthday <= #{birthdayTo}");
            }
            sql.append(" order by school_id");
            if (i != null && pageSize != null) {
                sql.append(" limit #{i}, #{pageSize}");
            }
            return sql.toString();
        }


        public String addTakeOffice(TakeOffice takeOffice) {
            StringBuilder sql = new StringBuilder();
            if (takeOffice.getEndTime() != null) {
                sql.append(
                        "insert into take_office(school_id, department, work_position, " +
                                "start_time, end_time) " +
                                "values(#{schoolId}, #{department}, " +
                                "#{workPosition}, #{startTime}, " +
                                "#{endTime})"
                );
            } else {
                sql.append(
                        "insert into take_office(school_id, department, work_position, " +
                                "start_time) " +
                                "values(#{schoolId}, #{department}, " +
                                "#{workPosition}, #{startTime})"
                );
            }
            return sql.toString();
        }

        public String updateTakeOffice(TakeOffice takeOffice) {
            return "update take_office set " +
                    "end_time = #{endTime} " +
                    "where school_id = #{schoolId} and " +
                    "department = #{department} and " +
                    "work_position = #{workPosition}";
        }


    }
}
