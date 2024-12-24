package com.backend.academicsys.entity;

public class Take_course_time {
    private int turn;
    private String start_time;
    private String end_time;
    public Take_course_time(int turn, String start_time, String end_time){
        this.turn = turn;
        this.start_time = start_time;
        this.end_time = end_time;
    }
    @Override
    public String toString() {
        return "Take_course_time{" +
                "turn=" + turn +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}

