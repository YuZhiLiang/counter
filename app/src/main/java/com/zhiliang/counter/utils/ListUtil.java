package com.zhiliang.counter.utils;

import java.util.List;

/**
 * Created by 04816381 on 2018-04-19.
 */

public class ListUtil {
    public static int getSize(List list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        return list.size();
    }
}
