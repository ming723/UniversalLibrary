package com.abner.ming.abnerlibrary.net.test;

import com.abner.ming.abnerlibrary.net.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengjin on 17/8/24.
 */

public class WeekNewsBean implements IModel{
    private List<ListBean> datas = new ArrayList<>();

    public List<ListBean> getDatas() {
        return datas;
    }

    public void setDatas(List<ListBean> datas) {
        this.datas = datas;
    }

    public class ListBean {
        private String week_desc;
        private String week_link;
        private String week_title;
        private String week_img;
        private String week_time;
        private String week_way;

        public String getWeek_way() {
            return week_way;
        }

        public void setWeek_way(String week_way) {
            this.week_way = week_way;
        }

        public String getWeek_desc() {
            return week_desc;
        }

        public void setWeek_desc(String week_desc) {
            this.week_desc = week_desc;
        }

        public String getWeek_link() {
            return week_link;
        }

        public void setWeek_link(String week_link) {
            this.week_link = week_link;
        }

        public String getWeek_title() {
            return week_title;
        }

        public void setWeek_title(String week_title) {
            this.week_title = week_title;
        }

        public String getWeek_img() {
            return week_img;
        }

        public void setWeek_img(String week_img) {
            this.week_img = week_img;
        }

        public String getWeek_time() {
            return week_time;
        }

        public void setWeek_time(String week_time) {
            this.week_time = week_time;
        }
    }

}
