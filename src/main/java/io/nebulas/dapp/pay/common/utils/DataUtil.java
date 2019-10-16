package io.nebulas.dapp.pay.common.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * User: nathan
 * Date: 2018-04-26
 */
public class DataUtil {

    public static <T> T selectOne(List<T> list) {

        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

}
