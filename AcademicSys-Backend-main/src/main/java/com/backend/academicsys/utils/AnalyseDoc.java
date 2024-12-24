package com.backend.academicsys.utils;

import com.backend.academicsys.entity.StudentExtend;
import com.backend.academicsys.entity.TeacherExtend;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalyseDoc {

    private static final String defaultPhoto = "https://academic-sys.oss-cn-hangzhou.aliyuncs.com/userPhoto/default.png";

    public static List<List<String>> parseExcelFile(InputStream inputStream) {
        List<List<String>> data = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int maxColumns = sheet.getRow(0).getLastCellNum();
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (int i = 0; i < maxColumns; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cell.getCellType()) {
                        case STRING -> rowData.add(cell.getStringCellValue());
                        case NUMERIC -> {
                            if (DateUtil.isCellDateFormatted(cell)) {
                                rowData.add(cell.getDateCellValue().toString());
                            } else {
                                rowData.add(String.valueOf(cell.getNumericCellValue()));
                            }
                        }
                        case BOOLEAN -> rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        case FORMULA -> rowData.add(cell.getCellFormula());
                        default -> rowData.add("");
                    }
                }
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<StudentExtend> addStudentByDoc(MultipartFile file) {
        List<StudentExtend> studentExtends = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            List<List<String>> data = parseExcelFile(inputStream);
            if (!data.isEmpty()) {
                data = data.subList(1, data.size());
            }
            for (List<String> row : data) {
                StudentExtend studentExtend = new StudentExtend();
                studentExtend.setUserName(row.get(0));
                studentExtend.setGender(row.get(1));
                studentExtend.setSchoolId(new BigDecimal(row.get(2)).longValue());
                studentExtend.setIdType(row.get(3));
                studentExtend.setIdNumber(row.get(4));
                studentExtend.setBirthday(new Date(row.get(5)));
                long phone = new BigDecimal(row.get(6)).longValue();
                studentExtend.setPhone(Long.toString(phone));
                String email = studentExtend.getSchoolId().toString() + "@example.com";
                studentExtend.setEmail(email);
                studentExtend.setCountry(row.get(7));
                studentExtend.setNation(row.get(8));
                studentExtend.setPoliticalRole(row.get(9));
                studentExtend.setUserPhoto(defaultPhoto);
                studentExtend.setCollege(row.get(10));
                studentExtend.setClassName(row.get(11));
                studentExtend.setMajor(row.get(12));
                studentExtend.setDuration(new BigDecimal(row.get(13)).intValue());
                studentExtend.setSchoolingType(row.get(14));
                studentExtend.setAdmissionTime(new Date(row.get(15)));
                studentExtend.setExpectedGraduation(new Date(row.get(16)));
                studentExtend.setCurrentEducation(row.get(17));
                studentExtend.setGainedEducation(row.get(18));
                studentExtend.setGraduatedSchool(row.get(19));
                studentExtend.setCurrentGrades(100.0F);
                studentExtend.setCurrentRanking("1/100");
                String defaultPassword = "hi" + studentExtend.getSchoolId().toString();
                defaultPassword = Md5Util.getMD5String(defaultPassword);
                studentExtend.setPassword(defaultPassword);
                studentExtends.add(studentExtend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentExtends;
    }

    public static List<StudentExtend> updateStudentByDoc(MultipartFile file) {
        List<StudentExtend> studentExtends = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            List<List<String>> data = parseExcelFile(inputStream);
            if (!data.isEmpty()) {
                data = data.subList(1, data.size());
            }
            for (List<String> row : data) {
                StudentExtend studentExtend = new StudentExtend();
                studentExtend.setUserName(row.get(0));
                studentExtend.setGender(row.get(1));
                studentExtend.setSchoolId(new BigDecimal(row.get(2)).longValue());
                studentExtend.setIdType(row.get(3));
                studentExtend.setIdNumber(row.get(4));
                if (row.get(5) != null && !row.get(5).isEmpty()) {
                    System.out.println(row.get(5));
                    studentExtend.setBirthday(new Date(row.get(5)));
                }
                if (row.get(6) != null && !row.get(6).isEmpty()) {
                    long phone = new BigDecimal(row.get(6)).longValue();
                    studentExtend.setPhone(Long.toString(phone));
                }
                studentExtend.setCountry(row.get(7));
                studentExtend.setNation(row.get(8));
                studentExtend.setPoliticalRole(row.get(9));
                studentExtend.setCollege(row.get(10));
                studentExtend.setClassName(row.get(11));
                studentExtend.setMajor(row.get(12));
                if (row.get(13) != null && !row.get(13).isEmpty()) {
                    studentExtend.setDuration(new BigDecimal(row.get(13)).intValue());
                }
                studentExtend.setSchoolingType(row.get(14));
                if (row.get(15) != null && !row.get(15).isEmpty()) {
                    studentExtend.setAdmissionTime(new Date(row.get(15)));
                }
                if (row.get(16) != null && !row.get(16).isEmpty()) {
                    studentExtend.setExpectedGraduation(new Date(row.get(16)));
                }
                studentExtend.setCurrentEducation(row.get(17));
                studentExtend.setGainedEducation(row.get(18));
                studentExtend.setGraduatedSchool(row.get(19));
                studentExtends.add(studentExtend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentExtends;
    }

    public static List<TeacherExtend> addTeacherByDoc(MultipartFile file) {
        List<TeacherExtend> teacherExtends = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            List<List<String>> data = parseExcelFile(inputStream);
            if (!data.isEmpty()) {
                data = data.subList(1, data.size());
            }
            for (List<String> row : data) {
                TeacherExtend teacherExtend = new TeacherExtend();
                teacherExtend.setUserName(row.get(0));
                teacherExtend.setGender(row.get(1));
                teacherExtend.setSchoolId(new BigDecimal(row.get(2)).longValue());
                teacherExtend.setIdType(row.get(3));
                teacherExtend.setIdNumber(row.get(4));
                teacherExtend.setBirthday(new Date(row.get(5)));
                long phone = new BigDecimal(row.get(6)).longValue();
                teacherExtend.setPhone(Long.toString(phone));
                String email = teacherExtend.getSchoolId().toString() + "@example.com";
                teacherExtend.setEmail(email);
                teacherExtend.setCountry(row.get(7));
                teacherExtend.setNation(row.get(8));
                teacherExtend.setPoliticalRole(row.get(9));
                teacherExtend.setUserPhoto(defaultPhoto);
                teacherExtend.setJobTitle(row.get(10));
                teacherExtend.setAdmissionTime(new Date(row.get(11)));
                teacherExtend.setEducation(row.get(12));
                String defaultPassword = "hi" + teacherExtend.getSchoolId().toString();
                defaultPassword = Md5Util.getMD5String(defaultPassword);
                teacherExtend.setPassword(defaultPassword);
                teacherExtends.add(teacherExtend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teacherExtends;
    }

    public static List<TeacherExtend> updateTeacherByDoc(MultipartFile file) {
        List<TeacherExtend> teacherExtends = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            List<List<String>> data = parseExcelFile(inputStream);
            if (!data.isEmpty()) {
                data = data.subList(1, data.size());
            }
            for (List<String> row : data) {
                TeacherExtend teacherExtend = new TeacherExtend();
                teacherExtend.setUserName(row.get(0));
                teacherExtend.setGender(row.get(1));
                teacherExtend.setSchoolId(new BigDecimal(row.get(2)).longValue());
                teacherExtend.setIdType(row.get(3));
                teacherExtend.setIdNumber(row.get(4));
                if (row.get(5) != null && !row.get(5).isEmpty()) {
                    teacherExtend.setBirthday(new Date(row.get(5)));
                }
                if (row.get(6) != null && !row.get(6).isEmpty()) {
                    long phone = new BigDecimal(row.get(6)).longValue();
                    teacherExtend.setPhone(Long.toString(phone));
                }
                teacherExtend.setCountry(row.get(7));
                teacherExtend.setNation(row.get(8));
                teacherExtend.setPoliticalRole(row.get(9));
                teacherExtend.setJobTitle(row.get(10));
                if (row.get(11) != null && !row.get(11).isEmpty()) {
                    teacherExtend.setAdmissionTime(new Date(row.get(11)));
                }
                teacherExtend.setEducation(row.get(12));
                teacherExtends.add(teacherExtend);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teacherExtends;
    }

    public static List<Long> deleteUserByDoc(MultipartFile file) {
        List<Long> schoolIds = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            List<List<String>> data = parseExcelFile(inputStream);
            if (!data.isEmpty()) {
                data = data.subList(1, data.size());
            }
            for (List<String> row : data) {
                long schoolId = new BigDecimal(row.get(0)).longValue();
                schoolIds.add(schoolId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return schoolIds;
    }
}
