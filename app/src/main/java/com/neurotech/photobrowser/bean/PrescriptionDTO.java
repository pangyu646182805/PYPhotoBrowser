package com.neurotech.photobrowser.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhongyang on 2017/9/27.
 * <p>
 * 药单 传输层对象
 */
public class PrescriptionDTO implements Serializable {
    private Prescription prescription;

    private List<PrescriptionSubItem> subItemList;

    private List<PrescriptionFileMap> fileMapList;

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public List<PrescriptionSubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<PrescriptionSubItem> subItemList) {
        this.subItemList = subItemList;
    }

    public List<PrescriptionFileMap> getFileMapList() {
        return fileMapList;
    }

    public void setFileMapList(List<PrescriptionFileMap> fileMapList) {
        this.fileMapList = fileMapList;
    }
}
