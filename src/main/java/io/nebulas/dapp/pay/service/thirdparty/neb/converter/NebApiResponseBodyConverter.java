package io.nebulas.dapp.pay.service.thirdparty.neb.converter;

import com.alibaba.fastjson.JSON;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

public class NebApiResponseBodyConverter<T> implements Converter<ResponseBody, T> {

  private final Type type;

  public NebApiResponseBodyConverter(Type type){
    this.type = type;
  }


  @Override public T convert(ResponseBody value) throws IOException {
    return JSON.parseObject(value.string(),type);
  }
}
