package au.com.lakindum.constructionmanager.service.data.impl;

import au.com.lakindum.constructionmanager.exception.ConstructionManagerException;
import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.service.data.BuildInfoParserService;

public class BuildInfoParserServiceImpl implements BuildInfoParserService {
    public static final String BUILD_DURATION_PATTERN = "^[0-9]+[s]$";

    public BuildInfo getBuildInfo(final String[] buildInfoArray) {
        BuildInfo.BuildInfoBuilder buildInfoBuilder = BuildInfo.builder();
        if (buildInfoArray.length != 6) throw new ConstructionManagerException("Invalid csv data presentation found.");
        try {
            Integer customerId = Integer.parseInt(buildInfoArray[0]);
            Integer contractId = Integer.parseInt(buildInfoArray[1]);
            String geoZone = getStringValue(buildInfoArray[2], "geoZone");
            String teamCode = getStringValue(buildInfoArray[3], "teamCode");
            String projectCode = getStringValue(buildInfoArray[4], "projectCode");
            Integer buildDuration = getBuildDuration(buildInfoArray[5]);
            return buildInfoBuilder.customerId(customerId).contractId(contractId)
                    .geoZone(geoZone).teamCode(teamCode).projectCode(projectCode).buildDuration(buildDuration).build();
        } catch(Exception e) {
            throw new ConstructionManagerException(e.getMessage());
        }
    }

    private String getStringValue(final String value, final String variableName) {
        if (value == null || value.trim().isEmpty()) throw new ConstructionManagerException("Invalid value for " + variableName);
        return value.trim();
    }

    private Integer getBuildDuration(final String buildDuration) {
        if (buildDuration == null) throw new ConstructionManagerException("Build duration is null");
        if (buildDuration.matches(BUILD_DURATION_PATTERN)) {
            return Integer.parseInt(buildDuration.substring(0, buildDuration.length() - 1));
        } else {
            throw new ConstructionManagerException("Build Duration pattern is invalid");
        }
    }
}
