package com.etoc.model;

public class SysUserGroupUser {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_usergroup_user.id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_usergroup_user.group_id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    private String groupId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_usergroup_user.user_id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    private String userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_usergroup_user.id
     *
     * @return the value of sys_usergroup_user.id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_usergroup_user.id
     *
     * @param id the value for sys_usergroup_user.id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_usergroup_user.group_id
     *
     * @return the value of sys_usergroup_user.group_id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_usergroup_user.group_id
     *
     * @param groupId the value for sys_usergroup_user.group_id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_usergroup_user.user_id
     *
     * @return the value of sys_usergroup_user.user_id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_usergroup_user.user_id
     *
     * @param userId the value for sys_usergroup_user.user_id
     *
     * @mbg.generated Tue Aug 14 20:53:43 CST 2018
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}