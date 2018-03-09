package com.abner.ming.abnerlibrary.sql;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/3/8.
 */

@Entity
public class UserInFo extends SqlBean{
    @Id
    private int id;
    private String name;
    @Generated(hash = 135339640)
    public UserInFo(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 480046334)
    public UserInFo() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
