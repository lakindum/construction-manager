package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CustomerListPerGeoZoneReport extends Report {

    public CustomerListPerGeoZoneReport(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public void accept(ReportPrinterService reportPrinterService) {
        reportPrinterService.print(this);
    }
}
