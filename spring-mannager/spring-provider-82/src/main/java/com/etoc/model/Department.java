package com.etoc.model;

public class Department {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.id
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column department.departmentName
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    private String departmentname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.id
     *
     * @return the value of department.id
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.id
     *
     * @param id the value for department.id
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column department.departmentName
     *
     * @return the value of department.departmentName
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    public String getDepartmentname() {
        return departmentname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column department.departmentName
     *
     * @param departmentname the value for department.departmentName
     *
     * @mbg.generated Sat Mar 23 13:00:11 CST 2019
     */
    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
}