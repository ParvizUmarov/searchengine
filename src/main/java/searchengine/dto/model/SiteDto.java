package searchengine.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import searchengine.entity.Status;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteDto {

    private Integer id;
    private Status status;
    private Date date;
    private String lastError;
    private String url;
    private String name;

}
