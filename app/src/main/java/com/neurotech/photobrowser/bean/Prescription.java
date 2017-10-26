package com.neurotech.photobrowser.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * 药单表 t_info_prescription
 */
public class Prescription implements Serializable {
    /**
     * 不用传
     */
    private Integer id;

    private Integer patientId;

    private Date beginDate;

    private Date endDate;

    private Integer dayCount;

    /**
     * 药品的数量
     */
    private Integer subCount;

    private Integer fileCount;

    /**
     * 不用传
     */
    private String createTime;

    /**
     * 不用传
     */
    private String updateTime;

    /**
     * 不用传
     */
    private Integer version;

    // 药单编号
    private String number;

    public static int compareToDate(java.sql.Date beginDate, java.sql.Date endDate) {
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        long diff = (end - begin) / (1000L * 3600L * 24L);
        return (int) (diff + 1);
    }

    public static int compareToDate(java.util.Date beginDate, java.util.Date endDate) {
        long begin = beginDate.getTime();
        long end = endDate.getTime();
        long diff = (end - begin) / (1000L * 3600L * 24L);
        return (int) (diff + 1);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}