package au.com.lakindum.constructionmanager.service.data.impl;

import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.data.BuildInfoSummaryGeneratorService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BuildInfoSummaryGeneratorServiceImpl implements BuildInfoSummaryGeneratorService {

    private final Map<Integer, Set<Integer>> customersPerContractMap = new HashMap<>();
    private final Map<String, Set<Integer>> customersPerGeoZoneMap = new HashMap<>();
    private final Map<String, Set<Integer>> buildDurationsPerGeoZoneMap = new HashMap<>();

    public void updateReportInfo(BuildInfo buildInfo) {
        updateCustomersPerContract(buildInfo);
        updateCustomersPerGeoZone(buildInfo);
        updateBuildDurationsPerGeoZone(buildInfo);
    }

    public ReportInfo getReportInfo() {
        return ReportInfo.builder()
            .customersPerContractMap(this.customersPerContractMap)
            .customersPerGeoZoneMap(this.customersPerGeoZoneMap)
            .buildDurationsPerGeoZoneMap(this.buildDurationsPerGeoZoneMap)
            .build();
    }

    private void updateCustomersPerContract(final BuildInfo buildInfo) {
        if (customersPerContractMap.containsKey(buildInfo.getContractId())) {
            customersPerContractMap.get(buildInfo.getContractId()).add(buildInfo.getCustomerId());
        } else {
            Set<Integer> customerIdSet = new HashSet<>();
            customerIdSet.add(buildInfo.getCustomerId());
            customersPerContractMap.put(buildInfo.getContractId(), customerIdSet);
        }
    }

    private void updateCustomersPerGeoZone(final BuildInfo buildInfo) {
        if (customersPerGeoZoneMap.containsKey(buildInfo.getGeoZone())) {
            customersPerGeoZoneMap.get(buildInfo.getGeoZone()).add(buildInfo.getCustomerId());
        } else {
            Set<Integer> customerIdSet = new HashSet<>();
            customerIdSet.add(buildInfo.getCustomerId());
            customersPerGeoZoneMap.put(buildInfo.getGeoZone(), customerIdSet);
        }
    }

    private void updateBuildDurationsPerGeoZone(final BuildInfo buildInfo) {
        if (buildDurationsPerGeoZoneMap.containsKey(buildInfo.getGeoZone())) {
            buildDurationsPerGeoZoneMap.get(buildInfo.getGeoZone()).add(buildInfo.getBuildDuration());
        } else {
            Set<Integer> buildDurationSet = new HashSet<>();
            buildDurationSet.add(buildInfo.getBuildDuration());
            buildDurationsPerGeoZoneMap.put(buildInfo.getGeoZone(), buildDurationSet);
        }
    }

}
