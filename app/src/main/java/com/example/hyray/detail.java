package com.example.hyray;

public class detail {

    private String EmpName,EmpCode,EmpActive;

    public detail(String empName, String empCode, String empActive) {
        this.EmpName=empName;
        this.EmpCode=empCode;
        this.EmpActive=empActive;
    }

    public String getEmpName() {
        return EmpName;
    }


    public String getEmpCode() {
        return EmpCode;
    }


    public String getEmpActive() {
        return EmpActive;
    }


}
