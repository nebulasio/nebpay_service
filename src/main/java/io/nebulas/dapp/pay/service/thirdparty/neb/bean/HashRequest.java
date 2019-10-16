package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Desc:
 * User: nathan
 * Date: 2018-03-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HashRequest implements Serializable {
    private String hash;
}
