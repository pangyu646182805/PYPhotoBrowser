package com.neurotech.photobrowser.loader;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/10/22.
 */

public class AlbumCategoryComparator implements Comparator<Long> {
    @Override
    public int compare(Long l1, Long l2) {
        return l2.compareTo(l1);
    }
}
