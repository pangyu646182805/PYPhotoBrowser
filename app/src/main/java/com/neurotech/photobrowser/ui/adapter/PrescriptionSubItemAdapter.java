package com.neurotech.photobrowser.ui.adapter;

import android.content.Context;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.bean.PrescriptionSubItem;
import com.neurotech.photobrowser.ui.adapter.base.BaseRvAdapter;
import com.neurotech.photobrowser.ui.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/9/29.
 */

public class PrescriptionSubItemAdapter extends BaseRvAdapter<PrescriptionSubItem> {
    public PrescriptionSubItemAdapter(Context context, List<PrescriptionSubItem> dataList, int layoutId) {
        super(context, dataList, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, PrescriptionSubItem item, int position, int viewType) {
        if (item != null) {
            holder.setText(R.id.tv_drug_name, item.getDrugName())
                    .setText(R.id.tv_morning, item.getMorning())
                    .setText(R.id.tv_afternoon, item.getAfternoon())
                    .setText(R.id.tv_evening, item.getEvening());
        }
    }
}
