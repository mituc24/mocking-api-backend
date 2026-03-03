package org.estar.apimocking.importer;

import org.estar.apimocking.models.MockConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ApiImportStrategy {
    String getFormatName();
    List<MockConfiguration> parse(MultipartFile file, String username) throws IOException;
}
