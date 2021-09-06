package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;

public interface Report {
    void accept(ReportPrinterService reportPrinterService, ReportInfo reportInfo);
}
