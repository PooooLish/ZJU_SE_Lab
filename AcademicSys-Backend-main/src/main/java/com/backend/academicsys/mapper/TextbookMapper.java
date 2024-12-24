package com.backend.academicsys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.backend.academicsys.entity.Textbook;

@Mapper
public interface TextbookMapper {

    @Select({
        "<script>",
        "SELECT * FROM textbook",
        "<where>",
        "<if test='condition.textbookId != null'>",
        "AND textbook_id = #{condition.textbookId}",
        "</if>",
        "<if test='condition.bookTitle != null'>",
        "AND book_title LIKE CONCAT('%', #{condition.bookTitle}, '%')",
        "</if>",
        "<if test='condition.author != null'>",
        "AND author LIKE CONCAT('%', #{condition.author}, '%')",
        "</if>",
        "<if test='condition.isbn != null'>",
        "AND isbn = #{condition.isbn}",
        "</if>",
        "<if test='condition.courseId != null'>",
        "AND course_id = #{condition.courseId}",
        "</if>",
        "</where>",
        "</script>"
    })
    List<Textbook> findByCondition(@Param("condition") Map<String, Object> condition);

    @Select("SELECT * FROM textbook")
    List<Textbook> findAll();

    @Select("SELECT * FROM textbook WHERE textbook_id=#{textbookId}")
    Textbook findById(int textbookId);

    @Delete("DELETE FROM textbook WHERE textbook_id=#{textbookId}")
    boolean deleteById(int textbookId);

    @Insert("INSERT INTO textbook VALUES " +
            "(#{textbookId}, #{bookTitle}, #{author}, #{edition},  #{isbn}, " +
            "#{publisher}, #{publicationYear}, #{courseId}, #{price}, #{bookDescription}, " +
            "#{bookPhoto}, #{stock}, #{booked})")
    boolean createTextbook(Textbook t);

    @Update("UPDATE textbook SET " +
            "book_title=#{bookTitle}, author=#{author}, edition=#{edition}, isbn=#{isbn}, " +
            "publisher=#{publisher}, publication_year=#{publicationYear}, course_id=#{courseId}, price=#{price}, " +
            "book_description=#{bookDescription}, book_photo=#{bookPhoto}, stock=#{stock}, booked=#{booked} " +
            "WHERE textbook_id=#{textbookId}")
    boolean modTextbook(Textbook t);
}