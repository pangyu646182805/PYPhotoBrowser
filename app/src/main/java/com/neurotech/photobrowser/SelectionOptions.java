package com.neurotech.photobrowser;

import com.neurotech.photobrowser.utils.MimeType;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public class SelectionOptions {
    @MimeType.MediaMimeType
    public int mimeType;  // 媒体库mime类型
    public int maxSelectable = 1;  // 最多选择数量
    public int gridSize = 3;  // RecyclerView显示网格大小

    public ViewHolderCreator viewHolderCreator;

    private SelectionOptions() {
    }

    public static SelectionOptions getOptions() {
        return SelectionOptionsHolder.OPTIONS;
    }

    public static SelectionOptions getCleanOptions() {
        SelectionOptions options = SelectionOptionsHolder.OPTIONS;
        options.reset();
        return options;
    }

    private void reset() {
        mimeType = MimeType.PHOTO;
        maxSelectable = 1;
        gridSize = 3;
    }

    private static class SelectionOptionsHolder {
        private static final SelectionOptions OPTIONS = new SelectionOptions();
    }
}
