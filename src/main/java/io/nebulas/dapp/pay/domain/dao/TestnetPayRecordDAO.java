package io.nebulas.dapp.pay.domain.dao;

import io.nebulas.dapp.pay.common.utils.DataUtil;
import io.nebulas.dapp.pay.domain.mapper.TestnetPayRecordMapper;
import io.nebulas.dapp.pay.domain.model.TestnetPayRecord;
import io.nebulas.dapp.pay.domain.model.TestnetPayRecordCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Desc:
 * User: nathan
 * Date: 2018-04-26
 */
@Repository
public class TestnetPayRecordDAO {

    @Autowired
    private TestnetPayRecordMapper mapper;

    public int save(String payId, String txHash) {
        TestnetPayRecord record = new TestnetPayRecord();
        record.setPayId(payId);
        record.setTxHash(txHash);
        return mapper.insertSelective(record);
    }

    public TestnetPayRecord getByPayId(String payId) {
        TestnetPayRecordCondition cond = new TestnetPayRecordCondition();
        cond.createCriteria().andPayIdEqualTo(payId);
        return DataUtil.selectOne(mapper.selectByCondition(cond));
    }
}

