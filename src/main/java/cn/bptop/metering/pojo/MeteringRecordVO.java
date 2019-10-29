package cn.bptop.metering.pojo;

public class MeteringRecordVO
{
    private Metering metering;
    private MeteringRecord meteringRecord;

    public Metering getMetering()
    {
        return metering;
    }

    public void setMetering(Metering metering)
    {
        this.metering = metering;
    }

    public MeteringRecord getMeteringRecord()
    {
        return meteringRecord;
    }

    public void setMeteringRecord(MeteringRecord meteringRecord)
    {
        this.meteringRecord = meteringRecord;
    }
}
