package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Social;
import com.mycompany.myapp.repository.SocialRepository;
import com.mycompany.myapp.service.SocialService;
import com.mycompany.myapp.service.dto.SocialDTO;
import com.mycompany.myapp.service.mapper.SocialMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Social}.
 */
@Service
@Transactional
public class SocialServiceImpl implements SocialService {

    private final Logger log = LoggerFactory.getLogger(SocialServiceImpl.class);

    private final SocialRepository socialRepository;

    private final SocialMapper socialMapper;

    public SocialServiceImpl(SocialRepository socialRepository, SocialMapper socialMapper) {
        this.socialRepository = socialRepository;
        this.socialMapper = socialMapper;
    }

    @Override
    public SocialDTO save(SocialDTO socialDTO) {
        log.debug("Request to save Social : {}", socialDTO);
        Social social = socialMapper.toEntity(socialDTO);
        social = socialRepository.save(social);
        return socialMapper.toDto(social);
    }

    @Override
    public SocialDTO update(SocialDTO socialDTO) {
        log.debug("Request to save Social : {}", socialDTO);
        Social social = socialMapper.toEntity(socialDTO);
        social = socialRepository.save(social);
        return socialMapper.toDto(social);
    }

    @Override
    public Optional<SocialDTO> partialUpdate(SocialDTO socialDTO) {
        log.debug("Request to partially update Social : {}", socialDTO);

        return socialRepository
            .findById(socialDTO.getId())
            .map(existingSocial -> {
                socialMapper.partialUpdate(existingSocial, socialDTO);

                return existingSocial;
            })
            .map(socialRepository::save)
            .map(socialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialDTO> findAll() {
        log.debug("Request to get all Socials");
        return socialRepository.findAll().stream().map(socialMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocialDTO> findOne(Long id) {
        log.debug("Request to get Social : {}", id);
        return socialRepository.findById(id).map(socialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Social : {}", id);
        socialRepository.deleteById(id);
    }
}
