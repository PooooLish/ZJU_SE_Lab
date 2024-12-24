package com.backend.academicsys.controller;

import com.backend.academicsys.entity.*;
import com.backend.academicsys.service.UserService;
import com.backend.academicsys.utils.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.backend.academicsys.entity.Result;



import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public Result<String> login(Long schoolId, @Pattern(regexp = "^[0-9a-zA-Z]{6,16}$", message = "密码格式错误") String password) {
        User user = userService.findUser(schoolId);
        if (user == null) {
            return new Result<>(400, "用户不存在", null);
        }

        String storedPassword = user.getPassword();
        String inputPassword = Md5Util.getMD5String(password);
        if (!storedPassword.equals(inputPassword)) {
            return new Result<>(400, "密码错误", null);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getSchoolId());
        claims.put("username", user.getUserName());
        String token = JwtUtil.genToken(claims);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token, token, 1, TimeUnit.HOURS);

        return new Result<>(200, "登录成功", token);
    }

    @GetMapping("/infoStu")
    public Result<StudentExtend> infoStu() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 3) {
            return new Result<>(400, "需学生本人查看", null);
        }
        StudentExtend user = userService.findStudent(schoolId);
        return new Result<>(200, "查询成功", user);
    }

    @GetMapping("/infoTea")
    public Result<TeacherExtend> infoTea() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 2) {
            return new Result<>(400, "需教师本人查看", null);
        }
        TeacherExtend user = userService.findTeacher(schoolId);
        return new Result<>(200, "查询成功", user);
    }

    @GetMapping("/infoTakeOffice")
    public Result<List<TakeOffice>> infoTakeOffice() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        if ((int) (schoolId / 1e10) != 2) {
            return new Result<>(400, "需教师本人查看", null);
        }
        List<TakeOffice> takeOfficeList = userService.findTakeOfficeBySchoolId(schoolId);
        return new Result<>(200, "查询成功", takeOfficeList);
    }

    @GetMapping("/info")
    public Result<User> info() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        User user = userService.findUser(schoolId);
        return new Result<>(200, "查询成功", user);
    }

    @PostMapping("/logout")
    public Result<String> logout(@RequestHeader("Authorization") String token) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        RedisOperations<String, String> redisOperations = operations.getOperations();
        redisOperations.delete(token);
        return Result.success("退出成功");
    }

    @PostMapping("/updatePwd")
    public Result<String> updatePassword(String oldPassword, String newPassword, String rePassword, @RequestHeader("Authorization") String token) {

        if (oldPassword.isEmpty() || newPassword.isEmpty() || rePassword.isEmpty()) {
            return Result.fail("密码不能为空");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        User loginUser = userService.findUser(schoolId);
        if (!rePassword.equals(newPassword)) {
            return Result.fail("两次填写的新密码不一样");
        }
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPassword))) {
            return Result.fail("原密码填写不正确");
        }

        userService.updatePassword(schoolId, newPassword);
