package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;

public class AverageBuildDurationPerGeoZoneReport extends Report {

    public AverageBuildDurationPerGeoZoneReport(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public void accept(ReportPrinterService reportPrinterService) {
        reportPrinterService.print(this);
    }
}
