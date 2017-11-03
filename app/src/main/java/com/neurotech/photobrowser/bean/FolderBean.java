package com.neurotech.photobrowser.bean;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/11/2.
 */

public class FolderBean {
    private String folderName;
    private List<FileBean> fileList;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<FileBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileBean> fileList) {
        this.fileList = fileList;
    }

    @Override
    public boolean equals(Object obj) {
        FolderBean folderBean = (FolderBean) obj;
        return this.folderName.equals(folderBean.getFolderName()) || super.equals(obj);
    }
}
