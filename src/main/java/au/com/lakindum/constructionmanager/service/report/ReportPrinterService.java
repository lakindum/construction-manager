package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;

public interface ReportPrinterService {
    void print(CustomerCountPerContractReport customerCountPerContractReport, ReportInfo reportInfo);
    void print(CustomerCountPerGeoZoneReport customerCountPerGeoZoneReport, ReportInfo reportInfo);
    void print(CustomerListPerGeoZoneReport customerListPerGeoZoneReport, ReportInfo reportInfo);
    void print(AverageBuildDurationPerGeoZoneReport averageBuildDurationPerGeoZoneReport, ReportInfo reportInfo);
}
