package au.com.lakindum.constructionmanager;

import au.com.lakindum.constructionmanager.context.BuildDataContext;
import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.data.BuildDataProviderStrategy;
import au.com.lakindum.constructionmanager.service.data.BuildInfoParserService;
import au.com.lakindum.constructionmanager.service.data.BuildInfoSummaryGeneratorService;
import au.com.lakindum.constructionmanager.service.data.impl.BuildInfoParserServiceImpl;
import au.com.lakindum.constructionmanager.service.data.impl.BuildInfoSummaryGeneratorServiceImpl;
import au.com.lakindum.constructionmanager.service.data.impl.CSVBuildDataProviderStrategy;
import au.com.lakindum.constructionmanager.service.report.*;
import au.com.lakindum.constructionmanager.service.report.impl.ConsoleReportPrinterServiceImpl;

public class BuildReportGenerator {

    public static void main(String[] args) {
        BuildReportGenerator buildReportGenerator = new BuildReportGenerator();
        try {
            ReportInfo reportInfo = buildReportGenerator.collectData();
            buildReportGenerator.generateReport(reportInfo);
        } catch (Exception e) {
            System.out.println("ERROR FOUND: ");
            System.out.println(e.getMessage());
        }
    }

    private ReportInfo collectData() {
        /* TODO: I would prefer to use a dependency injection framework such as Spring, Guice here etc
            rather than instantiating each of the classes manually and injecting them
            in to the constructors, but for the purpose of this exercise I didn't use a DI framework to avoid
            using third party libs whenever possible.
         */
        BuildInfoParserService buildInfoParserService = new BuildInfoParserServiceImpl();
        BuildInfoSummaryGeneratorService buildInfoSummaryGeneratorService = new BuildInfoSummaryGeneratorServiceImpl();
        BuildDataProviderStrategy csvBuildDataProviderStrategy = new CSVBuildDataProviderStrategy(buildInfoParserService, buildInfoSummaryGeneratorService);
        BuildDataContext buildDataContext = new BuildDataContext(csvBuildDataProviderStrategy);

        //TODO: Should be executed in a loop when file size is too large
        DataExtractionInfo dataExtractionInfo = DataExtractionInfo.builder().pageNumber(1).pageSize(20).build();
        return buildDataContext.executeReportDataCollector(dataExtractionInfo);
    }

    private void generateReport(ReportInfo reportInfo) {
        Report[] arrReports = new Report[]{
                new CustomerCountPerContractReport(),
                new CustomerCountPerGeoZoneReport(),
                new CustomerListPerGeoZoneReport(),
                new AverageBuildDurationPerGeoZoneReport()};
        ReportCollection reportCollection = ReportCollection.builder().reports(arrReports).build();
        reportCollection.print(new ConsoleReportPrinterServiceImpl(), reportInfo);
    }
}
