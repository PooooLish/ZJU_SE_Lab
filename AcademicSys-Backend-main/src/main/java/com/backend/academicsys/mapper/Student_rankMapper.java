package com.backend.academicsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.backend.academicsys.dto.RankDto;
import com.backend.academicsys.entity.Student_rank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;



@Mapper
public interface Student_rankMapper extends BaseMapper<Student_rank> {
    /**
    * 根据studentId， semester在student_rank中查找学生排名
    */
    @Select("SELECT  period_time, period_grades, period_rank FROM student_rank WHERE school_id = #{studentId} AND sem_or_year = #{sem_or_year} AND period_time = #{period} ")
    RankDto findRanksByStudentIdAndSemester(Long studentId, int sem_or_year, String period);

    /**
     * 根据studentId在student_rank中查找学生排名
     */
    @Select("SELECT  period_time , period_grades, period_rank FROM student_rank WHERE school_id = #{studentId} ")
    List<RankDto> findRanksByStudentId(Long studentId);
}
