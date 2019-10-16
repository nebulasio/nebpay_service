package io.nebulas.dapp.pay.service;

import io.nebulas.dapp.pay.domain.dao.PayRecordDAO;
import io.nebulas.dapp.pay.domain.model.PayRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Desc:
 * User: nathan
 * Date: 2018-04-26
 */
@Service
public class PayRecordService {
    @Autowired
    private PayRecordDAO payRecordDAO;

    public boolean save(String payId, String txHash) {
        if (StringUtils.isEmpty(payId) || StringUtils.isEmpty(txHash)) {
            return false;
        }
        return payRecordDAO.save(payId, txHash) > 0;
    }

    public PayRecord getRecordByPayId(String payId) {
        if (StringUtils.isEmpty(payId)) {
            return null;
        }
        return payRecordDAO.getByPayId(payId);
    }
}
