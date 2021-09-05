package au.com.lakindum.constructionmanager.service.report;

public interface ReportPrinterService {
    void print(CustomerCountPerContractReport customerCountPerContractReport);
    void print(CustomerCountPerGeoZoneReport customerCountPerGeoZoneReport);
    void print(CustomerListPerGeoZoneReport customerListPerGeoZoneReport);
    void print(AverageBuildDurationPerGeoZoneReport averageBuildDurationPerGeoZoneReport);
}
