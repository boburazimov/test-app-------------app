package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Barcode;
import com.mycompany.myapp.repository.BarcodeRepository;
import com.mycompany.myapp.service.BarcodeService;
import com.mycompany.myapp.service.dto.BarcodeDTO;
import com.mycompany.myapp.service.mapper.BarcodeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Barcode}.
 */
@Service
@Transactional
public class BarcodeServiceImpl implements BarcodeService {

    private final Logger log = LoggerFactory.getLogger(BarcodeServiceImpl.class);

    private final BarcodeRepository barcodeRepository;

    private final BarcodeMapper barcodeMapper;

    public BarcodeServiceImpl(BarcodeRepository barcodeRepository, BarcodeMapper barcodeMapper) {
        this.barcodeRepository = barcodeRepository;
        this.barcodeMapper = barcodeMapper;
    }

    @Override
    public BarcodeDTO save(BarcodeDTO barcodeDTO) {
        log.debug("Request to save Barcode : {}", barcodeDTO);
        Barcode barcode = barcodeMapper.toEntity(barcodeDTO);
        barcode = barcodeRepository.save(barcode);
        return barcodeMapper.toDto(barcode);
    }

    @Override
    public BarcodeDTO update(BarcodeDTO barcodeDTO) {
        log.debug("Request to save Barcode : {}", barcodeDTO);
        Barcode barcode = barcodeMapper.toEntity(barcodeDTO);
        barcode = barcodeRepository.save(barcode);
        return barcodeMapper.toDto(barcode);
    }

    @Override
    public Optional<BarcodeDTO> partialUpdate(BarcodeDTO barcodeDTO) {
        log.debug("Request to partially update Barcode : {}", barcodeDTO);

        return barcodeRepository
            .findById(barcodeDTO.getId())
            .map(existingBarcode -> {
                barcodeMapper.partialUpdate(existingBarcode, barcodeDTO);

                return existingBarcode;
            })
            .map(barcodeRepository::save)
            .map(barcodeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BarcodeDTO> findAll() {
        log.debug("Request to get all Barcodes");
        return barcodeRepository.findAll().stream().map(barcodeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BarcodeDTO> findOne(Long id) {
        log.debug("Request to get Barcode : {}", id);
        return barcodeRepository.findById(id).map(barcodeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Barcode : {}", id);
        barcodeRepository.deleteById(id);
    }
}
