package com.neurotech.photobrowser.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 药单子项表 t_info_prescription_sub_item
 */
public class PrescriptionSubItem implements Serializable {
    /**
     * 不用传
     */
    private Integer id;

    /**
     * 添加药单不用传
     */
    private Integer prescriptionId;

    private Integer drugId;

    private String drugName;

    /**
     * 规格 先不用传
     */
    private String standard;

    /**
     * 传12mg
     */
    private String morning = "0mg";

    private String afternoon = "0mg";

    private String evening = "0mg";

    /**
     * 不用传
     */
    private Timestamp createTime;

    /**
     * 不用传
     */
    private Timestamp updateTime;

    /**
     * 不用传
     */
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName == null ? null : drugName.trim();
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning == null ? null : morning.trim();
    }

    public String getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon == null ? null : afternoon.trim();
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening == null ? null : evening.trim();
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}