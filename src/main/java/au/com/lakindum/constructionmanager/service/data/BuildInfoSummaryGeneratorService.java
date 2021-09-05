package au.com.lakindum.constructionmanager.service.data;

import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;

public interface BuildInfoSummaryGeneratorService {
    void updateReportInfo(BuildInfo buildInfo);
    ReportInfo getReportInfo();
}
