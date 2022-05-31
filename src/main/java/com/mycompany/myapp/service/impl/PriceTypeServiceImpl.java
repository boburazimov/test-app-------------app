package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.PriceType;
import com.mycompany.myapp.repository.PriceTypeRepository;
import com.mycompany.myapp.service.PriceTypeService;
import com.mycompany.myapp.service.dto.PriceTypeDTO;
import com.mycompany.myapp.service.mapper.PriceTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PriceType}.
 */
@Service
@Transactional
public class PriceTypeServiceImpl implements PriceTypeService {

    private final Logger log = LoggerFactory.getLogger(PriceTypeServiceImpl.class);

    private final PriceTypeRepository priceTypeRepository;

    private final PriceTypeMapper priceTypeMapper;

    public PriceTypeServiceImpl(PriceTypeRepository priceTypeRepository, PriceTypeMapper priceTypeMapper) {
        this.priceTypeRepository = priceTypeRepository;
        this.priceTypeMapper = priceTypeMapper;
    }

    @Override
    public PriceTypeDTO save(PriceTypeDTO priceTypeDTO) {
        log.debug("Request to save PriceType : {}", priceTypeDTO);
        PriceType priceType = priceTypeMapper.toEntity(priceTypeDTO);
        priceType = priceTypeRepository.save(priceType);
        return priceTypeMapper.toDto(priceType);
    }

    @Override
    public PriceTypeDTO update(PriceTypeDTO priceTypeDTO) {
        log.debug("Request to save PriceType : {}", priceTypeDTO);
        PriceType priceType = priceTypeMapper.toEntity(priceTypeDTO);
        priceType = priceTypeRepository.save(priceType);
        return priceTypeMapper.toDto(priceType);
    }

    @Override
    public Optional<PriceTypeDTO> partialUpdate(PriceTypeDTO priceTypeDTO) {
        log.debug("Request to partially update PriceType : {}", priceTypeDTO);

        return priceTypeRepository
            .findById(priceTypeDTO.getId())
            .map(existingPriceType -> {
                priceTypeMapper.partialUpdate(existingPriceType, priceTypeDTO);

                return existingPriceType;
            })
            .map(priceTypeRepository::save)
            .map(priceTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PriceTypeDTO> findAll() {
        log.debug("Request to get all PriceTypes");
        return priceTypeRepository.findAll().stream().map(priceTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PriceTypeDTO> findOne(Long id) {
        log.debug("Request to get PriceType : {}", id);
        return priceTypeRepository.findById(id).map(priceTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceType : {}", id);
        priceTypeRepository.deleteById(id);
    }
}
