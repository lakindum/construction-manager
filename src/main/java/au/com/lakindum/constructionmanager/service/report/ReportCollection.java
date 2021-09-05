package au.com.lakindum.constructionmanager.service.report;

import lombok.Builder;

@Builder
public class ReportCollection {
    Report[] reports;

    public void printReports(ReportPrinterService reportPrinterService) {
        for (Report report:reports) {
            report.accept(reportPrinterService);
        }
    }
}
