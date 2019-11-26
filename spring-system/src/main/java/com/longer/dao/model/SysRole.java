package com.longer.dao.model;

public class SysRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.id
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.role_name
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.system_type
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    private Integer systemType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.description
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.available
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    private String available;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.id
     *
     * @return the value of sys_role.id
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.id
     *
     * @param id the value for sys_role.id
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.role_name
     *
     * @return the value of sys_role.role_name
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.role_name
     *
     * @param roleName the value for sys_role.role_name
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.system_type
     *
     * @return the value of sys_role.system_type
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public Integer getSystemType() {
        return systemType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.system_type
     *
     * @param systemType the value for sys_role.system_type
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public void setSystemType(Integer systemType) {
        this.systemType = systemType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.description
     *
     * @return the value of sys_role.description
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.description
     *
     * @param description the value for sys_role.description
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_role.available
     *
     * @return the value of sys_role.available
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public String getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_role.available
     *
     * @param available the value for sys_role.available
     *
     * @mbg.generated Wed Jun 27 11:18:42 CST 2018
     */
    public void setAvailable(String available) {
        this.available = available;
    }
}