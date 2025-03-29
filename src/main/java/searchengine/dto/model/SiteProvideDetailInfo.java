package searchengine.dto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteProvideDetailInfo {

    private String url;
    private String mainDomain;
    private String body;
    private int code;
    private String bodyText;

}
