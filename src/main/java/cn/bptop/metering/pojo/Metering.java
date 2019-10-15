package cn.bptop.metering.pojo;

public class Metering
{
    private Integer id;
    private String ddName;
    private String mTool;
    private String mValidity;
    private Integer mStatusCode;
    private String mStatus;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDdName()
    {
        return ddName;
    }

    public void setDdName(String ddName)
    {
        this.ddName = ddName;
    }

    public String getmTool()
    {
        return mTool;
    }

    public void setmTool(String mTool)
    {
        this.mTool = mTool;
    }

    public String getmValidity()
    {
        return mValidity;
    }

    public void setmValidity(String mValidity)
    {
        this.mValidity = mValidity;
    }

    public Integer getmStatusCode()
    {
        return mStatusCode;
    }

    public void setmStatusCode(Integer mStatusCode)
    {
        this.mStatusCode = mStatusCode;
    }

    public String getmStatus()
    {
        return mStatus;
    }

    public void setmStatus(String mStatus)
    {
        this.mStatus = mStatus;
    }
}
