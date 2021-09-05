package au.com.lakindum.constructionmanager.service.impl;

import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.BuildDataProvider;

public class CSVBuildDataProvider implements BuildDataProvider {

    public ReportInfo getReportInfo(DataExtractionInfo dataExtractionInfo) {
        return ReportInfo.builder().build();
    }
}
