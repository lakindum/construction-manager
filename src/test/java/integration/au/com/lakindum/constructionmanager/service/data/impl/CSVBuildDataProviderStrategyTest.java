package integration.au.com.lakindum.constructionmanager.service.data.impl;

import au.com.lakindum.constructionmanager.exception.ConstructionManagerException;
import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.data.BuildDataProviderStrategy;
import au.com.lakindum.constructionmanager.service.data.BuildInfoParserService;
import au.com.lakindum.constructionmanager.service.data.BuildInfoSummaryGeneratorService;
import au.com.lakindum.constructionmanager.service.data.impl.BuildInfoParserServiceImpl;
import au.com.lakindum.constructionmanager.service.data.impl.BuildInfoSummaryGeneratorServiceImpl;
import au.com.lakindum.constructionmanager.service.data.impl.CSVBuildDataProviderStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class CSVBuildDataProviderStrategyTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private BuildDataProviderStrategy buildDataProviderStrategy;

    @Before
    public void setUp() throws Exception {
        BuildInfoParserService buildInfoParserService = new BuildInfoParserServiceImpl();
        BuildInfoSummaryGeneratorService buildInfoSummaryGeneratorService = new BuildInfoSummaryGeneratorServiceImpl();
        buildDataProviderStrategy = new CSVBuildDataProviderStrategy(buildInfoParserService, buildInfoSummaryGeneratorService);
    }

    @Test
    public void testGetReportInfoWithValidFileShouldPass() {
        DataExtractionInfo dataExtractionInfo = DataExtractionInfo.builder().sourceFilename("project_info.csv").pageNumber(1).pageSize(20).build();
        ReportInfo reportInfo = buildDataProviderStrategy.getBuildInfo(dataExtractionInfo);
        Assert.assertEquals(getReportInfo(), reportInfo);
    }

    @Test
    public void testGetReportInfoWithInvalidFilePathShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage("Error reading from file");
        DataExtractionInfo dataExtractionInfo = DataExtractionInfo.builder().sourceFilename("invalid_file.csv").pageNumber(1).pageSize(20).build();
        ReportInfo reportInfo = buildDataProviderStrategy.getBuildInfo(dataExtractionInfo);
        Assert.assertEquals(getReportInfo(), reportInfo);
    }

    @Test
    public void testGetReportInfoWithBlankLineShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage("Invalid csv data presentation found.");
        DataExtractionInfo dataExtractionInfo = DataExtractionInfo.builder().sourceFilename("project_info_with_blank_line.csv").pageNumber(1).pageSize(20).build();
        ReportInfo reportInfo = buildDataProviderStrategy.getBuildInfo(dataExtractionInfo);
        Assert.assertEquals(getReportInfo(), reportInfo);
    }

    private ReportInfo getReportInfo() {
        Set<Integer> customerSet1 = new HashSet<>();
        customerSet1.add(1223456);
        customerSet1.add(1233456);
        customerSet1.add(2343225);

        Set<Integer> customerSet2 = new HashSet<>();
        customerSet2.add(3244132);
        customerSet2.add(3244332);

        Set<Integer> customerSet3 = new HashSet<>();
        customerSet3.add(1223456);
        customerSet3.add(1233456);

        Set<Integer> customerSet4 = new HashSet<>();
        customerSet4.add(2343225);

        Set<Integer> buildDurationSet1 = new HashSet<>();
        buildDurationSet1.add(4322);
        buildDurationSet1.add(4122);

        Set<Integer> buildDurationSet2 = new HashSet<>();
        buildDurationSet2.add(2211);
        buildDurationSet2.add(2221);

        Set<Integer> buildDurationSet3 = new HashSet<>();
        buildDurationSet3.add(3445);

        Map<Integer, Set<Integer>> customersPerContractMap = new HashMap<>();
        customersPerContractMap.put(2345, customerSet1);
        customersPerContractMap.put(2346, customerSet2);

        Map<String, Set<Integer>> customersPerGeoZoneMap = new HashMap<>();
        customersPerGeoZoneMap.put("eu_west", customerSet2);
        customersPerGeoZoneMap.put("us_west", customerSet3);
        customersPerGeoZoneMap.put("us_east", customerSet4);

        Map<String, Set<Integer>> buildDurationsPerGeoZoneMap = new HashMap<>();
        buildDurationsPerGeoZoneMap.put("eu_west", buildDurationSet1);
        buildDurationsPerGeoZoneMap.put("us_west", buildDurationSet2);
        buildDurationsPerGeoZoneMap.put("us_east", buildDurationSet3);

        return ReportInfo.builder()
                .customersPerContractMap(customersPerContractMap)
                .customersPerGeoZoneMap(customersPerGeoZoneMap)
                .buildDurationsPerGeoZoneMap(buildDurationsPerGeoZoneMap)
                .build();
    }
}