//        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//        operations.getOperations().delete(token);
        return Result.success();
    }

    @PostMapping("/updateStu")
    public Result<String> updateStu(@Email String email, String phone, String birthAddr, String userAddr) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        StudentExtend student = userService.findStudent(schoolId);
        if (student == null) {
            return Result.fail("用户非学生");
        }
        if (email.isEmpty() && phone.isEmpty() && userAddr.isEmpty() && birthAddr.isEmpty()) {
            return Result.fail("修改信息不能为空");
        }
        if (!email.isEmpty()) {
            User user = userService.findByEmail(email);
            if (user != null && !user.getSchoolId().equals(schoolId)) {
                return Result.fail("邮箱已被注册");
            }
            userService.updateEmail(schoolId, email);
        }
        if (!phone.isEmpty()) {
            User user = userService.findByPhone(phone);
            if (user != null && !user.getSchoolId().equals(schoolId)) {
                return Result.fail("手机号已被注册");
            }
            userService.updatePhone(schoolId, phone);
        }
        if (!birthAddr.isEmpty()) {
            userService.updateBirthAddress(schoolId, birthAddr);
        }
        if (!userAddr.isEmpty()) {
            userService.updateUserAddress(schoolId, userAddr);
        }

        return Result.success();
    }

    @PostMapping("/updateTea")
    public Result<String> updateTeacher(
            @Email String email, String phone,
            String birthAddr, String userAddr,
            String pastEducation, String pastWork,
            String research, String teaching
    ) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        TeacherExtend teacher = userService.findTeacher(schoolId);
        if (teacher == null) {
            return Result.fail("用户非教师");
        }
        if (!email.isEmpty()) {
            User user = userService.findByEmail(email);
            if (user != null && !user.getSchoolId().equals(schoolId)) {
                return Result.fail("邮箱已被注册");
            }
            userService.updateEmail(schoolId, email);
        }
        if (!phone.isEmpty()) {
            User user = userService.findByPhone(phone);
            if (user != null && !user.getSchoolId().equals(schoolId)) {
                return Result.fail("手机号已被注册");
            }
            userService.updatePhone(schoolId, phone);
        }
        if (!birthAddr.isEmpty()) {
            userService.updateBirthAddress(schoolId, birthAddr);
        }
        if (!userAddr.isEmpty()) {
            userService.updateUserAddress(schoolId, userAddr);
        }
        if (!pastEducation.isEmpty()) {
            userService.updatePastEducation(schoolId, pastEducation);
        }
        if (!pastWork.isEmpty()) {
            userService.updatePastWork(schoolId, pastWork);
        }
        if (!research.isEmpty()) {
            userService.updateResearch(schoolId, research);
        }
        if (!teaching.isEmpty()) {
            userService.updateTeaching(schoolId, teaching);
        }

        return Result.success();
    }

    // 更新时检查身份证号是否已存在
    private boolean checkUpdateIdentity(String idType, String idNumber, Long schoolId) {
        if (idType == null || idNumber == null) {
            return true;
        }
        User userById = userService.findByIdNumber(idType, idNumber);
        return userById == null || userById.getSchoolId().equals(schoolId);
    }

    // 允许更新所有的参数，非关键信息除外
    @PostMapping("/updateStuAdmin")
    public Result<String> updateStudentAdmin(@RequestBody StudentExtend studentExtend) {
        if (!userService.checkAdmin()) {
            return Result.fail("权限不足");
        }

        if(studentExtend.getSchoolId() == null){
            return Result.fail("修改信息需要学号");
        }
        User user = userService.findUser(studentExtend.getSchoolId());
        if(user == null){
            return Result.fail("用户不存在");
        }
        userService.updateStudentAdmin(studentExtend);

        return Result.success();
    }

    // 允许更新所有的参数，非关键信息除外
    @PostMapping("/updateTeaAdmin")
    public Result<String> updateTeacherAdmin(@RequestBody TeacherExtend teacherExtend) {
        if (!userService.checkAdmin()) {
            return Result.fail("权限不足");
        }
        // 控制台打印输出studentExtend的内容
        if(teacherExtend == null){
            return Result.fail("修改信息不能为空");
        }
        System.out.println(teacherExtend);

        if(teacherExtend.getSchoolId() == null){
            return Result.fail("修改信息需要学号");
        }
        User user = userService.findUser(teacherExtend.getSchoolId());
        if(user == null){
            return Result.fail("用户不存在");
        }
        if (!checkUpdateIdentity(teacherExtend.getIdType(), teacherExtend.getIdNumber(), teacherExtend.getSchoolId())) {
            return Result.fail("身份证号已存在");
        }
        userService.updateTeacherAdmin(teacherExtend);
        return Result.success();
    }

    @PostMapping("/updateAdmin")
    public Result<String> updateAdmin(@RequestBody User user) {
        if (!userService.checkAdmin()) {
            return Result.fail("权限不足");
        }
        if(user.getSchoolId() == null){
            return Result.fail("修改信息需要学号");
        }
        User userOri = userService.findUser(user.getSchoolId());
        if(userOri == null){
            return Result.fail("用户不存在");
        }
        userService.updateAdmin(user);
        return Result.success();
    }

    @PostMapping("/updateStuAdminByDoc")
    public Result<List<StudentExtend>> updateStudentAdminByDoc(
            MultipartFile file
    ) {
        if (!userService.checkAdmin()) {
            return Result.fail("权限不足");
        }
        List<StudentExtend> failedList = new ArrayList<>();
        List<StudentExtend> studentExtends = AnalyseDoc.updateStudentByDoc(file);
        for (StudentExtend studentExtend : studentExtends) {
            if (studentExtend.getSchoolId()==null) {
                failedList.add(studentExtend);
            } else if(userService.findUser(studentExtend.getSchoolId()) == null){
                return Result.fail("用户不存在");
            } else if (!checkUpdateIdentity(studentExtend.getIdType(), studentExtend.getIdNumber(), studentExtend.getSchoolId())) {
                failedList.add(studentExtend);
            }
            else {
                userService.updateStudentAdmin(studentExtend);
            }
        }
        if (failedList.isEmpty()) {
            return Result.success(null);
        } else {
            return Result.fail("部分更新失败", failedList);
        }
    }


    @PostMapping("/updateTeaAdminByDoc")
    public Result<List<TeacherExtend>> updateTeacherAdminByDoc(
            MultipartFile file
    ) {
        if (!userService.checkAdmin()) {
            return Result.fail("权限不足");
        }
        List<TeacherExtend> failedList = new ArrayList<>();
        List<TeacherExtend> teacherExtends = AnalyseDoc.updateTeacherByDoc(file);
        for (TeacherExtend teacherExtend : teacherExtends) {
            if (teacherExtend.getSchoolId()==null) {
                failedList.add(teacherExtend);
            } else if(userService.findUser(teacherExtend.getSchoolId()) == null){
                return Result.fail("用户不存在");
            } else if (!checkUpdateIdentity(teacherExtend.getIdType(), teacherExtend.getIdNumber(), teacherExtend.getSchoolId())) {
                failedList.add(teacherExtend);
            }
            else {
                userService.updateTeacherAdmin(teacherExtend);
            }
        }
        if (failedList.isEmpty()) {
            return Result.success(null);
        } else {
            return Result.fail("部分更新失败", failedList);
        }
    }

    @PostMapping("/uploadPhoto")
    public Result<String> uploadPhoto(MultipartFile file) throws IOException {
        Map<String, Object> map = ThreadLocalUtil.get();
        Long schoolId = (Long) map.get("id");
        User user = userService.findUser(schoolId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        String localPath = FileUtil.savePhotoFile(file, schoolId);
        String photoUrl = FileUtil.uploadPhotoFile(localPath, schoolId);
        userService.updatePhoto(schoolId, photoUrl);
        FileUtil.deletePhotoFile(localPath);
        return Result.success(photoUrl);
    }

    @GetMapping("/queryStu")
    public Result<List<StudentExtend>> queryStudent(
            @ModelAttribute StudentExtend studentExtend,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayTo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeTo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date graduationTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date graduationTimeTo,
            Integer page, Integer pageSize,
            Boolean allowBlurredQuery
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (page == null || pageSize == null) {
            return new Result<>(400, "分页参数不能为空", null);
        }
        List<StudentExtend> users = userService.queryStudent(
                studentExtend, birthdayFrom, birthdayTo,
                admissionTimeFrom, admissionTimeTo,
                graduationTimeFrom, graduationTimeTo,
                (page - 1) * pageSize, pageSize, allowBlurredQuery);
        return new Result<>(200, "查询成功", users);
    }

    @GetMapping("/queryStuNum")
    public Result<Integer> queryStudentNum(
            @ModelAttribute StudentExtend studentExtend,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayTo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeTo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date graduationTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date graduationTimeTo,
            Boolean allowBlurredQuery
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        Integer res = userService.queryStudent(
                studentExtend, birthdayFrom, birthdayTo,
                admissionTimeFrom, admissionTimeTo,
                graduationTimeFrom, graduationTimeTo,
                null, null, allowBlurredQuery).size();
        return new Result<>(200, "查询成功", res);
    }


    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TeacherQueryResult extends TeacherExtend {
        private List<TakeOffice> takeOfficeList;
    }

    @GetMapping("/queryTea")
    public Result<List<TeacherQueryResult>> queryTeacher(
            @ModelAttribute TeacherExtend teacherExtend,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeTo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayTo,
            String department,
            Integer page, Integer pageSize,
            Boolean allowBlurredQuery,
            Boolean withTakeOffice
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        if (withTakeOffice == null) {
            withTakeOffice = false;
        }
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (page == null || pageSize == null) {
            return new Result<>(400, "分页参数不能为空", null);
        }
        List<TeacherQueryResult> users = userService.queryTeacher(
                teacherExtend, admissionTimeFrom, admissionTimeTo,
                birthdayFrom, birthdayTo, department,
                (page - 1) * pageSize, pageSize, allowBlurredQuery, withTakeOffice);
        return new Result<>(200, "查询成功", users);
    }

    @GetMapping("/queryTeaNum")
    public Result<Integer> queryTeacherNum(
            @ModelAttribute TeacherExtend teacherExtend,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeTo,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayTo,
            String department,
            Boolean allowBlurredQuery
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        Integer res = userService.queryTeacher(
                teacherExtend, admissionTimeFrom, admissionTimeTo,
                birthdayFrom, birthdayTo, department,
                null, null, allowBlurredQuery, false).size();
        return new Result<>(200, "查询成功", res);
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class TeacherQueryResultPublic extends TeacherPublic {
        private List<TakeOffice> takeOfficeList;
    }

    @GetMapping("/queryTeaPublic")
    public Result<List<TeacherQueryResultPublic>> queryTeacherPublic(
            @ModelAttribute TeacherPublic teacherPublic,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeTo,
            String department,
            Integer page, Integer pageSize,
            Boolean allowBlurredQuery,
            Boolean withTakeOffice
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        if (withTakeOffice == null) {
            withTakeOffice = false;
        }
        if (page == null || pageSize == null) {
            return new Result<>(400, "分页参数不能为空", null);
        }
        List<TeacherQueryResultPublic> users = userService.queryTeacherPublic(
                teacherPublic, admissionTimeFrom, admissionTimeTo, department,
                (page - 1) * pageSize, pageSize,
                allowBlurredQuery, withTakeOffice);
        return new Result<>(200, "查询成功", users);
    }

    @GetMapping("/queryTeaPublicNum")
    public Result<Integer> queryTeacherPublicNum(
            @ModelAttribute TeacherPublic teacherPublic,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date admissionTimeTo,
            String department,
            Boolean allowBlurredQuery
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        Integer res = userService.queryTeacherPublic(
                teacherPublic, admissionTimeFrom, admissionTimeTo, department,
                null, null, allowBlurredQuery, false).size();
        return new Result<>(200, "查询成功", res);
    }

    @GetMapping("/queryAdmin")
    public Result<List<User>> queryAdmin(
            @ModelAttribute User user,
            Integer page, Integer pageSize,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayTo,
            Boolean allowBlurredQuery
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (page == null || pageSize == null) {
            return new Result<>(400, "分页参数不能为空", null);
        }
        if (user.getSchoolId() != null && (int) (user.getSchoolId() / 1e10) != 1) {
            return new Result<>(200, "查询成功", null);
        }
        List<User> users = userService.queryAdmin(
                user, (page - 1) * pageSize, pageSize, birthdayFrom, birthdayTo, allowBlurredQuery);
        return new Result<>(200, "查询成功", users);
    }

    @GetMapping("/queryAdminNum")
    public Result<Integer> queryAdminNum(
            @ModelAttribute User user,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayFrom,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdayTo,
            Boolean allowBlurredQuery
    ) {
        if (allowBlurredQuery == null) {
            allowBlurredQuery = false;
        }
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (user.getSchoolId() != null && (int) (user.getSchoolId() / 1e10) != 1) {
            return new Result<>(200, "查询成功", 0);
        }
        Integer res = userService.queryAdmin(user, null, null, birthdayFrom, birthdayTo, allowBlurredQuery).size();
        return new Result<>(200, "查询成功", res);
    }

    private Result<String> checkStudent(StudentExtend studentExtend) {
        boolean info = studentExtend.getSchoolId() != null || studentExtend.getUserName() != null
                || studentExtend.getGender() != null || studentExtend.getPhone() != null
                || studentExtend.getCountry() != null || studentExtend.getNation() != null
                || studentExtend.getPoliticalRole() != null || studentExtend.getAdmissionTime() != null
                || studentExtend.getGainedEducation() != null || studentExtend.getCurrentEducation() != null
                || studentExtend.getCollege() != null || studentExtend.getDuration() != null
                || studentExtend.getSchoolingType() != null || studentExtend.getIdType() != null
                || studentExtend.getIdNumber() != null || studentExtend.getBirthday() != null
                || studentExtend.getMajor() != null || studentExtend.getClassName() != null
                || studentExtend.getExpectedGraduation() != null || studentExtend.getGraduatedSchool() != null;
        if (!info) {
            return new Result<>(400, "信息不完整", null);
        }
        if (userService.findUser(studentExtend.getSchoolId()) != null) {
            return new Result<>(400, "用户已存在", null);
        }
        if (userService.findUserById(studentExtend.getIdType(), studentExtend.getIdNumber()) != null) {
            return new Result<>(400, "身份证号已存在", null);
        }
        if (userService.findByPhone(studentExtend.getPhone()) != null) {
            return new Result<>(400, "电话号码已存在", null);
        }
        if ((int) (studentExtend.getSchoolId() / 1e10) != 3) {
            return new Result<>(400, "学工号不符合规范", null);
        }
        return new Result<>(200, "信息无误", null);
    }

    @PostMapping("/addStu")
    public Result<String> addStudent(@RequestBody StudentExtend studentExtend) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        Result<String> result = checkStudent(studentExtend);
        if (result.getCode() != 200) {
            return result;
        }
        userService.addStudent(studentExtend);
        return new Result<>(200, "添加成功", null);
    }

    @PostMapping("/addStuByDoc")
    public Result<List<StudentExtend>> addStudentByDoc(MultipartFile file) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        List<StudentExtend> failedList = new ArrayList<>();
        List<StudentExtend> studentExtends = AnalyseDoc.addStudentByDoc(file);
        for (StudentExtend studentExtend : studentExtends) {
            Result<String> result = checkStudent(studentExtend);
            if (result.getCode() != 200) {
                failedList.add(studentExtend);
            } else {
                userService.addStudent(studentExtend);
            }
        }
        if (failedList.isEmpty()) {
            return new Result<>(200, "添加成功", null);
        } else {
            return new Result<>(400, "部分添加失败", failedList);
        }
    }

    private Result<String> checkTeacher(TeacherExtend teacherExtend) {
        boolean info = teacherExtend.getSchoolId() != null || teacherExtend.getUserName() != null
                || teacherExtend.getGender() != null || teacherExtend.getPhone() != null
                || teacherExtend.getCountry() != null || teacherExtend.getNation() != null
                || teacherExtend.getIdType() != null || teacherExtend.getIdNumber() != null
                || teacherExtend.getPoliticalRole() != null || teacherExtend.getAdmissionTime() != null
                || teacherExtend.getEducation() != null || teacherExtend.getJobTitle() != null;
        if (!info) {
            return new Result<>(400, "信息不完整", null);
        }
        if (userService.findUser(teacherExtend.getSchoolId()) != null) {
            return new Result<>(400, "用户已存在", null);
        }
        if (userService.findUserById(teacherExtend.getIdType(), teacherExtend.getIdNumber()) != null) {
            return new Result<>(400, "身份证号已存在", null);
        }
        if (userService.findByPhone(teacherExtend.getPhone()) != null) {
            return new Result<>(400, "电话号码已存在", null);
        }
        if ((int) (teacherExtend.getSchoolId() / 1e10) != 2) {
            return new Result<>(400, "学工号不符合规范", null);
        }
        return new Result<>(200, "信息无误", null);
    }

    @PostMapping("/addTea")
    public Result<String> addTeacher(@RequestBody TeacherExtend teacherExtend) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        Result<String> result = checkTeacher(teacherExtend);
        if (result.getCode() != 200) {
            return result;
        }
        userService.addTeacher(teacherExtend);
        return new Result<>(200, "添加成功", null);
    }

    @PostMapping("/addTeaByDoc")
    public Result<List<TeacherExtend>> addTeacherByDoc(MultipartFile file) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        List<TeacherExtend> failedList = new ArrayList<>();
        List<TeacherExtend> teacherExtends = AnalyseDoc.addTeacherByDoc(file);
        for (TeacherExtend teacherExtend : teacherExtends) {
            Result<String> result = checkTeacher(teacherExtend);
            if (result.getCode() != 200) {
                failedList.add(teacherExtend);
            } else {
                userService.addTeacher(teacherExtend);
            }
        }
        if (failedList.isEmpty()) {
            return new Result<>(200, "添加成功", null);
        } else {
            return new Result<>(400, "部分添加失败", failedList);
        }
    }

    private Result<String> checkUser(User user) {
        boolean info = user.getSchoolId() != null || user.getUserName() != null
                || user.getGender() != null || user.getPhone() != null
                || user.getCountry() != null || user.getNation() != null
                || user.getIdType() != null || user.getIdNumber() != null
                || user.getPoliticalRole() != null || user.getBirthday() != null;
        if (!info) {
            return new Result<>(400, "信息不完整", null);
        }
        if (userService.findUser(user.getSchoolId()) != null) {
            return new Result<>(400, "用户已存在", null);
        }
        if (userService.findUserById(user.getIdType(), user.getIdNumber()) != null) {
            return new Result<>(400, "身份证号已存在", null);
        }
        if (userService.findByPhone(user.getPhone()) != null) {
            return new Result<>(400, "电话号码已存在", null);
        }
        if ((int) (user.getSchoolId() / 1e10) != 1) {
            return new Result<>(400, "学工号不符合规范", null);
        }
        return new Result<>(200, "信息无误", null);
    }

    @PostMapping("/addAdmin")
    public Result<String> addAdmin(@RequestBody User user) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        Result<String> result = checkUser(user);
        if (result.getCode() != 200) {
            return result;
        }
        userService.addAdmin(user);
        return new Result<>(200, "添加成功", null);
    }

    @PostMapping("/addTakeOffice")
    public Result<String> addTakeOffice(@RequestBody TakeOffice takeOffice) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (takeOffice.getSchoolId() == null || takeOffice.getDepartment() == null || takeOffice.getWorkPosition() == null || takeOffice.getStartTime() == null) {
            return new Result<>(400, "信息不完整", null);
        }
        TakeOffice takeOfficeOri = userService.findTakeOffice(takeOffice.getSchoolId(), takeOffice.getDepartment(), takeOffice.getWorkPosition());
        if (takeOfficeOri != null) {
            return new Result<>(400, "记录已存在", null);
        }
        userService.addTakeOffice(takeOffice);
        return new Result<>(200, "添加成功", null);
    }

    @PostMapping("/updateTakeOffice")
    public Result<String> updateTakeOffice(@RequestBody TakeOffice takeOffice) {
        // 只能更新结束时间
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (takeOffice.getSchoolId() == null || takeOffice.getDepartment() == null || takeOffice.getWorkPosition() == null || takeOffice.getEndTime() == null) {
            return new Result<>(400, "信息不完整", null);
        }
        TakeOffice takeOfficeOri = userService.findTakeOffice(takeOffice.getSchoolId(), takeOffice.getDepartment(), takeOffice.getWorkPosition());
        if (takeOfficeOri == null) {
            return new Result<>(400, "记录不存在", null);
        }
        userService.updateTakeOffice(takeOffice);
        return new Result<>(200, "更新成功", null);
    }

    @PostMapping("deleteTakeOffice")
    public Result<String> deleteTakeOffice(@RequestBody TakeOffice takeOffice) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (takeOffice.getSchoolId() == null || takeOffice.getDepartment() == null || takeOffice.getWorkPosition() == null) {
            return new Result<>(400, "信息不完整", null);
        }
        TakeOffice takeOfficeOri = userService.findTakeOffice(takeOffice.getSchoolId(), takeOffice.getDepartment(), takeOffice.getWorkPosition());
        if (takeOfficeOri == null) {
            return new Result<>(400, "记录不存在", null);
        }
        userService.deleteTakeOffice(takeOffice);
        return new Result<>(200, "删除成功", null);
    }

    @PostMapping("/deleteUser")
    public Result<String> deleteUser(Long schoolId) {
        // 将彻底删除用户以及相关信息，慎用
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        if (schoolId == null) {
            return new Result<>(400, "学工号不能为空", null);
        }
        User user = userService.findUser(schoolId);
        if (user == null) {
            return new Result<>(400, "用户不存在", null);
        }
        User temp = new User();
        if (userService.queryAdmin(temp, null, null, null, null, false).size() == 1 && (int) (schoolId / 1e10) == 1) {
            return new Result<>(400, "至少保留一个管理员", null);
        }
        userService.deleteUser(schoolId);
        return new Result<>(200, "删除成功", null);
    }

    @PostMapping("/deleteUserByDoc")
    public Result<List<Long>> deleteUserByDoc(MultipartFile file) {
        if (!userService.checkAdmin()) {
            return new Result<>(400, "权限不足", null);
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        Long originId = (Long) map.get("id");
        List<Long> failedList = new ArrayList<>();
        List<Long> schoolIds = AnalyseDoc.deleteUserByDoc(file);
        for (Long schoolId : schoolIds) {
            if (schoolId == null) {
                failedList.add(null);
            } else {
                User user = userService.findUser(schoolId);
                if (user == null) {
                    failedList.add(schoolId);
                } else if (Objects.equals(originId, schoolId)) {
                    failedList.add(schoolId);
                } else {
                    userService.deleteUser(schoolId);
                }
            }
        }
        if (failedList.isEmpty()) {
            return new Result<>(200, "删除成功", null);
        } else {
            return new Result<>(400, "部分删除失败", failedList);
        }
    }

    @PostMapping("/checkEmailCode")
    public Result<String> checkEmailCode(Long schoolId, String email, String code) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        if (schoolId == null) {
            return Result.fail("学号不能为空");
        }
        if (email.isEmpty() || code.isEmpty()) {
            return Result.fail("邮箱或验证码不能为空");
        }
        User user1 = userService.findUser(schoolId);
        User user2 = userService.findByEmail(email);
        if (user1 == null || user2 == null) {
            return Result.fail("用户不存在");
        }
        if (!Objects.equals(user1.getSchoolId(), user2.getSchoolId())) {
            return Result.fail("学号与邮箱不匹配");
        }
        String storedCode = operations.get(email);
        if (storedCode == null) {
            return new Result<>(400, "验证码已过期", null);
        }
        if (!storedCode.equals(code)) {
            return new Result<>(400, "验证码错误", null);
        }
        operations.set(String.valueOf(schoolId), String.valueOf(schoolId), 5, TimeUnit.MINUTES);
        RedisOperations<String, String> redisOperations = operations.getOperations();
        redisOperations.delete(email);
        return new Result<>(200, "验证成功，请在 5 分钟内重置密码", null);
    }

    @PostMapping("/findPwd")
    public Result<String> findPassword(Long schoolId, String email, String id_type, String id_number) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String storedCode = operations.get(email);
        if (storedCode != null) {
            return new Result<>(400, "请勿重复发送验证码", null);
        }
        if (schoolId == null) {
            return Result.fail("学号不能为空");
        }
        if (email.isEmpty() || id_number.isEmpty() || id_type.isEmpty()) {
            return Result.fail("手机号或证件号不能为空");
        }
        User user1 = userService.findByEmail(email);
        User user2 = userService.findByIdNumber(id_type, id_number);
        User user3 = userService.findUser(schoolId);
        if (user1 == null || user2 == null || user3 == null) {
            return Result.fail("用户不存在");
        }
        if (!Objects.equals(user1.getSchoolId(), user2.getSchoolId()) || !Objects.equals(user1.getSchoolId(), user3.getSchoolId())) {
            return Result.fail("信息不匹配");
        }
        // 随机生成6位验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        if (code.length() != 6) {
            code = "0" + code;
        }
        String subject = "【教务管理系统】 密码找回验证码";
        String content = "您的验证码是：" + code +
                "\n请勿泄露给他人，验证码有效期为5分钟。";
        Result<String> res = EmailUtils.sendEmail(email, subject, content);
        if (res.getCode() == 200) {
            operations.set(email, code, 5, TimeUnit.MINUTES);
        }
        return res;
    }

    @PostMapping("/resetPwd")
    public Result<String> resetPassword(Long schoolId, String newPassword) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String storedCode = operations.get(String.valueOf(schoolId));
        if (storedCode == null) {
            return new Result<>(400, "请先验证邮箱", null);
        }
        if (schoolId == null) {
            return Result.fail("学号不能为空");
        }
        if (newPassword.isEmpty()) {
            return Result.fail("新密码不能为空");
        }
        userService.updatePasswordOut(schoolId, newPassword);
        RedisOperations<String, String> redisOperations = operations.getOperations();
        redisOperations.delete(String.valueOf(schoolId));
        return Result.success();
    }
}
