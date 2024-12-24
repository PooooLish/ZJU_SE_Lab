package com.backend.academicsys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.backend.academicsys.dto.CourseNameAndSemester;
import com.backend.academicsys.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Delete("DELETE FROM course")
    void deleteAllCourses();
    
    /**
     * 根据课程ID列表和学期列表查询对应的课程ID列表
     *
     * @param courseIds 课程ID列表
     * @param semesters 学期列表
     * @return 符合条件的课程列表
     */
    @Select("<script>" +
            "SELECT * " +
            "FROM course " +
            "WHERE course_id IN " +
            "<foreach item='id' collection='courseIds' open='(' separator=',' close=')'>#{id}</foreach> " +
            "AND semester IN " +
            "<foreach item='semester' collection='semesters' open='(' separator=',' close=')'>#{semester}</foreach> " +
            "</script>")
    List<Course> findCoursesByCourseIdsAndSemesters(@Param("courseIds") List<Long> courseIds, @Param("semesters") List<String> semesters);

    @Insert("INSERT INTO course (course_id, course_name, credit, course_department, category, schedule, location, semester, max_students, num_students, test_time, grade_cal, target, description) " +
            "VALUES (#{course_id}, #{course_name}, #{credit}, #{course_department}, #{category}, #{schedule}, #{location}, #{semester}, #{max_students}, #{num_students}, #{test_time}, #{grade_cal}, #{target}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "course_id")
    @Override
    int insert(Course course);

    /*
     * 根据course name找courseId
     * course name可以接受正则查找
     */
    @Select("SELECT course_id FROM course WHERE course_name LIKE CONCAT('%', #{courseName}, '%')")
    Long findCourseByFuzzyCourseName(String courseName);

    /**
     * 根据课程course_id查询课程名称和学期
     */
    @Select("SELECT course_name, semester FROM course WHERE course_id = #{courseId}")
    CourseNameAndSemester findCourseNameAndSemesterByCourseId(Long courseId);

    @Select({
        "<script>",
        "SELECT DISTINCT c.*",
        "FROM course c",
        "LEFT JOIN course_teacher ct ON c.course_id = ct.course_id AND ct.ta_or_tc = 0",
        "LEFT JOIN user u ON ct.school_id = u.school_id",
        "<where>",
        "<if test='condition.courseId != null'>",
        "AND c.course_id = #{condition.courseId}",
        "</if>",
        "<if test='condition.courseName != null'>",
        "AND c.course_name LIKE CONCAT('%', #{condition.courseName}, '%')",
        "</if>",
        "<if test='condition.courseDepartment != null'>",
        "AND c.course_department = #{condition.courseDepartment}",
        "</if>",
        "<if test='condition.category != null'>",
        "AND c.category = #{condition.category}",
        "</if>",
        "<if test='condition.semester != null'>",
        "AND c.semester = #{condition.semester}",
        "</if>",
        "<if test='condition.teacherId != null'>",
        "AND ct.school_id = #{condition.teacherId}",
        "</if>",
        "<if test='condition.teacherName != null'>",
        "AND u.user_name LIKE CONCAT('%', #{condition.teacherName}, '%')",
        "</if>",
        "</where>",
        "</script>"
    })
    List<Course> findByCondition(@Param("condition") Map<String, Object> condition);

    @Select("SELECT * FROM course")
    List<Course> findAll();

    @Select("SELECT * FROM course WHERE course_id=#{course_id}")
    Course findById(int course_id);

    @Delete("DELETE FROM course WHERE course_id=#{course_id}")
    boolean deleteById(int course_id);

    @Insert("INSERT INTO course " +
            "(course_id, course_name, credit, course_department, category, " +
            "course_start_time, course_end_time, location, semester, max_students, num_students, " +
            "test_start_time, test_end_time, grade_cal, target, description) VALUES " +
            "(#{courseId}, #{courseName}, #{credit}, #{courseDepartment}, #{category}, " +
            "#{courseStartTime}, #{courseEndTime}, #{location}, #{semester}, #{maxStudents}, #{numStudents}, " +
            "#{testStartTime}, #{testEndTime}, #{gradeCal}, #{target}, #{description})")
    boolean createCourse(Course course);

    @Update("UPDATE course SET " +
            "course_name=#{courseName}, credit=#{credit}, course_department=#{courseDepartment}, category=#{category}, " +
            "course_start_time=#{courseStartTime}, course_end_time=#{courseEndTime}, location=#{location}, semester=#{semester}, " +
            "max_students=#{maxStudents}, num_students=#{numStudents}, test_start_time=#{testStartTime}, test_end_time=#{testEndTime}, " +
            "grade_cal=#{gradeCal}, target=#{target}, description=#{description} " +
            "WHERE course_id=#{courseId}")
    boolean modCourse(Course course);

    @Select("select * from course where course_id = #{course_id}")
    Course getCourseById(int course_id);
    
    
    @Select("select * from course ")
    public List<Course> selectAll_Course();
    @Select("select * from course where course_id = #{course_id}")
    public Course selectId_course(int course_id);
    @Select("select * from course where course_name = #{course_name}")
    public List<Course> selectName_course(String course_name);
    @Select("select * from course where credit = #{credit}")
    public List<Course> selectCredit_course(float credit);
    @Select("select * from course where course_department = #{course_department}")
    public List<Course> selectDepartment_course(String course_department);
    @Select("select * from course where category = #{category}")
    public List<Course> selectCategory_course(String category);
    @Select("select * from course where semester = #{semester}")
    public List<Course> selectSemester_course(String semester);
    @Select("select * from course where location = #{location}")
    public List<Course> selectLocation_course(String location);
    @Select("select * from course where target = #{target}")
    public List<Course> selectTarget_course(String target);

    @Select("SELECT * FROM course WHERE course_id = #{id}")
    Course selectCourseById(int id);

    @Update(
            "UPDATE course " +
            "SET num_students = #{num}"+
            " where course_id = #{id}"
            )
    int set_num_student(int id, int num);

}
