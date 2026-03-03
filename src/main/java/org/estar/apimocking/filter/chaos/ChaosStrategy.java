package org.estar.apimocking.filter.chaos;

import jakarta.servlet.http.HttpServletResponse;
import org.estar.apimocking.models.MockConfiguration;

import java.io.IOException;

public interface ChaosStrategy {
    boolean apply(MockConfiguration config, HttpServletResponse response) throws IOException;
}
