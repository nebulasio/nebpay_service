package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc:
 * User: nathan
 * Date: 2018-03-26
 */
@Getter
@Setter
public class NebResponse<T> {
    private String error;
    private T result;
}
