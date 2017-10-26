package com.neurotech.photobrowser.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;

import com.neurotech.photobrowser.R;
import com.neurotech.photobrowser.bean.Prescription;
import com.neurotech.photobrowser.bean.PrescriptionDTO;
import com.neurotech.photobrowser.bean.PrescriptionFileMap;
import com.neurotech.photobrowser.bean.PrescriptionSubItem;
import com.neurotech.photobrowser.config.Constants;
import com.neurotech.photobrowser.ui.adapter.base.BaseRvAdapter;
import com.neurotech.photobrowser.ui.adapter.base.BaseViewHolder;
import com.neurotech.photobrowser.ui.adapter.base.IMultiItemViewType;
import com.neurotech.photobrowser.ui.widget.DisplayRecyclerView;
import com.neurotech.photobrowser.utils.L;
import com.neurotech.photobrowser.utils.TimeUtils;

import java.util.List;

import me.xiaopan.sketch.SketchImageView;

/**
 * Created by NeuroAndroid on 2017/9/29.
 */

public class PrescriptionsAdapter extends BaseRvAdapter<PrescriptionDTO> {
    private static final int VIEW_TYPE_PURE_PRESCRIPTIONS = 0;
    private static final int VIEW_TYPE_GRAPHIC_PRESCRIPTIONS = 1;

    public PrescriptionsAdapter(Context context, List<PrescriptionDTO> dataList, IMultiItemViewType<PrescriptionDTO> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void convert(BaseViewHolder holder, PrescriptionDTO item, int position, int viewType) {
        if (item != null) {
            holder.getItemView().setOnTouchListener((view, motionEvent) -> {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        L.e(motionEvent.getRawX() + " : " + motionEvent.getY());
                        break;
                }
                return false;
            });
            Prescription prescription = item.getPrescription();
            List<PrescriptionSubItem> subItemList = item.getSubItemList();

            String updateTime = prescription.getUpdateTime();
            boolean sameDay = TimeUtils.isSameDay(updateTime);
            L.e("PrescriptionsAdapter updateTime : " + updateTime);
            if (sameDay) {
                holder.setText(R.id.tv_time, TimeUtils.millis2String(
                        TimeUtils.string2Millis(updateTime, Constants.YYYY_MM_DD_HH_MM_SS_24HOUR), Constants.HH_MM_24HOUR));
            } else {
                holder.setText(R.id.tv_time, TimeUtils.millis2String(
                        TimeUtils.string2Millis(updateTime, Constants.YYYY_MM_DD_HH_MM_SS_24HOUR), "yyyy年M月d日 HH:mm"));
            }

            holder.setText(R.id.tv_prescriptions_number, prescription.getNumber())
                    .setText(R.id.tv_prescriptions_cycle, getMedicalCycleStr(prescription));
            switch (viewType) {
                case VIEW_TYPE_PURE_PRESCRIPTIONS:
                    DisplayRecyclerView rvSubItem = holder.getView(R.id.rv_sub_item);
                    rvSubItem.requestFocus();
                    rvSubItem.setLayoutManager(new LinearLayoutManager(mContext));
                    rvSubItem.setAdapter(new PrescriptionSubItemAdapter(mContext, subItemList, R.layout.item_prescriptions_sub_item));
                    break;
                case VIEW_TYPE_GRAPHIC_PRESCRIPTIONS:
                    List<PrescriptionFileMap> fileMapList = item.getFileMapList();
                    SketchImageView ivImg = holder.getView(R.id.iv_img);
                    String imgUrl = Constants.BASE_URL + "file/download?fileId=" + fileMapList.get(0).getFileId();
                    L.e("PrescriptionsAdapter imgUrl --> " + imgUrl);
                    // ivImg.setImageResource(R.mipmap.ic_launcher);
                    ivImg.displayImage(imgUrl);
                    break;
            }
            holder.setOnClickListener(R.id.ll_efficacy_view, view -> {
            }).setOnClickListener(R.id.ll_efficacy_compare, view -> {
            });
        }
    }

    @Override
    protected IMultiItemViewType<PrescriptionDTO> provideMultiItemViewType() {
        return new IMultiItemViewType<PrescriptionDTO>() {
            @Override
            public int getItemViewType(int position, PrescriptionDTO prescriptionDTO) {
                List<PrescriptionFileMap> fileMapList = prescriptionDTO.getFileMapList();
                if (fileMapList == null || fileMapList.isEmpty()) {
                    return VIEW_TYPE_PURE_PRESCRIPTIONS;
                } else {
                    return VIEW_TYPE_GRAPHIC_PRESCRIPTIONS;
                }
            }

            @Override
            public int getLayoutId(int viewType) {
                switch (viewType) {
                    case VIEW_TYPE_PURE_PRESCRIPTIONS:
                    default:
                        return R.layout.item_pure_prescriptions;
                    case VIEW_TYPE_GRAPHIC_PRESCRIPTIONS:
                        return R.layout.item_graphic_prescriptions;
                }
            }
        };
    }

    /**
     * 得到服药周期字符串
     */
    private String getMedicalCycleStr(Prescription prescription) {
        String beginDate = TimeUtils.date2String(prescription.getBeginDate(), Constants.M_D);
        String endDate = TimeUtils.date2String(prescription.getEndDate(), Constants.M_D);
        return beginDate + "-" + endDate;
    }
}
