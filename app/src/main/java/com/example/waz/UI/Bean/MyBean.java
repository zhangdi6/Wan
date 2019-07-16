package com.example.waz.UI.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MyBean {
    @Id(autoincrement = true)
    private Long lid;
    private String authorName;
    private String title;
    private String desc;
    private String img;
    private String niceDate;
    private String superName;
    private String link;
    private boolean isChecked;
    @Generated(hash = 1862996391)
    public MyBean(Long lid, String authorName, String title, String desc,
            String img, String niceDate, String superName, String link,
            boolean isChecked) {
        this.lid = lid;
        this.authorName = authorName;
        this.title = title;
        this.desc = desc;
        this.img = img;
        this.niceDate = niceDate;
        this.superName = superName;
        this.link = link;
        this.isChecked = isChecked;
    }
    @Generated(hash = 1281580447)
    public MyBean() {
    }
    public Long getLid() {
        return this.lid;
    }
    public void setLid(Long lid) {
        this.lid = lid;
    }
    public String getAuthorName() {
        return this.authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getNiceDate() {
        return this.niceDate;
    }
    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }
    public String getSuperName() {
        return this.superName;
    }
    public void setSuperName(String superName) {
        this.superName = superName;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public boolean getIsChecked() {
        return this.isChecked;
    }
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    
}
