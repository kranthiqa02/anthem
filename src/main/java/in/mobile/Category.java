package in.mobile;

public class Category
{
    private Integer id;
    private String type;
    private String subType;
    private String description;
    private String status;

    public Category() {
    }
    public Category(Integer id, String type, String subType, String description, String status)
    {
        this.id = id;
        this.type = type;
        this.subType = subType;
        this.description = description;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
