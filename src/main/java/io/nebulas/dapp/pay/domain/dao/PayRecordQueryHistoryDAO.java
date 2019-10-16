package io.nebulas.dapp.pay.domain.dao;

import io.nebulas.dapp.pay.domain.enums.EnvEnum;
import io.nebulas.dapp.pay.domain.mapper.PayRecordQueryHistoryMapper;
import io.nebulas.dapp.pay.domain.model.PayRecordQueryHistory;
import io.nebulas.dapp.pay.domain.model.PayRecordQueryHistoryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Desc:
 * User: nathan
 * Date: 2018-05-09
 */
@Repository
public class PayRecordQueryHistoryDAO {

    @Autowired
    private PayRecordQueryHistoryMapper mapper;

    public void save(EnvEnum env, String payId) {
        PayRecordQueryHistory history = new PayRecordQueryHistory();
        history.setEnv(env.getValue());
        history.setPayId(payId);
        mapper.insertSelective(history);
    }

    public int count(EnvEnum env, String payId, Date from, Date to) {
        PayRecordQueryHistoryCondition cond = new PayRecordQueryHistoryCondition();
        cond.createCriteria().andEnvEqualTo(env.getValue()).andPayIdEqualTo(payId).andCreatedAtBetween(from, to);
        return mapper.countByCondition(cond);
    }
}
