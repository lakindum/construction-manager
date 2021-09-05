package au.com.lakindum.constructionmanager.service;

import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;

public interface BuildDataProvider {
    ReportInfo getReportInfo(DataExtractionInfo dataExtractionInfo);
}
