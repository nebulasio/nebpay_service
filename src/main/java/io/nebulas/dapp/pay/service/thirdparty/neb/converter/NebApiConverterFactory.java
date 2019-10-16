package io.nebulas.dapp.pay.service.thirdparty.neb.converter;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class NebApiConverterFactory extends Converter.Factory {

    public static NebApiConverterFactory create() {
        return new NebApiConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new NebApiResponseBodyConverter<>(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new NebApiRequestBodyConverter<>();
    }
}
