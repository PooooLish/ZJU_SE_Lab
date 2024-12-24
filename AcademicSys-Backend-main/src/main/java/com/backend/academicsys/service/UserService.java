package com.backend.academicsys.service;

import com.backend.academicsys.controller.UserManageController;
import com.backend.academicsys.entity.*;

import java.util.Date;
import java.util.List;

import com.backend.academicsys.controller.UserManageController;
import com.backend.academicsys.entity.StudentExtend;
import com.backend.academicsys.entity.TakeOffice;
import com.backend.academicsys.entity.TeacherExtend;
import com.backend.academicsys.entity.TeacherPublic;
import com.backend.academicsys.entity.User;

public interface UserService {
    User findUser(Long schoolId);

    void updatePassword(Long schoolId, String newPassword);

    User findByEmail(String email);

    User findByPhone(String phone);

    void updateEmail(Long schoolId, String email);

    void updatePhone(Long schoolId, String phone);

    Boolean checkAdmin();

    List<StudentExtend> queryStudent(
            StudentExtend studentExtend,
            Date birthdayFrom, Date birthdayTo,
            Date admissionTimeFrom, Date admissionTimeTo,
            Date graduationTimeFrom, Date graduationTimeTo,
            Integer page, Integer pageSize, Boolean allowBlurredQuery);

    List<UserManageController.TeacherQueryResult> queryTeacher(
            TeacherExtend teacherExtend,
            Date admissionTimeFrom, Date admissionTimeTo, Date birthdayFrom, Date birthdayTo,
            String Department, Integer i, Integer i1, Boolean allowBlurredQuery, Boolean withTakeOffice);

    List<UserManageController.TeacherQueryResultPublic> queryTeacherPublic(TeacherPublic teacherPublic, Date admissionTimeFrom, Date admissionTimeTo,
                                                                           String department, Integer i, Integer i1, Boolean allowBlurredQuery, Boolean withTakeOffice);

    void addStudent(StudentExtend studentExtend);

    void addTeacher(TeacherExtend teacherExtend);

    StudentExtend findStudent(Long schoolId);

    TeacherExtend findTeacher(Long schoolId);

    TakeOffice findTakeOffice(Long schoolId, String department, String workPosition);

    void addTakeOffice(TakeOffice takeOffice);

    void updateTakeOffice(TakeOffice takeOffice);

    void deleteTakeOffice(TakeOffice takeOffice);

    void deleteUser(Long schoolId);

    List<TakeOffice> findTakeOfficeBySchoolId(Long schoolId);

    void updateBirthAddress(Long schoolId, String birthAddr);

    void updateUserAddress(Long schoolId, String userAddr);

    void updatePastEducation(Long schoolId, String pastEducation);

    void updatePastWork(Long schoolId, String pastWork);

    void updateResearch(Long schoolId, String research);

    void updateTeaching(Long schoolId, String teaching);

    void updateStudentAdmin(StudentExtend studentExtend);

    void updateTeacherAdmin(TeacherExtend teacherExtend);

    User findByIdNumber(String idType, String idNumber);

    void addAdmin(User user);

    void updatePasswordOut(Long schoolId, String newPassword);

    User findUserById(String idType, String idNumber);

    void updateAdmin(User user);

    void updatePhoto(Long schoolId, String photoUrl);

    List<User> queryAdmin(User user, Integer i, Integer pageSize, Date birthdayFrom, Date birthdayTo, Boolean allowBlurredQuery);
}
