package com.neurotech.basecore.model.response;

import com.neurotech.basecore.bean.PrescriptionDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/9/29.
 */

public class PrescriptionsResponse implements Serializable {
    /**
     * totalRows : 4
     * rows : 4
     * page : 0
     * data : [{"fileMapList":[{"fileId":7,"id":3,"prescriptionId":4},{"fileId":8,"id":4,"prescriptionId":4},{"fileId":9,"id":5,"prescriptionId":4}],"prescription":{"beginDate":"2017-09-29","createTime":"2017-09-29 07:25:26","dayCount":2,"endDate":"2017-09-30","fileCount":3,"id":4,"number":"170929072526","patientId":100000161,"subCount":4,"updateTime":"2017-09-29 07:25:26","version":0},"subItemList":[{"afternoon":"4mg","createTime":"2017-09-29 07:25:26","drugId":4,"drugName":"左乙拉西坦片","evening":"5mg","id":6,"morning":"4mg","prescriptionId":4,"standard":"","updateTime":"2017-09-29 07:25:26","version":0},{"afternoon":"6mg","createTime":"2017-09-29 07:25:26","drugId":7,"drugName":"卡马西平片","evening":"3mg","id":7,"morning":"6mg","prescriptionId":4,"standard":"","updateTime":"2017-09-29 07:25:26","version":0},{"afternoon":"7mg","createTime":"2017-09-29 07:25:26","drugId":4,"drugName":"左乙拉西坦片","evening":"8mg","id":8,"morning":"5mg","prescriptionId":4,"standard":"","updateTime":"2017-09-29 07:25:26","version":0},{"afternoon":"7mg","createTime":"2017-09-29 07:25:26","drugId":8,"drugName":"加巴喷丁胶囊","evening":"8mg","id":9,"morning":"6mg","prescriptionId":4,"standard":"","updateTime":"2017-09-29 07:25:26","version":0}]},{"fileMapList":[{"fileId":4,"id":1,"prescriptionId":3},{"fileId":5,"id":2,"prescriptionId":3}],"prescription":{"beginDate":"2017-09-28","createTime":"2017-09-28 19:03:58","dayCount":2,"endDate":"2017-09-29","fileCount":2,"id":3,"number":"170928190357","patientId":100000161,"subCount":1,"updateTime":"2017-09-28 19:03:58","version":0},"subItemList":[{"afternoon":"0mg","createTime":"2017-09-28 19:03:58","drugId":5,"drugName":"奥卡西平片","evening":"0mg","id":5,"morning":"0mg","prescriptionId":3,"standard":"","updateTime":"2017-09-28 19:03:58","version":0}]},{"fileMapList":[],"prescription":{"beginDate":"2017-09-28","createTime":"2017-09-28 18:36:18","dayCount":3,"endDate":"2017-09-30","fileCount":0,"id":2,"number":"170928183618","patientId":100000161,"subCount":3,"updateTime":"2017-09-28 18:36:18","version":0},"subItemList":[{"afternoon":"0mg","createTime":"2017-09-28 18:36:18","drugId":5,"drugName":"奥卡西平片","evening":"0mg","id":2,"morning":"0mg","prescriptionId":2,"standard":"","updateTime":"2017-09-28 18:36:18","version":0},{"afternoon":"0mg","createTime":"2017-09-28 18:36:18","drugId":5,"drugName":"奥卡西平片","evening":"0mg","id":3,"morning":"0mg","prescriptionId":2,"standard":"","updateTime":"2017-09-28 18:36:18","version":0},{"afternoon":"0mg","createTime":"2017-09-28 18:36:18","drugId":5,"drugName":"奥卡西平片","evening":"0mg","id":4,"morning":"0mg","prescriptionId":2,"standard":"","updateTime":"2017-09-28 18:36:18","version":0}]},{"fileMapList":[],"prescription":{"beginDate":"2017-09-28","createTime":"2017-09-28 18:35:03","dayCount":3,"endDate":"2017-09-30","fileCount":0,"id":1,"number":"170928183503","patientId":100000161,"subCount":1,"updateTime":"2017-09-28 18:35:03","version":0},"subItemList":[{"afternoon":"0mg","createTime":"2017-09-28 18:35:03","drugId":5,"drugName":"奥卡西平片","evening":"0mg","id":1,"morning":"0mg","prescriptionId":1,"standard":"","updateTime":"2017-09-28 18:35:03","version":0}]}]
     */
    private int totalRows;
    private int rows;
    private int page;
    private List<PrescriptionDTO> data;

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<PrescriptionDTO> getData() {
        return data;
    }

    public void setData(List<PrescriptionDTO> data) {
        this.data = data;
    }
}
