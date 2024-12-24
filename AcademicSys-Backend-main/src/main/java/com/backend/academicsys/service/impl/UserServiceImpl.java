package com.backend.academicsys.service.impl;

import com.backend.academicsys.controller.UserManageController;
import com.backend.academicsys.entity.*;
import com.backend.academicsys.service.UserService;
import com.backend.academicsys.utils.Md5Util;
import com.backend.academicsys.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.academicsys.mapper.UserManageMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public User findUser(Long schoolId) {
        return userManageMapper.findUser(schoolId);
    }

    @Override
    public void updatePassword(Long schoolId, String newPassword) {
        userManageMapper.updatePassword(schoolId, Md5Util.getMD5String(newPassword));
    }

    @Override
    public User findByEmail(String email) {
        return userManageMapper.findByEmail(email);
    }

    @Override
    public User findByPhone(String phone) {
        return userManageMapper.findByPhone(phone);
    }

    @Override
    public void updateEmail(Long schoolId, String email) {
        userManageMapper.updateEmail(schoolId, email);
    }

    @Override
    public void updatePhone(Long schoolId, String phone) {
        userManageMapper.updatePhone(schoolId, phone);
    }

    public Boolean checkAdmin() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        int type = (int) (schoolId / 1e10);
        return type == 1;
    }

    @Override
    public List<StudentExtend> queryStudent(
            StudentExtend studentExtend,
            Date birthdayFrom, Date birthdayTo,
            Date admissionTimeFrom, Date admissionTimeTo,
            Date graduationTimeFrom, Date graduationTimeTo,
            Integer page, Integer pageSize, Boolean allowBlurredQuery) {
        return userManageMapper.queryStudent(
                studentExtend,
                birthdayFrom, birthdayTo,
                admissionTimeFrom, admissionTimeTo,
                graduationTimeFrom, graduationTimeTo,
                page, pageSize, allowBlurredQuery);
    }

    @Override
    public List<UserManageController.TeacherQueryResult> queryTeacher(
            TeacherExtend teacherExtend,
            Date admissionTimeFrom, Date admissionTimeTo, Date birthdayFrom, Date birthdayTo,
            String Department, Integer i, Integer i1, Boolean allowBlurredQuery, Boolean withTakeOffice) {
        List<UserManageController.TeacherQueryResult> teacherExtends = userManageMapper.queryTeacher(
                teacherExtend, admissionTimeFrom, admissionTimeTo,
                birthdayFrom, birthdayTo, Department, i, i1, allowBlurredQuery);
        if (withTakeOffice) {
            for (UserManageController.TeacherQueryResult teacher : teacherExtends) {
                List<TakeOffice> takeOffices = userManageMapper.findTakeOffice(teacher.getSchoolId());
                teacher.setTakeOfficeList(takeOffices);
            }
        }
        return teacherExtends;
    }

    @Override
    public List<UserManageController.TeacherQueryResultPublic> queryTeacherPublic(
            TeacherPublic teacherPublic,
            Date admissionTimeFrom, Date admissionTimeTo,
            String department,
            Integer i, Integer i1, Boolean allowBlurredQuery, Boolean withTakeOffice) {
        List<UserManageController.TeacherQueryResultPublic> teacherPubliclist = userManageMapper.queryTeacherPublic(
                teacherPublic, admissionTimeFrom, admissionTimeTo,
                department, i, i1, allowBlurredQuery);
        if (withTakeOffice) {
            for (UserManageController.TeacherQueryResultPublic teacher : teacherPubliclist) {
                List<TakeOffice> takeOffices = userManageMapper.findTakeOffice(teacher.getSchoolId());
                teacher.setTakeOfficeList(takeOffices);
            }
        }
        return teacherPubliclist;
    }

    @Override
    public void addStudent(StudentExtend studentExtend) {
        if (studentExtend.getPassword() == null) {
            String defaultPassword = "hi" + studentExtend.getSchoolId().toString();
            defaultPassword = Md5Util.getMD5String(defaultPassword);
            studentExtend.setPassword(defaultPassword);
        }
        if (studentExtend.getEmail() == null) {
            String email = studentExtend.getSchoolId().toString() + "@example.com";
            studentExtend.setEmail(email);
        }
        if (studentExtend.getUserPhoto() == null) {
            studentExtend.setUserPhoto("https://academic-sys.oss-cn-hangzhou.aliyuncs.com/userPhoto/default.png");
        }
        if (studentExtend.getCurrentGrades() == null) {
            studentExtend.setCurrentGrades(100.0F);
        }
        if (studentExtend.getCurrentRanking() == null) {
            studentExtend.setCurrentRanking("1/100");
        }
        if (studentExtend.getUserAddr() == null) {
            studentExtend.setUserAddr("待补充");
        }
        if (studentExtend.getBirthAddr() == null) {
            studentExtend.setBirthAddr("待补充");
        }
        userManageMapper.addStudentUser(studentExtend);
        userManageMapper.addStudent(studentExtend);
    }

    @Override
    public void addTeacher(TeacherExtend teacherExtend) {
        if (teacherExtend.getPassword() == null) {
            String defaultPassword = "hi" + teacherExtend.getSchoolId().toString();
            defaultPassword = Md5Util.getMD5String(defaultPassword);
            teacherExtend.setPassword(defaultPassword);
        }
        if (teacherExtend.getEmail() == null) {
            String email = teacherExtend.getSchoolId().toString() + "@example.com";
            teacherExtend.setEmail(email);
        }
        if (teacherExtend.getUserPhoto() == null) {
            teacherExtend.setUserPhoto("https://academic-sys.oss-cn-hangzhou.aliyuncs.com/userPhoto/default.png");
        }
        if (teacherExtend.getUserAddr() == null) {
            teacherExtend.setUserAddr("待补充");
        }
        if (teacherExtend.getBirthAddr() == null) {
            teacherExtend.setBirthAddr("待补充");
        }
        if (teacherExtend.getPastEducation() == null) {
            teacherExtend.setPastEducation("待补充");
        }
        if (teacherExtend.getPastWork() == null) {
            teacherExtend.setPastWork("待补充");
        }
        if (teacherExtend.getResearch() == null) {
            teacherExtend.setResearch("待补充");
        }
        if (teacherExtend.getTeaching() == null) {
            teacherExtend.setTeaching("待补充");
        }
        userManageMapper.addTeacherUser(teacherExtend);
        userManageMapper.addTeacher(teacherExtend);
    }

    @Override
    public StudentExtend findStudent(Long schoolId) {
        return userManageMapper.findStudent(schoolId);
    }

    @Override
    public TeacherExtend findTeacher(Long schoolId) {
        return userManageMapper.findTeacher(schoolId);
    }

    @Override
    public TakeOffice findTakeOffice(Long schoolId, String department, String workPosition) {
        return userManageMapper.findTakeOfficeRecord(schoolId, department, workPosition);
    }

    @Override
    public void addTakeOffice(TakeOffice takeOffice) {
        userManageMapper.addTakeOffice(takeOffice);
    }

    @Override
    public void updateTakeOffice(TakeOffice takeOffice) {
        userManageMapper.updateTakeOffice(takeOffice);
    }

    @Override
    public void deleteTakeOffice(TakeOffice takeOffice) {
        userManageMapper.deleteTakeOffice(takeOffice);
    }

    @Override
    public void deleteUser(Long schoolId) {
        int type = (int) (schoolId / 1e10);
        Integer forumId = userManageMapper.findForumId(schoolId);
        if (forumId != null) {
            userManageMapper.deleteForumOption(forumId);
            userManageMapper.deleteForumOptionByPostId(forumId);
            userManageMapper.deleteForumPost(forumId);
            userManageMapper.deleteForumUser(schoolId);
        }
        if (type == 1) {
            userManageMapper.deleteCourseChangeAdmin(schoolId);
            userManageMapper.deleteUser(schoolId);
        } else if (type == 2) {
            userManageMapper.deleteTakeOfficeBySchoolId(schoolId);
            userManageMapper.deleteExamTeacher(schoolId);
            userManageMapper.deleteCourseTeacher(schoolId);
            userManageMapper.deleteQuestionnaireTeacher(schoolId);
            userManageMapper.deleteForumOptionByPostIdTeacher(schoolId);
            userManageMapper.deleteForumPostTeacher(schoolId);
            userManageMapper.deleteTeacherUser(schoolId);
            userManageMapper.deleteUser(schoolId);
        } else if (type == 3) {
            userManageMapper.deleteExamStudent(schoolId);
            userManageMapper.deleteCourseChangeStudent(schoolId);
            userManageMapper.deleteCourseTeacher(schoolId);
            userManageMapper.deleteCourseStudent(schoolId);
            userManageMapper.deleteQuestionnaireStudent(schoolId);
            userManageMapper.deleteTextBookOrder(schoolId);
            userManageMapper.deleteStudentRank(schoolId);
            userManageMapper.deleteStudentUser(schoolId);
            userManageMapper.deleteUser(schoolId);
        }
    }

    @Override
    public List<TakeOffice> findTakeOfficeBySchoolId(Long schoolId) {
        return userManageMapper.findTakeOffice(schoolId);
    }

    @Override
    public void updateBirthAddress(Long schoolId, String birthAddr) {
        userManageMapper.updateBirthAddress(schoolId, birthAddr);
    }

    @Override
    public void updateUserAddress(Long schoolId, String userAddr) {
        userManageMapper.updateUserAddress(schoolId, userAddr);
    }

    @Override
    public void updatePastEducation(Long schoolId, String pastEducation) {
        userManageMapper.updatePastEducation(schoolId, pastEducation);
    }

    @Override
    public void updatePastWork(Long schoolId, String pastWork) {
        userManageMapper.updatePastWork(schoolId, pastWork);
    }

    @Override
    public void updateResearch(Long schoolId, String research) {
        userManageMapper.updateResearch(schoolId, research);
    }

    @Override
    public void updateTeaching(Long schoolId, String teaching) {
        userManageMapper.updateTeaching(schoolId, teaching);
    }

    @Override
    public void updateStudentAdmin(StudentExtend studentExtend) {
        Long schoolId =  studentExtend.getSchoolId();

        if(studentExtend.getUserName()!=null && !studentExtend.getUserName().isEmpty()){
            userManageMapper.updateUsername(schoolId, studentExtend.getUserName());
        }
        if(studentExtend.getGender()!=null && !studentExtend.getGender().isEmpty()){
            userManageMapper.updateGender(schoolId, studentExtend.getGender());
        }
        if(studentExtend.getIdType()!=null && !studentExtend.getIdType().isEmpty()){
            userManageMapper.updateIdType(schoolId, studentExtend.getIdType());
        }
        if(studentExtend.getIdNumber()!=null && !studentExtend.getIdNumber().isEmpty()){
            userManageMapper.updateIdNumber(schoolId, studentExtend.getIdNumber());
        }
        if(studentExtend.getBirthday()!=null){
            userManageMapper.updateBirthday(schoolId, studentExtend.getBirthday());
        }
        if(studentExtend.getCountry()!=null && !studentExtend.getCountry().isEmpty()){
            userManageMapper.updateCountry(schoolId, studentExtend.getCountry());
        }
        if(studentExtend.getNation()!=null && !studentExtend.getNation().isEmpty()){
            userManageMapper.updateNation(schoolId, studentExtend.getNation());
        }
        if(studentExtend.getPoliticalRole()!=null && !studentExtend.getPoliticalRole().isEmpty()){
            userManageMapper.updatePoliticalRole(schoolId, studentExtend.getPoliticalRole());
        }
        if(studentExtend.getCollege()!=null && !studentExtend.getCollege().isEmpty()){
            userManageMapper.updateCollege(schoolId, studentExtend.getCollege());
        }
        if(studentExtend.getMajor()!=null && !studentExtend.getMajor().isEmpty()){
            userManageMapper.updateMajor(schoolId, studentExtend.getMajor());
        }
        if(studentExtend.getClassName()!=null && !studentExtend.getClassName().isEmpty()){
            userManageMapper.updateClassName(schoolId, studentExtend.getClassName());
        }
        if(studentExtend.getDuration()!=null){
            userManageMapper.updateDuration(schoolId, studentExtend.getDuration());
        }
        if(studentExtend.getSchoolingType()!=null && !studentExtend.getSchoolingType().isEmpty()){
            userManageMapper.updateSchoolingType(schoolId, studentExtend.getSchoolingType());
        }
        if(studentExtend.getAdmissionTime()!=null){
            userManageMapper.updateSTUAdmissionTime(schoolId, studentExtend.getAdmissionTime());
        }
        if(studentExtend.getExpectedGraduation()!=null){
            userManageMapper.updateExpectedGraduation(schoolId, studentExtend.getExpectedGraduation());
        }
        if(studentExtend.getCurrentEducation()!=null && !studentExtend.getCurrentEducation().isEmpty()){
            userManageMapper.updateCurrentEducation(schoolId, studentExtend.getCurrentEducation());
        }
        if(studentExtend.getGainedEducation()!=null && !studentExtend.getGainedEducation().isEmpty()){
            userManageMapper.updateGainedEducation(schoolId, studentExtend.getGainedEducation());
        }
        if(studentExtend.getGraduatedSchool()!=null && !studentExtend.getGraduatedSchool().isEmpty()){
            userManageMapper.updateGraduatedSchool(schoolId, studentExtend.getGraduatedSchool());
        }
        if(studentExtend.getCurrentRanking()!=null && !studentExtend.getCurrentRanking().isEmpty()){
            userManageMapper.updateCurrentRanking(schoolId, studentExtend.getCurrentRanking());
        }
        if(studentExtend.getCurrentGrades()!=null){
            userManageMapper.updateCurrentGrades(schoolId, studentExtend.getCurrentGrades());
        }


    }

    @Override
    public void updateTeacherAdmin(TeacherExtend teacherExtend) {
        Long schoolId = teacherExtend.getSchoolId();

        if(teacherExtend.getUserName()!=null && !teacherExtend.getUserName().isEmpty()){
            userManageMapper.updateUsername(schoolId, teacherExtend.getUserName());
        }
        if(teacherExtend.getGender()!=null && !teacherExtend.getGender().isEmpty()){
            userManageMapper.updateGender(schoolId, teacherExtend.getGender());
        }
        if(teacherExtend.getIdType()!=null && !teacherExtend.getIdType().isEmpty()){
            userManageMapper.updateIdType(schoolId, teacherExtend.getIdType());
        }
        if(teacherExtend.getIdNumber()!=null && !teacherExtend.getIdNumber().isEmpty()){
            userManageMapper.updateIdNumber(schoolId, teacherExtend.getIdNumber());
        }
        if(teacherExtend.getBirthday()!=null){
            userManageMapper.updateBirthday(schoolId, teacherExtend.getBirthday());
        }
        if(teacherExtend.getCountry()!=null && !teacherExtend.getCountry().isEmpty()){
            userManageMapper.updateCountry(schoolId, teacherExtend.getCountry());
        }
        if(teacherExtend.getNation()!=null && !teacherExtend.getNation().isEmpty()){
            userManageMapper.updateNation(schoolId, teacherExtend.getNation());
        }
        if(teacherExtend.getPoliticalRole()!=null && !teacherExtend.getPoliticalRole().isEmpty()){
            userManageMapper.updatePoliticalRole(schoolId, teacherExtend.getPoliticalRole());
        }
        if(teacherExtend.getUserPhoto()!=null && !teacherExtend.getUserPhoto().isEmpty()){
            userManageMapper.updateUserPhoto(schoolId, teacherExtend.getUserPhoto());
        }
        
        if (teacherExtend.getJobTitle() != null && !teacherExtend.getJobTitle().isEmpty()) {
            userManageMapper.updateJobTitle(schoolId, teacherExtend.getJobTitle());
        }
        if (teacherExtend.getAdmissionTime() != null) {
            userManageMapper.updateTeaAdmissionTime(schoolId, teacherExtend.getAdmissionTime());
        }
        if (teacherExtend.getEducation() != null && !teacherExtend.getEducation().isEmpty()) {
            userManageMapper.updateEducation(schoolId, teacherExtend.getEducation());
        }
    }
    @Override
    public User findByIdNumber(String idType, String idNumber) {
        return userManageMapper.findByIdNumber(idType, idNumber);
    }

    @Override
    public void addAdmin(User user) {
        if (user.getPassword() == null) {
            String defaultPassword = "hi" + user.getSchoolId().toString();
            defaultPassword = Md5Util.getMD5String(defaultPassword);
            user.setPassword(defaultPassword);
        }
        if (user.getEmail() == null) {
            String email = user.getSchoolId().toString() + "@example.com";
            user.setEmail(email);
        }
        if (user.getUserPhoto() == null) {
            user.setUserPhoto("https://academic-sys.oss-cn-hangzhou.aliyuncs.com/userPhoto/default.png");
        }
        if (user.getUserAddr() == null) {
            user.setUserAddr("待补充");
        }
        if (user.getBirthAddr() == null) {
            user.setBirthAddr("待补充");
        }
        userManageMapper.addAdminUser(user);
    }

    @Override
    public void updatePasswordOut(Long schoolId, String newPassword) {
        userManageMapper.updatePassword(schoolId, Md5Util.getMD5String(newPassword));
    }

    @Override
    public User findUserById(String idType, String idNumber) {
        return userManageMapper.findUserById(idType, idNumber);
    }

    @Override
    public void updateAdmin(User user) {
        Long schoolId = user.getSchoolId();
        if (user.getUserName() != null && !user.getUserName().isEmpty()) {
            userManageMapper.updateUsername(schoolId, user.getUserName());
        }
        if (user.getGender() != null && !user.getGender().isEmpty()) {
            userManageMapper.updateGender(schoolId, user.getGender());
        }
        if (user.getIdType() != null && !user.getIdType().isEmpty()) {
            userManageMapper.updateIdType(schoolId, user.getIdType());
        }
        if (user.getIdNumber() != null && !user.getIdNumber().isEmpty()) {
            userManageMapper.updateIdNumber(schoolId, user.getIdNumber());
        }
        if (user.getBirthday() != null) {
            userManageMapper.updateBirthday(schoolId, user.getBirthday());
        }
        if (user.getCountry() != null && !user.getCountry().isEmpty()) {
            userManageMapper.updateCountry(schoolId, user.getCountry());
        }
        if (user.getNation() != null && !user.getNation().isEmpty()) {
            userManageMapper.updateNation(schoolId, user.getNation());
        }
        if (user.getPoliticalRole() != null && !user.getPoliticalRole().isEmpty()) {
            userManageMapper.updatePoliticalRole(schoolId, user.getPoliticalRole());
        }
        if (user.getUserAddr() != null && !user.getUserAddr().isEmpty()) {
            userManageMapper.updateUserAddress(schoolId, user.getUserAddr());
        }
        if (user.getBirthAddr() != null && !user.getBirthAddr().isEmpty()) {
            userManageMapper.updateBirthAddress(schoolId, user.getBirthAddr());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userManageMapper.updateEmail(schoolId, user.getEmail());
        }
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            userManageMapper.updatePhone(schoolId, user.getPhone());
        }
    }

    @Override
    public void updatePhoto(Long schoolId, String photoUrl) {
        userManageMapper.updateUserPhoto(schoolId, photoUrl);
    }

    @Override
    public List<User> queryAdmin(User user, Integer i, Integer pageSize, Date birthdayFrom, Date birthdayTo, Boolean allowBlurredQuery) {
        return userManageMapper.queryAdmin(user, i, pageSize, birthdayFrom, birthdayTo, allowBlurredQuery);
    }
}
