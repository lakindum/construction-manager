package au.com.lakindum.constructionmanager.service.data;

import au.com.lakindum.constructionmanager.model.BuildInfo;

public interface BuildInfoParserService {
    BuildInfo getBuildInfo(String[] buildInfoArray);
}
