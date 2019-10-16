package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Desc:
 * User: nathan
 * Date: 2018-02-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionReceiptRequest implements Serializable {
    private String hash;
}
