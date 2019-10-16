package io.nebulas.dapp.pay.service.thirdparty.neb.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NebBlockResponse implements Serializable {
    private String hash;
    private String parentHash;
    private Long height;
    private Long nonce;
    private String coinbase;
    private String miner;
    private Long timestamp;
    private Integer chainId;
    private String stateRoot;
    private String txsRoot;
    private String eventsRoot;
    private DposContext dposContext;
    private List<NebTransactionResponse> transactions;

    public List<NebTransactionResponse> getNebTransactionResponses() {
        return CollectionUtils.isEmpty(transactions) ? Collections.EMPTY_LIST : transactions;
    }
}
