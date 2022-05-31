package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.GenderEnum;
import com.mycompany.myapp.domain.enumeration.PartnerTypeEnum;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Partner} entity.
 */
public class PartnerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 13)
    private String phonenumber;

    @NotNull
    @Size(max = 10)
    private String code;

    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String lastName;

    private GenderEnum gender;

    @Min(value = 16)
    @Max(value = 100)
    private Integer age;

    private Boolean isBlocked;

    private PartnerTypeEnum partnerType;

    @Min(value = 9)
    @Max(value = 9)
    private Integer inn;

    @Min(value = 14)
    @Max(value = 14)
    private Integer pinfl;

    private GroupDTO group;

    private SocialDTO social;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public PartnerTypeEnum getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(PartnerTypeEnum partnerType) {
        this.partnerType = partnerType;
    }

    public Integer getInn() {
        return inn;
    }

    public void setInn(Integer inn) {
        this.inn = inn;
    }

    public Integer getPinfl() {
        return pinfl;
    }

    public void setPinfl(Integer pinfl) {
        this.pinfl = pinfl;
    }

    public GroupDTO getGroup() {
        return group;
    }

    public void setGroup(GroupDTO group) {
        this.group = group;
    }

    public SocialDTO getSocial() {
        return social;
    }

    public void setSocial(SocialDTO social) {
        this.social = social;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartnerDTO)) {
            return false;
        }

        PartnerDTO partnerDTO = (PartnerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partnerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartnerDTO{" +
            "id=" + getId() +
            ", phonenumber='" + getPhonenumber() + "'" +
            ", code='" + getCode() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", age=" + getAge() +
            ", isBlocked='" + getIsBlocked() + "'" +
            ", partnerType='" + getPartnerType() + "'" +
            ", inn=" + getInn() +
            ", pinfl=" + getPinfl() +
            ", group=" + getGroup() +
            ", social=" + getSocial() +
            "}";
    }
}
