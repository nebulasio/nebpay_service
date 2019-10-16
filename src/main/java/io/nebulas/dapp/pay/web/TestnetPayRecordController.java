package io.nebulas.dapp.pay.web;

import io.nebulas.dapp.pay.common.bean.JsonResult;
import io.nebulas.dapp.pay.common.bean.Tuple;
import io.nebulas.dapp.pay.domain.enums.EnvEnum;
import io.nebulas.dapp.pay.domain.model.TestnetPayRecord;
import io.nebulas.dapp.pay.service.PayRecordQueryHistoryService;
import io.nebulas.dapp.pay.service.TestnetPayRecordService;
import io.nebulas.dapp.pay.service.thirdparty.neb.TestnetNebApiServiceWrapper;
import io.nebulas.dapp.pay.service.thirdparty.neb.bean.NebTransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Desc:
 * User: nathan
 * Date: 2018-04-25
 */
@Slf4j
@RequestMapping({"/api/testnet/pay", "/api/pay"})
@RestController
public class TestnetPayRecordController {

    @Autowired
    private TestnetPayRecordService payRecordService;
    @Autowired
    private TestnetNebApiServiceWrapper nebApiServiceWrapper;
    @Autowired
    private PayRecordQueryHistoryService payRecordQueryHistoryService;
//    @Autowired
//    private RedisService redisService;

    @PostMapping("")
    public JsonResult save(@RequestParam("payId") String payId, @RequestParam("txHash") String txHash) {
        log.info("save payId={} txHash={}", payId, txHash);
        TestnetPayRecord record = payRecordService.getRecordByPayId(payId);
        if (null != record) {
            return JsonResult.failed("payId already exist");
        }
        payRecordService.save(payId, txHash);
        return JsonResult.success();
    }


    @GetMapping("/query")
    public JsonResult query(@RequestParam("payId") String payId) {
        log.info("query payId={}", payId);

        payRecordQueryHistoryService.save(EnvEnum.TEST_NET, payId);

        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.plusMinutes(-1);

        int count = payRecordQueryHistoryService.count(EnvEnum.TEST_NET, payId, from.toDate(), to.toDate());
        if (count > 20) {
            log.warn("payId={} queried over 20 times, current={}", payId, count);
            return JsonResult.failed("you are queried more than 20 times per minute, please wait");
        }

//        String key = String.format("testnet:payId:%s:%s", payId, LocalDateTime.now().toString("yyyyMMddHHmm"));
//        long r = redisService.incrAndExpired(key, 2, TimeUnit.MINUTES);
//        if (r > 6) {
//            log.warn("payId={} queried over 6 times", payId);
//            return JsonResult.failed("you are queried more than 6 times per minute, please wait");
//        }

        TestnetPayRecord record = payRecordService.getRecordByPayId(payId);
        if (null == record) {
            return JsonResult.failed("payId " + payId + " does not exist");
        }

        String txHash = record.getTxHash();
//        NebTransactionResponse txResponse = nebApiServiceWrapper.getTransactionReceipt(txHash);
//        if (null == txResponse) {
//            return JsonResult.failed("payId " + payId + " get transaction error");
//        }
//        return JsonResult.success(txResponse);
        Tuple.Tuple3<Boolean, NebTransactionResponse, String> txResponse = nebApiServiceWrapper.getTransactionReceipt(txHash);
        if (!txResponse.first()) {
            String errorMsg = "payId " + payId + " get transaction error. ";
            if (StringUtils.isNotEmpty(txResponse.third())) {
                errorMsg += txResponse.third();
            }
            return JsonResult.failed(errorMsg);
        }
        return JsonResult.success(txResponse.second());
    }
}
