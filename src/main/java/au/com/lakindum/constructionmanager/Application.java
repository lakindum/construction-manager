package au.com.lakindum.constructionmanager;

import au.com.lakindum.constructionmanager.context.BuildDataContext;
import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.BuildDataProviderStrategy;
import au.com.lakindum.constructionmanager.service.BuildInfoParserService;
import au.com.lakindum.constructionmanager.service.ReportDataGeneratorService;
import au.com.lakindum.constructionmanager.service.impl.BuildInfoParserServiceImpl;
import au.com.lakindum.constructionmanager.service.impl.CSVBuildDataProviderStrategy;
import au.com.lakindum.constructionmanager.service.impl.ReportDataGeneratorServiceImpl;

public class Application {

    public static void main(String args[]) {
        System.out.println("Working !");

        /* TODO: I would prefer to use a dependency injection framework such as Spring, Guice here etc
            rather than instantiating each of the classes manually and injecting them
            in the constructors but for the purpose of this exercise I didn't use a DI framework to avoid
            using third party libs whenever possible.
         */
        BuildInfoParserService buildInfoParserService = new BuildInfoParserServiceImpl();
        ReportDataGeneratorService reportDataGeneratorService = new ReportDataGeneratorServiceImpl();
        BuildDataProviderStrategy csvBuildDataProviderStrategy = new CSVBuildDataProviderStrategy(buildInfoParserService, reportDataGeneratorService);
        BuildDataContext buildDataContext = new BuildDataContext(csvBuildDataProviderStrategy);

        //TODO: Should be executed in a loop when file size is too large
        DataExtractionInfo dataExtractionInfo = DataExtractionInfo.builder().pageNumber(1).pageSize(20).build();
        ReportInfo reportInfo = buildDataContext.executeReportDataCollector(dataExtractionInfo);

        System.out.println(reportInfo.toString());
    }
}
