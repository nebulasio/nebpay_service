package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Desc:
 * User: nathan
 * Date: 2018-03-08
 */
@Data
public class SendTransactionRequest implements Serializable {
    private String from;
    private String to;
    private String value;
    private Long nonce;
    private String gas_price;
    private String gas_limit;
    private Contract contract;
}
