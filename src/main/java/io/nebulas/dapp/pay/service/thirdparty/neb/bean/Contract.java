package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Desc:
 * User: nathan
 * Date: 2018-03-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract implements Serializable {
    private String source;
    private String sourceType;
    private String function;
    private String args;

    public Contract(String function, String args) {
        this.function = function;
        this.args = args;
    }
}
