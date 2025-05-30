package searchengine.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import searchengine.dto.model.PageDto;
import searchengine.entity.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(source = "page.site.id", target = "siteId")
    PageDto toDto(Page page);

    @Mapping(source = "siteId", target = "site.id")
    Page toEntity(PageDto pageDto);

}
