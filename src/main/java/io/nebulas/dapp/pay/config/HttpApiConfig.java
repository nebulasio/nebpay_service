package io.nebulas.dapp.pay.config;

import io.nebulas.dapp.pay.service.thirdparty.neb.NebApiService;
import io.nebulas.dapp.pay.service.thirdparty.neb.converter.NebApiConverterFactory;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * User: nathan
 * Date: 2018-04-25
 */
@Configuration
public class HttpApiConfig {

    @Value("${thirdparty.api.host.neb}")
    private String nebApiHost;

    @Value("${thirdparty.api.host.neb_testnet}")
    private String nebTestNetApiHost;

    @Bean
    @Scope
    public OkHttpClient createDefaultHttpClient() {
        return httpClient(50, 5, true);
    }

    @Scope
    @Primary
    @Bean("mainnetNebApiService")
    public NebApiService createNebApiService(OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(nebApiHost)
                .addConverterFactory(NebApiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(NebApiService.class);
    }

    @Scope
    @Bean("testnetNebApiService")
    public NebApiService createTestnetNebApiService(OkHttpClient httpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .baseUrl(nebTestNetApiHost)
                .addConverterFactory(NebApiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(NebApiService.class);
    }

    private OkHttpClient httpClient(int maxIdleConnections, long keepAliveDuration, boolean log) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(maxIdleConnections, keepAliveDuration, MINUTES))
                .connectTimeout(1, MINUTES)
                .readTimeout(2, MINUTES)
                .writeTimeout(1, MINUTES);
        if (log) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }
}
