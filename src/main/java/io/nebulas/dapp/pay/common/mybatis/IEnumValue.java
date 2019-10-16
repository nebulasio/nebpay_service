package io.nebulas.dapp.pay.common.mybatis;

/**
 * User: nathan
 * Date: 2018-04-26
 */
public interface IEnumValue {
    /**
     * enum value，only the interface that implements this method can be parsed
     */
    int getValue();

    default String name() {
        return "" + getValue();
    }
}
