package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Desc:
 * User: nathan
 * Date: 2018-03-08
 */
@Data
public class EstimateGasResponse implements Serializable {
    private String gas;
}
