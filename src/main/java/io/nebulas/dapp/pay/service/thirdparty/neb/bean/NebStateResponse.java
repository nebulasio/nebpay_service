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
public class NebStateResponse implements Serializable {
    private Integer chainId;
    private String tail;
    private String coinbase;
    private Integer peerCount;
    private Boolean isMining;
    private String protocolVersion;
    private Boolean sync;
    private String version;
}
