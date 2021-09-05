package au.com.lakindum.constructionmanager.service;

import au.com.lakindum.constructionmanager.model.BuildInfo;

public interface BuildInfoParserService {
    BuildInfo getBuildInfo(String[] buildInfoArray);
}
