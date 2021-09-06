package au.com.lakindum.constructionmanager.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DataExtractionInfo {
    private final String sourceFilename;
    private final Integer pageNumber;
    private final Integer pageSize;
}