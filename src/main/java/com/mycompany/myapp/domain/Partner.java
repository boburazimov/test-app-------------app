package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.GenderEnum;
import com.mycompany.myapp.domain.enumeration.PartnerTypeEnum;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Partner.
 */
@Entity
@Table(name = "partner")
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 13)
    @Column(name = "phonenumber", length = 13, nullable = false)
    private String phonenumber;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Size(max = 20)
    @Column(name = "first_name", length = 20)
    private String firstName;

    @Size(max = 20)
    @Column(name = "last_name", length = 20)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderEnum gender;

    @Min(value = 16)
    @Max(value = 100)
    @Column(name = "age")
    private Integer age;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @Enumerated(EnumType.STRING)
    @Column(name = "partner_type")
    private PartnerTypeEnum partnerType;

    @Min(value = 9)
    @Max(value = 9)
    @Column(name = "inn")
    private Integer inn;

    @Min(value = 14)
    @Max(value = 14)
    @Column(name = "pinfl")
    private Integer pinfl;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Group group;

    @ManyToOne
    private Social social;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Partner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public Partner phonenumber(String phonenumber) {
        this.setPhonenumber(phonenumber);
        return this;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCode() {
        return this.code;
    }

    public Partner code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Partner firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Partner lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GenderEnum getGender() {
        return this.gender;
    }

    public Partner gender(GenderEnum gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return this.age;
    }

    public Partner age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsBlocked() {
        return this.isBlocked;
    }

    public Partner isBlocked(Boolean isBlocked) {
        this.setIsBlocked(isBlocked);
        return this;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public PartnerTypeEnum getPartnerType() {
        return this.partnerType;
    }

    public Partner partnerType(PartnerTypeEnum partnerType) {
        this.setPartnerType(partnerType);
        return this;
    }

    public void setPartnerType(PartnerTypeEnum partnerType) {
        this.partnerType = partnerType;
    }

    public Integer getInn() {
        return this.inn;
    }

    public Partner inn(Integer inn) {
        this.setInn(inn);
        return this;
    }

    public void setInn(Integer inn) {
        this.inn = inn;
    }

    public Integer getPinfl() {
        return this.pinfl;
    }

    public Partner pinfl(Integer pinfl) {
        this.setPinfl(pinfl);
        return this;
    }

    public void setPinfl(Integer pinfl) {
        this.pinfl = pinfl;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Partner group(Group group) {
        this.setGroup(group);
        return this;
    }

    public Social getSocial() {
        return this.social;
    }

    public void setSocial(Social social) {
        this.social = social;
    }

    public Partner social(Social social) {
        this.setSocial(social);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partner)) {
            return false;
        }
        return id != null && id.equals(((Partner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partner{" +
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
            "}";
    }
}
