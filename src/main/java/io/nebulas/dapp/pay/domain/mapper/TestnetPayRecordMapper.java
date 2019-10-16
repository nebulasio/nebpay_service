package io.nebulas.dapp.pay.domain.mapper;

import io.nebulas.dapp.pay.domain.model.TestnetPayRecord;
import io.nebulas.dapp.pay.domain.model.TestnetPayRecordCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TestnetPayRecordMapper {
    int countByCondition(TestnetPayRecordCondition example);

    int deleteByCondition(TestnetPayRecordCondition example);

    int deleteById(Long id);

    int insert(TestnetPayRecord record);

    int insertSelective(TestnetPayRecord record);

    List<TestnetPayRecord> selectByCondition(TestnetPayRecordCondition example);

    TestnetPayRecord selectById(Long id);

    int updateByConditionSelective(@Param("record") TestnetPayRecord record, @Param("example") TestnetPayRecordCondition example);

    int updateByCondition(@Param("record") TestnetPayRecord record, @Param("example") TestnetPayRecordCondition example);

    int updateByIdSelective(TestnetPayRecord record);

    int updateById(TestnetPayRecord record);
}