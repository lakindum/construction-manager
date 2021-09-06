package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.report.impl.ConsoleReportPrinterServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class ReportCollectionTest {

    ReportPrinterService reportPrinterService;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUp() throws Exception {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        reportPrinterService = new ConsoleReportPrinterServiceImpl();
    }

    @Test
    public void testPrintAllReportsWithValidData() {
        Report[] arrReports = new Report[]{
                new CustomerCountPerContractReport(),
                new CustomerCountPerGeoZoneReport(),
                new CustomerListPerGeoZoneReport(),
                new AverageBuildDurationPerGeoZoneReport()};
        ReportCollection reportCollection = ReportCollection.builder().reports(arrReports).build();
        reportCollection.print(new ConsoleReportPrinterServiceImpl(), getReportInfo());
        Assert.assertTrue(getOutput().contains("==== Report Name: Number of unique customerIds for each contractId ===="));
        Assert.assertTrue(getOutput().contains("number of customerIds for contractId : 2345 is 3"));
        Assert.assertTrue(getOutput().contains("number of customerIds for contractId : 2346 is 2"));
        Assert.assertTrue(getOutput().contains("==== Report End ===="));

        Assert.assertTrue(getOutput().contains("==== Report Name: Number of unique customerIds for each geoZone ===="));
        Assert.assertTrue(getOutput().contains("number of customerIds for geoZone : eu_west is 2"));
        Assert.assertTrue(getOutput().contains("number of customerIds for geoZone : us_west is 2"));
        Assert.assertTrue(getOutput().contains("number of customerIds for geoZone : us_east is 1"));
        Assert.assertTrue(getOutput().contains("==== Report End ===="));

        Assert.assertTrue(getOutput().contains("==== Report Name: Unique customerIds for each geoZone ===="));
        Assert.assertTrue(getOutput().contains("customerIds for geoZone : eu_west"));
        Assert.assertTrue(getOutput().contains("---> 3244132"));
        Assert.assertTrue(getOutput().contains("---> 3244332"));
        Assert.assertTrue(getOutput().contains("customerIds for geoZone : us_west"));
        Assert.assertTrue(getOutput().contains("---> 1223456"));
        Assert.assertTrue(getOutput().contains("---> 1233456"));
        Assert.assertTrue(getOutput().contains("customerIds for geoZone : us_east"));
        Assert.assertTrue(getOutput().contains("---> 2343225"));
        Assert.assertTrue(getOutput().contains("==== Report End ===="));

        Assert.assertTrue(getOutput().contains("==== Report Name: Average buildDuration for each geoZone ===="));
        Assert.assertTrue(getOutput().contains("average buildDuration for geoZone eu_west is 4222.00"));
        Assert.assertTrue(getOutput().contains("average buildDuration for geoZone us_west is 2216.00"));
        Assert.assertTrue(getOutput().contains("average buildDuration for geoZone us_east is 3445.00"));
        Assert.assertTrue(getOutput().contains("==== Report End ===="));
    }

    @Test
    public void testPrintTwoReportsWithValidData() {
        Report[] arrReports = new Report[]{
                new CustomerCountPerContractReport(),
                new AverageBuildDurationPerGeoZoneReport()};
        ReportCollection reportCollection = ReportCollection.builder().reports(arrReports).build();
        reportCollection.print(new ConsoleReportPrinterServiceImpl(), getReportInfo());
        Assert.assertTrue(getOutput().contains("==== Report Name: Number of unique customerIds for each contractId ===="));
        Assert.assertTrue(getOutput().contains("number of customerIds for contractId : 2345 is 3"));
        Assert.assertTrue(getOutput().contains("number of customerIds for contractId : 2346 is 2"));
        Assert.assertTrue(getOutput().contains("==== Report End ===="));

        Assert.assertTrue(getOutput().contains("==== Report Name: Average buildDuration for each geoZone ===="));
        Assert.assertTrue(getOutput().contains("average buildDuration for geoZone eu_west is 4222.00"));
        Assert.assertTrue(getOutput().contains("average buildDuration for geoZone us_west is 2216.00"));
        Assert.assertTrue(getOutput().contains("average buildDuration for geoZone us_east is 3445.00"));
        Assert.assertTrue(getOutput().contains("==== Report End ===="));
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

    private String getOutput() {
        return testOut.toString();
    }

    @After
    public void restoreSystemInputOutput() {
        System.setOut(systemOut);
    }
}