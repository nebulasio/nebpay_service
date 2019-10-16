package io.nebulas.dapp.pay.service.thirdparty.neb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.nebulas.dapp.pay.common.bean.Tuple;
import io.nebulas.dapp.pay.service.thirdparty.neb.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * Desc:
 * User: nathan
 * Date: 2018-03-08
 */
@Service
public class NebApiServiceWrapper {
    @Autowired
    @Qualifier("mainnetNebApiService")
    private NebApiService nebApiService;

    public NebStateResponse getNebState() {
        return nebApiService.getNebState().toBlocking().first().getResult();
    }

    public NebBlockResponse getLatestIrreversibleBlock() {
        return nebApiService.getLatestIrreversibleBlock().toBlocking().first().getResult();
    }

    public String getGasPrice() {
        return nebApiService.getGasPrice().toBlocking().first().getResult().getGas_price();
    }

    public NebBlockResponse getBlockByHash(String hash) {
        return nebApiService.getBlockByHash(new GetBlockByHashRequest(hash, true)).toBlocking().first().getResult();
    }

    public NebBlockResponse getBlockByHash(String hash, Boolean fullTransaction) {
        try {
            return nebApiService.getBlockByHash(new GetBlockByHashRequest(hash, fullTransaction)).toBlocking().first().getResult();
        } catch (Exception e) {
            return null;
        }
    }

    public NebBlockResponse getBlockByHeight(Long height) {
        try {
            return nebApiService.getBlockByHeight(new GetBlockByHeightRequest(height, true)).toBlocking().first().getResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getDynasty(Long height) {
        return nebApiService.getDynasty(new GetDynastyRequest(height)).toBlocking().first().getResult().getMiners();
    }

    public String getGasUsed(String hash) {
        return nebApiService.getGasUsed(new HashRequest(hash)).toBlocking().first().getResult().getGas();
    }

    public GetAccountStateResponse getAccountState(String address) {
        return nebApiService.getAccountState(new GetAccountStateRequest(address, "latest")).toBlocking().first().getResult();
    }

//    public NebTransactionResponse getTransactionReceipt(String hash) {
//        try {
//            return nebApiService.getTransactionReceipt(new HashRequest(hash)).toBlocking().first().getResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public Tuple.Tuple3<Boolean, NebTransactionResponse, String> getTransactionReceipt(String hash) {
        try {
//            return nebApiService.getTransactionReceipt(new HashRequest(hash)).toBlocking().first().getResult();
            Response<NebResponse<NebTransactionResponse>> response = nebApiService.getTransactionReceipt(new HashRequest(hash)).execute();
            if (response.code() == 200) {
                return Tuple.tuple(true, response.body().getResult(), null);
            } else {
                JSONObject jsonObject = JSON.parseObject(response.errorBody().string());
                return Tuple.tuple(false, null, jsonObject.getString("error"));
            }
        } catch (Exception e) {
            return Tuple.tuple(false, null, null);
        }
    }

    public String estimateGas(SendTransactionRequest request) {
        return nebApiService.estimateGas(request).toBlocking().first().getResult().getGas();
    }

    public BigInteger getContractBalance(String address, String contract) {
        SendTransactionRequest request = new SendTransactionRequest();
        request.setFrom(address);
        request.setTo(contract);
        request.setValue("0");
        request.setGas_price("1000000");
        request.setGas_limit("20000000");
        request.setContract(new Contract("balanceOf", JSON.toJSONString(Arrays.asList(address))));

        JSONObject jsonObject = nebApiService.call(request).toBlocking().first().getResult();
        String balance = jsonObject.getString("result");
        return new BigInteger(JSONObject.parse(balance).toString());
    }
}
