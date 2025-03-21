package searchengine.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndexDto {

    private Integer id;
    private Integer pageId;
    private Integer siteId;
    private Float ranking;

}
