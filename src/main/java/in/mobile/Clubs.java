package in.mobile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="club")
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Clubs
{
    private Integer id;
    private String name;
    private String categoryName;
    private int categoryId;
    private Category category = new Category();
    private String description;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String locality ;
    private String city;
    private int stateId;
    private String pincode;
    private String primaryContactNo;
    private String primaryEmailAddress;
    private String websiteAddress;

    public Clubs() {
    }

    public Clubs(Integer id, String name, String categoryName, Category category, String description, String addressLine1,
                 String addressLine2, String addressLine3, String locality, String city, int stateId, String pincode,
                 String primaryContactNo, String primaryEmailAddress, String websiteAddress)
    {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
        this.category = category;
        this.description = description;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.locality = locality;
        this.city = city;
        this.stateId = stateId;
        this.pincode = pincode;
        this.primaryContactNo = primaryContactNo;
        this.primaryEmailAddress = primaryEmailAddress;
        this.websiteAddress = websiteAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return this.category.getId();
    }

    public void setCategoryId(Integer id) {
        this.category.setId(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPrimaryContactNo() {
        return primaryContactNo;
    }

    public void setPrimaryContactNo(String primaryContactNo) {
        this.primaryContactNo = primaryContactNo;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public void setPrimaryEmailAddress(String primaryEmailAddress) {
        this.primaryEmailAddress = primaryEmailAddress;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public String getCategoryName() {
        return getCategory().getType();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
