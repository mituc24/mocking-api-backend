package org.estar.apimocking.filter.chaos;

import jakarta.servlet.http.HttpServletResponse;
import org.estar.apimocking.models.MockConfiguration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class ErrorRateChaosStrategy implements ChaosStrategy{

    private final Random random = new Random();

    @Override
    public boolean apply(MockConfiguration config, HttpServletResponse response) throws IOException {
        if (config.getErrorRatePercentage() > 0) {
            int chance = random.nextInt(100); // Rolls a number from 0 to 99

            if (chance < config.getErrorRatePercentage()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Chaos Engine: Random failure injected!");
                return true;
            }
        }
        return false;

    }
}
