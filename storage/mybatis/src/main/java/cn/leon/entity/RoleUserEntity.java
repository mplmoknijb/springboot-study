package cn.leon.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sys_role")
public class RoleUserEntity implements Serializable {
    @Column(name = "id")
    private int id;
    @Column(name = "role")
    private String role;
    @Column(name = "name_n")
    private String nameN;
    @Column(name = "modules")
    private String modules;
    @Column(name = "describe_text")
    private String describeText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getNameN() {
        return nameN;
    }

    public void setNameN(String nameN) {
        this.nameN = nameN;
    }

    public String getDescribeText() {
        return describeText;
    }

    public void setDescribeText(String describeText) {
        this.describeText = describeText;
    }
}