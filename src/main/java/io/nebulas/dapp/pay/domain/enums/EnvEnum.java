package io.nebulas.dapp.pay.domain.enums;

/**
 * Desc:
 * User: nathan
 * Date: 2018-05-09
 */
public enum EnvEnum {
    MAIN_NET("mainnet"),
    TEST_NET("testnet");

    private String value;

    EnvEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
