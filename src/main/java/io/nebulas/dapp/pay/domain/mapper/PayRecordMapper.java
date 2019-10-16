package io.nebulas.dapp.pay.domain.mapper;

import io.nebulas.dapp.pay.domain.model.PayRecord;
import io.nebulas.dapp.pay.domain.model.PayRecordCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayRecordMapper {
    int countByCondition(PayRecordCondition example);

    int deleteByCondition(PayRecordCondition example);

    int deleteById(Long id);

    int insert(PayRecord record);

    int insertSelective(PayRecord record);

    List<PayRecord> selectByCondition(PayRecordCondition example);

    PayRecord selectById(Long id);

    int updateByConditionSelective(@Param("record") PayRecord record, @Param("example") PayRecordCondition example);

    int updateByCondition(@Param("record") PayRecord record, @Param("example") PayRecordCondition example);

    int updateByIdSelective(PayRecord record);

    int updateById(PayRecord record);
}