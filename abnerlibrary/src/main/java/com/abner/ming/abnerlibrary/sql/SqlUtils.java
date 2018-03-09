package com.abner.ming.abnerlibrary.sql;

import java.util.List;

/**
 * Created by AbnerMing on 2018/3/8.
 * 数据库操作类
 * 增删改查
 */

public class SqlUtils {
    private UserInFoDao dao;
    public void init(){
        dao= new GreenDaoManager().getDaoSession().getUserInFoDao();

    }

    private void query(){
        List<UserInFo> usersList = dao.queryBuilder().build().list();
        UserInFo info = dao.queryBuilder().where(UserInFoDao.Properties.Id.eq(0)).build().unique();
    }

    private void insert(){
        UserInFo info = new UserInFo(1, "");
        dao.insert(info);
    }

    private void delete(){
        UserInFo infos = dao.queryBuilder().where(UserInFoDao.Properties.Id.eq(0)).build().unique();
        dao.delete(infos);
        //删除多个
        List<UserInFo> userList = dao.queryBuilder().where(UserInFoDao.Properties.Name.eq("")).build().list();
        for (UserInFo info : userList) {
            dao.delete(info);
        }
    }

    private void change(){
        String id = "";
        UserInFo info = dao.queryBuilder().where(UserInFoDao.Properties.Id.eq(id)).build().unique();
        info.setName(info.getName() + "update");
        dao.update(info);
    }
}
