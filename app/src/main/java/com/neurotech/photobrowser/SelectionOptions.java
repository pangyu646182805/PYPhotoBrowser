package com.neurotech.photobrowser;

import com.neurotech.photobrowser.utils.MimeType;
import com.neurotech.photobrowser.utils.SelectMode;

/**
 * Created by NeuroAndroid on 2017/11/1.
 */

public class SelectionOptions {
    @MimeType.MediaMimeType
    public int mimeType;  // 媒体库mime类型

    @SelectMode.MediaSelectMode
    public int selectMode;  // 单选or多选模式

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
        selectMode = SelectMode.MULTIPLE_MODE;
    }

    private static class SelectionOptionsHolder {
        private static final SelectionOptions OPTIONS = new SelectionOptions();
    }
}
