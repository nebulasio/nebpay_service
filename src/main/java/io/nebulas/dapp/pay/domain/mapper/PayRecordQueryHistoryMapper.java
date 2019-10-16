package io.nebulas.dapp.pay.domain.mapper;

import io.nebulas.dapp.pay.domain.model.PayRecordQueryHistory;
import io.nebulas.dapp.pay.domain.model.PayRecordQueryHistoryCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayRecordQueryHistoryMapper {
    int countByCondition(PayRecordQueryHistoryCondition example);

    int deleteByCondition(PayRecordQueryHistoryCondition example);

    int deleteById(Integer id);

    int insert(PayRecordQueryHistory record);

    int insertSelective(PayRecordQueryHistory record);

    List<PayRecordQueryHistory> selectByCondition(PayRecordQueryHistoryCondition example);

    PayRecordQueryHistory selectById(Integer id);

    int updateByConditionSelective(@Param("record") PayRecordQueryHistory record, @Param("example") PayRecordQueryHistoryCondition example);

    int updateByCondition(@Param("record") PayRecordQueryHistory record, @Param("example") PayRecordQueryHistoryCondition example);

    int updateByIdSelective(PayRecordQueryHistory record);

    int updateById(PayRecordQueryHistory record);
}