package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;

public class CustomerListPerGeoZoneReport implements Report {

    public void accept(ReportPrinterService reportPrinterService, ReportInfo reportInfo) {
        reportPrinterService.print(this, reportInfo);
    }
}
