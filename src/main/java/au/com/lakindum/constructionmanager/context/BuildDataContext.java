package au.com.lakindum.constructionmanager.context;

import au.com.lakindum.constructionmanager.exception.ConstructionManagerException;
import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.data.BuildDataProviderStrategy;

public class BuildDataContext {
    private final BuildDataProviderStrategy buildDataProviderStrategy;

    public BuildDataContext(BuildDataProviderStrategy buildDataProviderStrategy){
        this.buildDataProviderStrategy = buildDataProviderStrategy;
    }

    public ReportInfo executeReportDataCollector(DataExtractionInfo dataExtractionInfo) {
        if (dataExtractionInfo == null) throw new ConstructionManagerException("Invalid dataExtractionInfo details");
        return buildDataProviderStrategy.getBuildInfo(dataExtractionInfo);
    }
}
