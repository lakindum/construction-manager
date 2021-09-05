package au.com.lakindum.constructionmanager.service.impl;

import au.com.lakindum.constructionmanager.exception.ConstructionManagerException;
import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.BuildDataProviderStrategy;
import au.com.lakindum.constructionmanager.service.BuildInfoParserService;
import au.com.lakindum.constructionmanager.service.ReportDataGeneratorService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CSVBuildDataProviderStrategy implements BuildDataProviderStrategy {

    private final BuildInfoParserService buildInfoParserService;
    private final ReportDataGeneratorService reportDataGeneratorService;

    private static final String fileName = "project_info.csv";
    private static final String COMMA = ",";

    public CSVBuildDataProviderStrategy(BuildInfoParserService buildInfoParserService,
                                        ReportDataGeneratorService reportDataGeneratorService) {
        this.buildInfoParserService = buildInfoParserService;
        this.reportDataGeneratorService = reportDataGeneratorService;
    }

    public ReportInfo getBuildInfo(final DataExtractionInfo dataExtractionInfo) {
        Pattern pattern = Pattern.compile(COMMA);
        try {
            Stream<String> buildDataStream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName)
                    .toURI()));
            buildDataStream
                .skip(getStartIndex(dataExtractionInfo))
                .limit(dataExtractionInfo.getPageSize())
                .forEach(strBuildInfo -> {
                    String[] arrBuildInfo = pattern.split(strBuildInfo);
                    BuildInfo buildInfo = buildInfoParserService.getBuildInfo(arrBuildInfo);
                    reportDataGeneratorService.updateReportInfo(buildInfo);
                });
        } catch (NumberFormatException | ConstructionManagerException e) {
            System.out.println("File content parsing error : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("File reading error : " + e.getMessage());
        }
        return reportDataGeneratorService.getReportInfo();
    }

    private Integer getStartIndex(final DataExtractionInfo dataExtractionInfo) {
        return dataExtractionInfo.getPageSize() * (dataExtractionInfo.getPageNumber() - 1);
    }

}
