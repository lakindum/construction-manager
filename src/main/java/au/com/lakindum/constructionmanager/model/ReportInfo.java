package au.com.lakindum.constructionmanager.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
@Getter
@Builder
public class ReportInfo {
    private final Map<Integer, Set<Integer>> customersPerContractMap;
    private final Map<String, Set<Integer>> customersPerGeoZoneMap;
    private final Map<String, Set<Integer>> buildDurationsPerGeoZoneMap;
}
