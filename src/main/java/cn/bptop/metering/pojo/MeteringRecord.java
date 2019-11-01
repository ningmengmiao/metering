package cn.bptop.metering.pojo;

public class MeteringRecord
{
    private Integer meteringRecordId;
    private Integer meteringId;
    private String unifyId;

    private String meteringValidity;
    private String plant;
    private String department;
    private String userId;
    private String ddName;
    private String meteringRange;

    public String getMeteringRange()
    {
        return meteringRange;
    }

    public void setMeteringRange(String meteringRange)
    {
        this.meteringRange = meteringRange;
    }

    public String getDdName()
    {
        return ddName;
    }

    public void setDdName(String ddName)
    {
        this.ddName = ddName;
    }

    private String manufacturingId;
    private String meteringStatus;

    public Integer getMeteringRecordId()
    {
        return meteringRecordId;
    }

    public void setMeteringRecordId(Integer meteringRecordId)
    {
        this.meteringRecordId = meteringRecordId;
    }

    public Integer getMeteringId()
    {
        return meteringId;
    }

    public void setMeteringId(Integer meteringId)
    {
        this.meteringId = meteringId;
    }

    public String getUnifyId()
    {
        return unifyId;
    }

    public void setUnifyId(String unifyId)
    {
        this.unifyId = unifyId;
    }

    public String getMeteringValidity()
    {
        return meteringValidity;
    }

    public void setMeteringValidity(String meteringValidity)
    {
        this.meteringValidity = meteringValidity;
    }

    public String getPlant()
    {
        return plant;
    }

    public void setPlant(String plant)
    {
        this.plant = plant;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getManufacturingId()
    {
        return manufacturingId;
    }

    public void setManufacturingId(String manufacturingId)
    {
        this.manufacturingId = manufacturingId;
    }

    public String getMeteringStatus()
    {
        return meteringStatus;
    }

    public void setMeteringStatus(String meteringStatus)
    {
        this.meteringStatus = meteringStatus;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    private String notes;
}
