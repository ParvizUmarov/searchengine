package searchengine.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import searchengine.dto.model.LemmaDto;
import searchengine.entity.Lemma;

@Mapper(componentModel = "spring")
public interface LemmaMapper {

    @Mapping(source = "lemma.site.id", target = "siteId")
    LemmaDto toDto(Lemma lemma);

    @Mapping(target = "site.id", source = "siteId")
    Lemma toEntity(LemmaDto lemmaDto);

}
