package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.CatalogNameEnum;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Group} entity.
 */
public class GroupDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 33)
    private String name;

    @Size(max = 200)
    private String info;

    private CatalogNameEnum catalog;

    private GroupDTO parent;

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

    public CatalogNameEnum getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogNameEnum catalog) {
        this.catalog = catalog;
    }

    public GroupDTO getParent() {
        return parent;
    }

    public void setParent(GroupDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupDTO)) {
            return false;
        }

        GroupDTO groupDTO = (GroupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, groupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", info='" + getInfo() + "'" +
            ", catalog='" + getCatalog() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
