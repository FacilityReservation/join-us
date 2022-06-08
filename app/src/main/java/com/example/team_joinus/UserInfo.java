package com.example.team_joinus;
//same as Post java class in lecture

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("mem_p")
    private String mem_p;           // 회원 기본키
    @SerializedName("mem_id")
    private String mem_id;          // 회원 아이디
    @SerializedName("mem_pw")
    private String mem_pw;          // 회원 비밀번호
    @SerializedName("mem_name")
    private String mem_name;        // 회원 이름
    @SerializedName("mem_company")
    private String mem_company;     // 기업상호명 ( 기업 회원일때만 상호명 입력 )
    @SerializedName("mem_type")
    private String mem_type;        // 회원유형 ( 1:관리자 2:일반회원 3:기업회원 )

    public UserInfo(String mem_p, String mem_id, String mem_pw, String mem_name, String mem_company, String mem_type) {
        this.mem_p = mem_p;
        this.mem_id = mem_id;
        this.mem_pw = mem_pw;
        this.mem_name = mem_name;
        this.mem_company = mem_company;
        this.mem_type = mem_type;
    }

    public void setP(String p) {
        mem_p = p;
    }

    public String getP() {
        return this.mem_p;
    }

    public void setID(String id) {
        mem_id = id;
    }

    public String getID() {
        return this.mem_id;
    }

    public void setPW(String pw) {
        mem_pw = pw;
    }

    public String getPW() {
        return this.mem_pw;
    }

    public void setNAME(String name) {
        mem_name = name;
    }

    public String getNAME() {
        return this.mem_name;
    }

    public void setCOMPANY(String company) {
        mem_company = company;
    }

    public String getCOMPANY() {
        return this.mem_company;
    }

    public void setTYPE(String type) {
        mem_type = type;
    }

    public String getTYPE() {
        return this.mem_type;
    }
}