package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Social} entity.
 */
public class SocialDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String name;

    @Size(max = 200)
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialDTO)) {
            return false;
        }

        SocialDTO socialDTO = (SocialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, socialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            "}";
    }
}
