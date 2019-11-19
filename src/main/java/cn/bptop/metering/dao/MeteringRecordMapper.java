package cn.bptop.metering.dao;

import cn.bptop.metering.pojo.MeteringRecord;
import cn.bptop.metering.pojo.MeteringRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MeteringRecordMapper
{
    //以工号查工具记录
    List<MeteringRecordVO> findRecord(@Param("userId") String userId, @Param("meteringRecordId") String meteringRecordId);

    //筛选
    List<MeteringRecordVO> filtrateRecord(@Param("meteringName") String meteringName, @Param("department") String department, @Param("meteringStatus") String meateringStatus);

    //添加记录
    void addRecord(@Param("meteringId") String meteringId, @Param("unifyId") String unifyId, @Param("meteringTestTime") String meteringTestTime, @Param("meteringValidity") String meteringValidity, @Param("meteringRange") String meteringRange, @Param("department") String department, @Param("userId") String userId, @Param("ddName") String ddName, @Param("manufacturingId") String manufacturingId, @Param("meteringStatus") String meteringStatus, @Param("notes") String notes, @Param("meteringLog") String meteringLog);

    //更新有效期
    void updateValidity(@Param("meteringValidity") String meteringValidity, @Param("meteringRecordId") String meteringRecordId);

    //更新状态
    void updateStatus(@Param("meteringStatus") String meteringStatus, @Param("meteringRecordId") String meteringRecordId);

    //更新备注
    void updateNotes(@Param("notes") String notes, @Param("meteringRecordId") String meteringRecordId);

    //以状态查记录
    List<MeteringRecord> findRecordByStatus(@Param("meteringStatus") String meteringStatus);

    // 更换持有人
    void makeOver(@Param("makeOverUser") String makeOverUser, @Param("makeOverUserId") String makeOverUserId, @Param("department") String department, @Param("meteringRecordId") String meteringRecordId);
}

