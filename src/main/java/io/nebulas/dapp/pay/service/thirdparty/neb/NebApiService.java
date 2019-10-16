package io.nebulas.dapp.pay.service.thirdparty.neb;

import com.alibaba.fastjson.JSONObject;
import io.nebulas.dapp.pay.service.thirdparty.neb.bean.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Desc:
 * User: nathan
 * Date: 2018-02-23
 */
public interface NebApiService {

    @GET("/v1/user/nebstate")
    Observable<NebResponse<NebStateResponse>> getNebState();

    @GET("/v1/user/lib")
    Observable<NebResponse<NebBlockResponse>> getLatestIrreversibleBlock();

    @GET("/v1/user/getGasPrice")
    Observable<NebResponse<GetGasPriceResponse>> getGasPrice();
    
    @POST("/v1/user/getBlockByHash")
    Observable<NebResponse<NebBlockResponse>> getBlockByHash(@Body GetBlockByHashRequest request);

    @POST("/v1/user/getBlockByHeight")
    Observable<NebResponse<NebBlockResponse>> getBlockByHeight(@Body GetBlockByHeightRequest request);

    @POST("/v1/user/dynasty")
    Observable<NebResponse<GetDynastyResponse>> getDynasty(@Body GetDynastyRequest request);

    @POST("/v1/user/getGasUsed")
    Observable<NebResponse<GetGasUsedResponse>> getGasUsed(@Body HashRequest request);

    @POST("/v1/user/accountstate")
    Observable<NebResponse<GetAccountStateResponse>> getAccountState(@Body GetAccountStateRequest request);

//    @POST("/v1/user/getTransactionReceipt")
//    Observable<NebResponse<NebTransactionResponse>> getTransactionReceipt(@Body HashRequest request);

    @POST("/v1/user/getTransactionReceipt")
    Call<NebResponse<NebTransactionResponse>> getTransactionReceipt(@Body HashRequest request);

    @POST("/v1/user/estimateGas")
    Observable<NebResponse<EstimateGasResponse>> estimateGas(@Body SendTransactionRequest request);

    @POST("/v1/user/call")
    Observable<NebResponse<JSONObject>> call(@Body SendTransactionRequest request);
}
