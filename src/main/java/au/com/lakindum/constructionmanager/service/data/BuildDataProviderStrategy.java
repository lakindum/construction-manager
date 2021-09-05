package au.com.lakindum.constructionmanager.service.data;

import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;

public interface BuildDataProviderStrategy {
    ReportInfo getBuildInfo(DataExtractionInfo dataExtractionInfo);
}
