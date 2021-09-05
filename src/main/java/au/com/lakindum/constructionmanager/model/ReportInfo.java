package au.com.lakindum.constructionmanager.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class ReportInfo {
    private final Map<String, Set<String>> customersPerContractMap;
    private final Map<String, Set<String>> customersPerGeoZoneMap;
    private final Map<String, Integer> averageBuildDurationPerGeoZoneMap;
}
