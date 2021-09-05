package au.com.lakindum.constructionmanager.service;

import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;

public interface ReportDataGeneratorService {
    void updateReportInfo(BuildInfo buildInfo);
    ReportInfo getReportInfo();
}
