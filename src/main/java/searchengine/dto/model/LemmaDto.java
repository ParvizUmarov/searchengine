package searchengine.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LemmaDto {

    private Integer id;
    private Integer siteId;
    private String lemma;
    private Integer frequency;

}
