package org.estar.apimocking.filter.chaos;

import jakarta.servlet.http.HttpServletResponse;
import org.estar.apimocking.models.MockConfiguration;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LatencyChaosStrategy implements ChaosStrategy{
    @Override
    public boolean apply(MockConfiguration config, HttpServletResponse response) throws IOException {
        if (config.getLatencyMs() > 0) {
            try {
                Thread.sleep(config.getLatencyMs());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return false;
    }
}
