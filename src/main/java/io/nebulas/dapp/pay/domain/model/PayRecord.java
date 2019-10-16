package io.nebulas.dapp.pay.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * dapp_pay_center.pay_record  支付记录
 *
 * @author hainan.wang
 * @date 2018-4-26
 *
 */
public class PayRecord implements Serializable {
    /**  */
    private Long id;

    /** 支付id */
    private String payId;

    /** tx hash */
    private String txHash;

    /** 创建时间 */
    private Date createdAt;

    /** 更新时间 */
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash == null ? null : txHash.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}