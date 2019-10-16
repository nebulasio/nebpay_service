package io.nebulas.dapp.pay.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * dapp_pay_center.pay_record_query_history  查询历史
 *
 * @author hainan.wang
 * @date 2018-5-9
 *
 */
public class PayRecordQueryHistory implements Serializable {
    /**  */
    private Integer id;

    /**  */
    private String env;

    /**  */
    private String payId;

    /**  */
    private Date createdAt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}