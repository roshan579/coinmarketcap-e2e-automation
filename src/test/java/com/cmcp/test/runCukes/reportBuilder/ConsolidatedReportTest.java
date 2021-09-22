package com.cmcp.test.runCukes.reportBuilder;

import static org.junit.Assert.*;

import com.cmcp.test.framework.report.ConsolidatedReport;
import org.junit.Test;

public class ConsolidatedReportTest {

    @Test
    public void createConsolidatedReport(){
        try{
            ConsolidatedReport consolidatedReport = new ConsolidatedReport();
            consolidatedReport.copyJsonFilesFromJobs();
            consolidatedReport.getStatisticsFromJsons();
        }catch (Exception ex){
            assertTrue("Report could not be created due to : "+ex.getMessage(), false);
        }
    }
}
