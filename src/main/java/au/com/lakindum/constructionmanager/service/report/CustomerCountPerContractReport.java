package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;

public class CustomerCountPerContractReport extends Report {

    public CustomerCountPerContractReport(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public void accept(ReportPrinterService reportPrinterService) {
        reportPrinterService.print(this);
    }
}
