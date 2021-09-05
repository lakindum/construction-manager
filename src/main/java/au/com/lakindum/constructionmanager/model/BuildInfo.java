package au.com.lakindum.constructionmanager.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuildInfo {

    private final Integer customerId;
    private final Integer contractId;
    private final String geoZone;
    private final String teamCode;
    private final String projectCode;
    private final Integer buildDuration;
}
