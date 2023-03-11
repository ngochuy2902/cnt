package com.gg.cnt.service;

import com.gg.cnt.dto.req.ContinentCreateReq;
import com.gg.cnt.errors.ObjectNotFoundException;
import com.gg.cnt.model.Continent;
import com.gg.cnt.repository.ContinentRepository;
import com.gg.cnt.repository.custom.CustomContinentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service()
@RequiredArgsConstructor
public class ContinentService {

    private final ContinentRepository continentRepository;
    private final CustomContinentRepository customContinentRepository;

    public Long createContinent(ContinentCreateReq req) {
        return continentRepository.save(Continent.builder()
                        .name(req.getName())
                        .build())
                .getId();
    }

    public Continent getContinentDetail(Long id) {
        return customContinentRepository.getById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Continent"));
    }
}
