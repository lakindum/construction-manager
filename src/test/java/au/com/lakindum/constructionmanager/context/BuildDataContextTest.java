package au.com.lakindum.constructionmanager.context;

import au.com.lakindum.constructionmanager.exception.ConstructionManagerException;
import au.com.lakindum.constructionmanager.model.DataExtractionInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.data.BuildDataProviderStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BuildDataContextTest {

    @Mock
    BuildDataProviderStrategy csvBuildDataProviderStrategy;
    BuildDataContext buildDataContext;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        buildDataContext = new BuildDataContext(csvBuildDataProviderStrategy);
    }

    @Test
    public void testExecuteReportDataCollectorWithValidDetailsShouldPass() {
        DataExtractionInfo dataExtractionInfo = DataExtractionInfo.builder().sourceFilename("project_info.csv").pageSize(10).pageNumber(1).build();
        ReportInfo reportInfo = getReportInfo();
        when(csvBuildDataProviderStrategy.getBuildInfo(dataExtractionInfo)).thenReturn(reportInfo);
        Assert.assertEquals(reportInfo, buildDataContext.executeReportDataCollector(dataExtractionInfo));
    }

    @Test
    public void testExecuteReportDataCollectorWithNullDataExtractionInfoShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage("Invalid dataExtractionInfo details");
        DataExtractionInfo dataExtractionInfo = null;
        buildDataContext.executeReportDataCollector(dataExtractionInfo);
    }

    private ReportInfo getReportInfo() {
        Set<Integer> customerSet1 = new HashSet<>();
        customerSet1.add(1);
        customerSet1.add(2);
        customerSet1.add(3);

        Set<Integer> customerSet2 = new HashSet<>();
        customerSet2.add(4);
        customerSet2.add(5);

        Set<Integer> buildDurationSet1 = new HashSet<>();
        buildDurationSet1.add(450);
        buildDurationSet1.add(550);

        Set<Integer> buildDurationSet2 = new HashSet<>();
        buildDurationSet2.add(650);
        buildDurationSet2.add(750);

        Map<Integer, Set<Integer>> customersPerContractMap = new HashMap<>();
        customersPerContractMap.put(100, customerSet1);
        customersPerContractMap.put(200, customerSet2);

        Map<String, Set<Integer>> customersPerGeoZoneMap = new HashMap<>();
        customersPerGeoZoneMap.put("us-west", customerSet1);
        customersPerGeoZoneMap.put("us-east", customerSet2);

        Map<String, Set<Integer>> buildDurationsPerGeoZoneMap = new HashMap<>();
        buildDurationsPerGeoZoneMap.put("us-west", buildDurationSet1);
        buildDurationsPerGeoZoneMap.put("eu-west", buildDurationSet2);

        return ReportInfo.builder()
            .customersPerContractMap(customersPerContractMap)
            .customersPerGeoZoneMap(customersPerGeoZoneMap)
            .buildDurationsPerGeoZoneMap(buildDurationsPerGeoZoneMap)
            .build();
    }
}