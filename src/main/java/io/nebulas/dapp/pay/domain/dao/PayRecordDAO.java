package io.nebulas.dapp.pay.domain.dao;

import io.nebulas.dapp.pay.common.utils.DataUtil;
import io.nebulas.dapp.pay.domain.mapper.PayRecordMapper;
import io.nebulas.dapp.pay.domain.model.PayRecord;
import io.nebulas.dapp.pay.domain.model.PayRecordCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Desc:
 * User: nathan
 * Date: 2018-04-26
 */
@Repository
public class PayRecordDAO {

    @Autowired
    private PayRecordMapper mapper;

    public int save(String payId, String txHash) {
        PayRecord record = new PayRecord();
        record.setPayId(payId);
        record.setTxHash(txHash);
        return mapper.insertSelective(record);
    }

    public PayRecord getByPayId(String payId) {
        PayRecordCondition cond = new PayRecordCondition();
        cond.createCriteria().andPayIdEqualTo(payId);
        return DataUtil.selectOne(mapper.selectByCondition(cond));
    }
}

