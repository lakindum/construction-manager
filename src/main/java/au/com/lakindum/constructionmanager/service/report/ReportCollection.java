package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;
import lombok.Builder;

@Builder
public class ReportCollection {
    private final Report[] reports;

    public void print(ReportPrinterService reportPrinterService, ReportInfo reportInfo) {
        for (Report report:reports) {
            report.accept(reportPrinterService, reportInfo);
        }
    }
}
