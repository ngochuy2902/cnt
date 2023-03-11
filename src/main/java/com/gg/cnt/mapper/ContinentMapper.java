package com.gg.cnt.mapper;

import com.gg.cnt.dto.res.ContinentRes;
import com.gg.cnt.model.Continent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ContinentMapper {
    ContinentMapper INSTANCE = Mappers.getMapper(ContinentMapper.class);

    ContinentRes toContinentRes(Continent continent);
}
