package au.com.lakindum.constructionmanager.service.data.impl;

import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.data.BuildInfoSummaryGeneratorService;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class BuildInfoSummaryGeneratorServiceImplTest {

    BuildInfoSummaryGeneratorService buildInfoSummaryGeneratorService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testUpdateReportInfoWithValuesShouldPass() {
        buildInfoSummaryGeneratorService = new BuildInfoSummaryGeneratorServiceImpl();
        feedBuildInfo();
        Assert.assertNotNull(buildInfoSummaryGeneratorService.getReportInfo());
    }

    @Test
    public void testGetReportInfoWithValidValuesShouldPass() {
        buildInfoSummaryGeneratorService = new BuildInfoSummaryGeneratorServiceImpl();
        feedBuildInfo();
        ReportInfo reportInfo = buildInfoSummaryGeneratorService.getReportInfo();
        Assert.assertEquals(getExpectedReportInfo(), reportInfo);
    }

    @Test
    public void testGetReportInfoWithDuplicatedCustomerIdsForContractIdShouldShowOneCustomerId() {
        buildInfoSummaryGeneratorService = new BuildInfoSummaryGeneratorServiceImpl();
        BuildInfo buildInfo1 = BuildInfo.builder().buildDuration(3445).projectCode("ProjectApple")
                .teamCode("RedTeam").geoZone("us_east").customerId(1000).contractId(2345).build();
        BuildInfo buildInfo2 = BuildInfo.builder().buildDuration(2211).projectCode("ProjectBanana")
                .teamCode("BlueTeam").geoZone("us_west").customerId(1000).contractId(2345).build();
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo1);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo2);
        ReportInfo reportInfo = buildInfoSummaryGeneratorService.getReportInfo();
        Assert.assertEquals(1, reportInfo.getCustomersPerContractMap().get(2345).size());
    }

    @Test
    public void testGetReportInfoWithDuplicatedCustomerIdsForGeoZoneShouldShowOneCustomerId() {
        buildInfoSummaryGeneratorService = new BuildInfoSummaryGeneratorServiceImpl();
        BuildInfo buildInfo1 = BuildInfo.builder().buildDuration(3445).projectCode("ProjectApple")
                .teamCode("RedTeam").geoZone("us_east").customerId(1000).contractId(2345).build();
        BuildInfo buildInfo2 = BuildInfo.builder().buildDuration(2211).projectCode("ProjectBanana")
                .teamCode("BlueTeam").geoZone("us_east").customerId(1000).contractId(2346).build();
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo1);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo2);
        ReportInfo reportInfo = buildInfoSummaryGeneratorService.getReportInfo();
        Assert.assertEquals(1, reportInfo.getCustomersPerGeoZoneMap().get("us_east").size());
    }

    @Test
    public void testGetReportInfoWithBuildDurationsPerGeoZone() {
        buildInfoSummaryGeneratorService = new BuildInfoSummaryGeneratorServiceImpl();
        BuildInfo buildInfo1 = BuildInfo.builder().buildDuration(1511).projectCode("ProjectApple")
                .teamCode("RedTeam").geoZone("us_east").customerId(1000).contractId(2345).build();
        BuildInfo buildInfo2 = BuildInfo.builder().buildDuration(1500).projectCode("ProjectBanana")
                .teamCode("BlueTeam").geoZone("us_east").customerId(1000).contractId(2346).build();
        BuildInfo buildInfo3 = BuildInfo.builder().buildDuration(2000).projectCode("ProjectCarrot")
                .teamCode("YellowTeam3").geoZone("eu_west").customerId(3244332).contractId(2346).build();
        BuildInfo buildInfo4 = BuildInfo.builder().buildDuration(2201).projectCode("ProjectDate")
                .teamCode("BlueTeam").geoZone("eu_west").customerId(1233456).contractId(2345).build();

        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo1);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo2);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo3);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo4);
        ReportInfo reportInfo = buildInfoSummaryGeneratorService.getReportInfo();
        Assert.assertEquals(2, reportInfo.getBuildDurationsPerGeoZoneMap().get("us_east").size());
        Assert.assertEquals(2, reportInfo.getBuildDurationsPerGeoZoneMap().get("eu_west").size());
    }

    private void feedBuildInfo() {
        BuildInfo buildInfo1 = BuildInfo.builder().buildDuration(3445).projectCode("ProjectApple")
                .teamCode("RedTeam").geoZone("us_east").customerId(2343225).contractId(2345).build();

        BuildInfo buildInfo2 = BuildInfo.builder().buildDuration(2211).projectCode("ProjectBanana")
                .teamCode("BlueTeam").geoZone("us_west").customerId(1223456).contractId(2345).build();

        BuildInfo buildInfo3 = BuildInfo.builder().buildDuration(4322).projectCode("ProjectCarrot")
                .teamCode("YellowTeam3").geoZone("eu_west").customerId(3244332).contractId(2346).build();

        BuildInfo buildInfo4 = BuildInfo.builder().buildDuration(2221).projectCode("ProjectDate")
                .teamCode("BlueTeam").geoZone("us_west").customerId(1233456).contractId(2345).build();

        BuildInfo buildInfo5 = BuildInfo.builder().buildDuration(4122).projectCode("ProjectEgg")
                .teamCode("YellowTeam3").geoZone("eu_west").customerId(3244132).contractId(2346).build();
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo1);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo2);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo3);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo4);
        buildInfoSummaryGeneratorService.updateReportInfo(buildInfo5);
    }

    private ReportInfo getExpectedReportInfo() {
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