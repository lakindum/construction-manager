package au.com.lakindum.constructionmanager.service.data.impl;


import au.com.lakindum.constructionmanager.exception.ConstructionManagerException;
import au.com.lakindum.constructionmanager.model.BuildInfo;
import au.com.lakindum.constructionmanager.service.data.BuildInfoParserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class BuildInfoParserServiceImplTest {

    BuildInfoParserService buildInfoParserService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        buildInfoParserService = new BuildInfoParserServiceImpl();
    }

    @Test
    public void testGetBuildInfoWithValidValuesShouldPass() {
        final String[] buildInfoArray = new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445s"};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
        Assert.assertEquals(3445, (int) buildInfo.getBuildDuration());
        Assert.assertEquals(2345, (int) buildInfo.getContractId());
        Assert.assertEquals(2343225, (int) buildInfo.getCustomerId());
        Assert.assertEquals("us_east", buildInfo.getGeoZone());
        Assert.assertEquals("ProjectApple", buildInfo.getProjectCode());
        Assert.assertEquals("RedTeam", buildInfo.getTeamCode());
    }

    @Test
    public void testGetBuildInfoWithInvalidArrayLengthShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage("Invalid csv data presentation found.");
        final String[] buildInfoArray = new String[]{"2343225","2345","us_east","RedTeam","ProjectApple"};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }

    @Test
    public void testGetBuildInfoWithStringCustomerIdShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage(containsString("For input string: \"A2343225\""));
        final String[] buildInfoArray = new String[]{"A2343225","2345","us_east","RedTeam","ProjectApple","3445s"};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }

    @Test
    public void testGetBuildInfoWithNullGeoZoneShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage(containsString("Invalid value for geoZone"));
        final String[] buildInfoArray = new String[]{"2343225","2345",null,"RedTeam","ProjectApple","3445s"};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }

    @Test
    public void testGetBuildInfoWithWhiteSpaceProjectCodeShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage(containsString("Invalid value for projectCode"));
        final String[] buildInfoArray = new String[]{"2343225","2345","us_east","RedTeam"," ","3445s"};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }

    @Test
    public void testGetBuildInfoBuildDurationWithoutSPostfixShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage(containsString("Build Duration pattern is invalid"));
        final String[] buildInfoArray = new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","3445"};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }

    @Test
    public void testGetBuildInfoWithStringBuildDurationShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage(containsString("Build Duration pattern is invalid"));
        final String[] buildInfoArray = new String[]{"2343225","2345","us_east","RedTeam","ProjectApple","B3445s"};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }

    @Test
    public void testGetBuildInfoWithEmptyBuildDurationShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage(containsString("Build Duration pattern is invalid"));
        final String[] buildInfoArray = new String[]{"2343225","2345","us_east","RedTeam","ProjectApple",""};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }

    @Test
    public void testGetBuildInfoWithNullBuildDurationShouldFail() {
        expectedException.expect(ConstructionManagerException.class);
        expectedException.expectMessage(containsString("Build duration is null"));
        final String[] buildInfoArray = new String[]{"2343225","2345","us_east","RedTeam","ProjectApple",null};
        BuildInfo buildInfo = buildInfoParserService.getBuildInfo(buildInfoArray);
    }
}