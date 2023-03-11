package com.gg.cnt.api;

import com.gg.cnt.dto.req.ContinentCreateReq;
import com.gg.cnt.mapper.ContinentMapper;
import com.gg.cnt.model.Continent;
import com.gg.cnt.service.ContinentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/continents")
@RequiredArgsConstructor
public class ContinentController {
    private final ContinentService continentService;

    @PostMapping()
    public ResponseEntity<?> createContinent(@Valid @RequestBody final ContinentCreateReq req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(continentService.createContinent(req));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getContinentDetail(@PathVariable final Long id) {
        Continent continent = continentService.getContinentDetail(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ContinentMapper.INSTANCE.toContinentRes(continent));
    }
}
