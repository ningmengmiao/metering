package cn.bptop.metering.pojo;

public class Metering
{
    private Integer meteringId;
    private String meteringName;
    private String meteringModel;
    private String meteringClassify;
    private Integer meteringPeriod;

    public Integer getMeteringId()
    {
        return meteringId;
    }

    public void setMeteringId(Integer meteringId)
    {
        this.meteringId = meteringId;
    }

    public String getMeteringName()
    {
        return meteringName;
    }

    public void setMeteringName(String meteringName)
    {
        this.meteringName = meteringName;
    }

    public String getMeteringModel()
    {
        return meteringModel;
    }

    public void setMeteringModel(String meteringModel)
    {
        this.meteringModel = meteringModel;
    }

    public String getMeteringClassify()
    {
        return meteringClassify;
    }

    public void setMeteringClassify(String meteringClassify)
    {
        this.meteringClassify = meteringClassify;
    }

    public Integer getMeteringPeriod()
    {
        return meteringPeriod;
    }

    public void setMeteringPeriod(Integer meteringPeriod)
    {
        this.meteringPeriod = meteringPeriod;
    }
}
