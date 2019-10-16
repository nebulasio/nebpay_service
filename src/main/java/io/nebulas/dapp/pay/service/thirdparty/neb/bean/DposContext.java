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
public class DposContext implements Serializable {
    private String dynastyRoot;
    private String nextDynastyRoot;
    private String delegateRoot;
    private String candidateRoot;
    private String voteRoot;
    private String mintCntRoot;
}
