package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NebTransactionResponse implements Serializable {
    private String hash;
    private Integer chainId;
    private String from;
    private String to;
    private String value;
    private Long nonce;
    private Long timestamp;
    private String type;
    private String data;
    private String gas_price;
    private String gas_limit;
    private String gas_used;
    private String contract_address;
    private Integer status;
    private String execute_error;
    private String execute_result;
}
