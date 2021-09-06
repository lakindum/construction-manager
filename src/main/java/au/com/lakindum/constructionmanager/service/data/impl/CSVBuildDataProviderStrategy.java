package au.com.lakindum.constructionmanager.service.data.impl;

import au.com.lakindum.constructionmanager.exception.ConstructionManagerException;
import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.data.BuildDataProviderStrategy;
import au.com.lakindum.constructionmanager.service.data.BuildInfoParserService;
import au.com.lakindum.constructionmanager.service.data.BuildInfoSummaryGeneratorService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class CSVBuildDataProviderStrategy implements BuildDataProviderStrategy {

    private final BuildInfoParserService buildInfoParserService;
    private final BuildInfoSummaryGeneratorService buildInfoSummaryGeneratorService;

    private static final String COMMA = ",";

    public CSVBuildDataProviderStrategy(BuildInfoParserService buildInfoParserService,
                                        BuildInfoSummaryGeneratorService buildInfoSummaryGeneratorService) {
        this.buildInfoParserService = buildInfoParserService;
        this.buildInfoSummaryGeneratorService = buildInfoSummaryGeneratorService;
    }

    public ReportInfo getBuildInfo(final DataExtractionInfo dataExtractionInfo) {
        Pattern pattern = Pattern.compile(COMMA);
        try {
            Stream<String> buildDataStream = Files.lines(Paths.get(ClassLoader.getSystemResource(dataExtractionInfo.getSourceFilename())
                    .toURI()));
            buildDataStream
                .skip(getStartIndex(dataExtractionInfo))
                .limit(dataExtractionInfo.getPageSize())
                .forEach(strBuildInfo -> {
                    String[] arrBuildInfo = pattern.split(strBuildInfo);
                    BuildInfo buildInfo = buildInfoParserService.getBuildInfo(arrBuildInfo);
                    buildInfoSummaryGeneratorService.updateReportInfo(buildInfo);
                });
        } catch (NullPointerException | IOException e) {
            throw new ConstructionManagerException("Error reading from file");
        } catch (Exception e) {
            throw new ConstructionManagerException(e.getMessage());
        }
        return buildInfoSummaryGeneratorService.getReportInfo();
    }

    private Integer getStartIndex(final DataExtractionInfo dataExtractionInfo) {
        return dataExtractionInfo.getPageSize() * (dataExtractionInfo.getPageNumber() - 1);
    }

}
