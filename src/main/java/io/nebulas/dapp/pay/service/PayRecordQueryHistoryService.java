package io.nebulas.dapp.pay.service;

import io.nebulas.dapp.pay.domain.dao.PayRecordQueryHistoryDAO;
import io.nebulas.dapp.pay.domain.enums.EnvEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Desc:
 * User: nathan
 * Date: 2018-05-09
 */
@Service
public class PayRecordQueryHistoryService {

    @Autowired
    private PayRecordQueryHistoryDAO payRecordQueryHistoryDAO;

    public void save(EnvEnum env, String payId) {
        payRecordQueryHistoryDAO.save(env, payId);
    }

    public int count(EnvEnum env, String payId, Date from, Date to) {
        return payRecordQueryHistoryDAO.count(env, payId, from, to);
    }
}
