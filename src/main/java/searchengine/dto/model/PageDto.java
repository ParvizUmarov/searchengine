package searchengine.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto {

    private Integer id;
    private Integer siteId;
    private String path;
    private Integer code;
    private String content;

}
