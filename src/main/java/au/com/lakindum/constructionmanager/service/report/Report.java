package au.com.lakindum.constructionmanager.service.report;

import au.com.lakindum.constructionmanager.model.ReportInfo;
import lombok.Getter;

@Getter
public abstract class Report {
    ReportInfo reportInfo;
    abstract public void accept(ReportPrinterService reportPrinterService);
}
