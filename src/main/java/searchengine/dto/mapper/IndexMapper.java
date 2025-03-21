package searchengine.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import searchengine.dto.model.IndexDto;
import searchengine.entity.Index;

@Mapper(componentModel = "spring")
public interface IndexMapper {

    @Mapping(source = "index.page.id", target = "pageId")
    IndexDto toDto(Index index);

    @Mapping(target = "page.id", source = "pageId")
    Index toEntity(IndexDto indexDto);

}
