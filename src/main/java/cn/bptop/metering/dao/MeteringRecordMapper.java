package cn.bptop.metering.dao;

import cn.bptop.metering.pojo.Metering;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MeteringRecordMapper
{
    List<Metering> findRecord(@Param("userId") String userId);

    void addRecord(@Param("meteringId") String meteringId, @Param("unifyId") String unifyId, @Param("meteringValidity") String meteringValidity, @Param("meteringRange") String meteringRange, @Param("department") String department, @Param("userId") String userId, @Param("ddName") String ddName, @Param("manufacturingId") String manufacturingId, @Param("notes") String notes);

    void updateValidity(@Param("meteringValidity") String meteringValidity, @Param("meteringRecordId") String meteringRecordId);

    void updateStatus(@Param("meteringStatus") String meteringStatus, @Param("meteringRecordId") String meteringRecordId);

    void updateNotes(@Param("notes") String notes, @Param("meteringRecordId") String meteringRecordId);
}
