package com.neurotech.photobrowser.bean;

import java.io.Serializable;

/**
 * 药单文件映射表 t_info_prescription_file_map
 */
public class PrescriptionFileMap implements Serializable {
    /**
     * 不用传
     */
    private Integer id;

    /**
     * 添加药单不用传
     */
    private Integer prescriptionId;

    /**
     * 添加药单不用传
     */
    private Integer fileId;

    /**
     * 本地图像path
     */
    private String localPath;

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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}