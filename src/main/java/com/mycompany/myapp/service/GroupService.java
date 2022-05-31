package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.GroupDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Group}.
 */
public interface GroupService {
    /**
     * Save a group.
     *
     * @param groupDTO the entity to save.
     * @return the persisted entity.
     */
    GroupDTO save(GroupDTO groupDTO);

    /**
     * Updates a group.
     *
     * @param groupDTO the entity to update.
     * @return the persisted entity.
     */
    GroupDTO update(GroupDTO groupDTO);

    /**
     * Partially updates a group.
     *
     * @param groupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GroupDTO> partialUpdate(GroupDTO groupDTO);

    /**
     * Get all the groups.
     *
     * @return the list of entities.
     */
    List<GroupDTO> findAll();

    /**
     * Get the "id" group.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupDTO> findOne(Long id);

    /**
     * Delete the "id" group.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
