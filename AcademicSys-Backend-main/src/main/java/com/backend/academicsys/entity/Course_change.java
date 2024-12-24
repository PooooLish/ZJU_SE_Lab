package com.backend.academicsys.entity;

import org.w3c.dom.Text;

import java.util.Date;

public class Course_change {
    private int change_id;
    private Long student_id;
    private int course_id;
    private String change_type;
    private Date change_date;
    private String change_reason;
    private String previous_status;
    private String new_status;
    private Long approved_by;
    private Date approval_date;
    private String record_status;

    @Override
    public String toString() {
        return "Course_change{" +
                "change_id=" + change_id +
                ", student_id=" + student_id +
                ", course_id=" + course_id +
                ", change_type='" + change_type + '\'' +
                ", change_date=" + change_date +
                ", change_reason=" + change_reason +
                ", previous_status='" + previous_status + '\'' +
                ", new_status='" + new_status + '\'' +
                ", approved_by=" + approved_by +
                ", approval_date=" + approval_date +
                ", record_status='" + record_status + '\'' +
                '}';
    }
    ;

    public Course_change(int change_id, int course_id, Long student_id,  Date change_date,String change_type,  String change_reason, String previous_status, String new_status, Long approved_by, Date approval_date, String record_status) {
        this.change_id = change_id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.change_type = change_type;
        this.change_date = change_date;
        this.change_reason = change_reason;
        this.previous_status = previous_status;
        this.new_status = new_status;
        this.approved_by = approved_by;
        this.approval_date = approval_date;
        this.record_status = record_status;
    }

    public Course_change(int course_id, Long student_id,  Date change_date,String change_type,  String change_reason, String previous_status, String new_status, Long approved_by, Date approval_date, String record_status) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.change_type = change_type;
        this.change_date = change_date;
        this.change_reason = change_reason;
        this.previous_status = previous_status;
        this.new_status = new_status;
        this.approved_by = approved_by;
        this.approval_date = approval_date;
        this.record_status = record_status;
    }

    public int getChange_id() {
        return change_id;
    }

    public void setChange_id(int change_id) {
        this.change_id = change_id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getChange_type() {
        return change_type;
    }

    public void setChange_type(String change_type) {
        this.change_type = change_type;
    }

    public Date getChange_date() {
        return change_date;
    }

    public void setChange_date(Date change_date) {
        this.change_date = change_date;
    }

    public String getChange_reason() {
        return change_reason;
    }

    public void setChange_reason(String change_reason) {
        this.change_reason = change_reason;
    }

    public String getPrevious_status() {
        return previous_status;
    }

    public void setPrevious_status(String previous_status) {
        this.previous_status = previous_status;
    }

    public String getNew_status() {
        return new_status;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }

    public Long getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(Long approved_by) {
        this.approved_by = approved_by;
    }

    public Date getApproval_date() {
        return approval_date;
    }

    public void setApproval_date(Date approval_date) {
        this.approval_date = approval_date;
    }

    public String getRecord_status() {
        return record_status;
    }

    public void setRecord_status(String record_status) {
        this.record_status = record_status;
    }
}
