package au.com.lakindum.constructionmanager.service.report.impl;

import au.com.lakindum.constructionmanager.model.ReportInfo;
import au.com.lakindum.constructionmanager.service.report.*;

import java.math.BigDecimal;

public class ConsoleReportPrinterServiceImpl implements ReportPrinterService {

    public void print(CustomerCountPerContractReport customerCountPerContractReport, ReportInfo reportInfo) {
        System.out.println("==== Report Name: Number of unique customerIds for each contractId ====");
        reportInfo
            .getCustomersPerContractMap()
            .forEach((contractId, customerIds) -> {
                System.out.println("number of customerIds for contractId : " + contractId + " is " + customerIds.size());
            });
        System.out.println("==== Report End ====");
        System.out.println("");
    }

    public void print(CustomerCountPerGeoZoneReport customerCountPerGeoZoneReport, ReportInfo reportInfo) {
        System.out.println("==== Report Name: Number of unique customerIds for each geoZone ====");
        reportInfo
            .getCustomersPerGeoZoneMap()
            .forEach((geoZone, customerIds) -> {
                System.out.println("number of customerIds for geoZone : " + geoZone + " is " + customerIds.size());
            });
        System.out.println("==== Report End ====");
        System.out.println("");
    }

    public void print(CustomerListPerGeoZoneReport customerListPerGeoZoneReport, ReportInfo reportInfo) {
        System.out.println("==== Report Name: Unique customerIds for each geoZone ====");
        reportInfo
            .getCustomersPerGeoZoneMap()
            .forEach((geoZone, customerIds) -> {
                System.out.println("customerIds for geoZone : " + geoZone);
                for (Integer customerId : customerIds) {
                    System.out.println("---> " + customerId);
                }
            });
        System.out.println("==== Report End ====");
        System.out.println("");
    }

    public void print(AverageBuildDurationPerGeoZoneReport averageBuildDurationPerGeoZoneReport, ReportInfo reportInfo) {
        System.out.println("==== Report Name: Average buildDuration for each geoZone ====");
        reportInfo
            .getBuildDurationsPerGeoZoneMap()
            .forEach((geoZone, buildDurations) -> {
                BigDecimal totalBuildDuration = new BigDecimal(0);
                for (Integer buildDuration : buildDurations) {
                    totalBuildDuration = totalBuildDuration.add(new BigDecimal(buildDuration));
                }
                BigDecimal averageBuildDuration = totalBuildDuration.divide(new BigDecimal(buildDurations.size()), 2, BigDecimal.ROUND_HALF_UP);
                System.out.println("average buildDuration for geoZone " + geoZone + " is " + averageBuildDuration);
            });
        System.out.println("==== Report End ====");
    }
}
