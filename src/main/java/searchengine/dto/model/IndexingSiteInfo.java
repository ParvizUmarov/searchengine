package searchengine.dto.model;

import lombok.Data;
import searchengine.entity.Lemma;

@Data
public class IndexingSiteInfo {

    private final int pageId;
    private final Lemma lemma;
    private final float rank;

}
