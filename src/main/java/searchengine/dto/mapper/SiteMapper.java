package searchengine.dto.mapper;

import org.mapstruct.Mapper;
import searchengine.dto.model.SiteDto;
import searchengine.entity.Site;

@Mapper(componentModel = "spring")
public interface SiteMapper {

    SiteDto toDto(Site site);
    Site toEntity(SiteDto siteDto);

}
