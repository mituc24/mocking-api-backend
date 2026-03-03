package org.estar.apimocking.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.estar.apimocking.filter.chaos.ChaosStrategy;
import org.estar.apimocking.models.MockConfiguration;
import org.estar.apimocking.repository.MockConfigurationRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class DynamicMockRoutingFilter extends OncePerRequestFilter {
    private final MockConfigurationRepository mockRepo;
    private final List<ChaosStrategy> chaosStrategies;

    DynamicMockRoutingFilter(MockConfigurationRepository mockConfigurationRepository, List<ChaosStrategy> list)
    {
        this.mockRepo = mockConfigurationRepository;
        this.chaosStrategies = list;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        //Filter non /mock/ URI
        if(!requestURI.startsWith("/mock/"))
        {
            filterChain.doFilter(request,response);
            return;
        }

        //Extract username
        String strippedUri = requestURI.substring(6);
        int firstSlashIndex = strippedUri.indexOf('/');
        if (firstSlashIndex == -1) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Invalid request " + "'\"}");
            response.getWriter().flush();
            return;
        }

        String username = strippedUri.substring(0, firstSlashIndex);
        String mockedPath = strippedUri.substring(firstSlashIndex); // e.g., "/api/products/123"
        String method = request.getMethod();

        Optional<MockConfiguration> optionalConfig = Optional.ofNullable(
                mockRepo.findByOwnerUsernameAndMethodAndPath(username, method, mockedPath));

        if(optionalConfig.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"No mock config found for method " + method + " and path '" + mockedPath + "'\"}");
            response.getWriter().flush();
            return;
        }

        MockConfiguration config = optionalConfig.get();

        //Apply chaos
        for(ChaosStrategy chaosStrategy:chaosStrategies)
        {
            if(chaosStrategy.apply(config, response)) return;
        }

        response.setStatus(config.getStatusCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (config.getResponseBody() != null) {
            response.getWriter().write(config.getResponseBody());
            response.getWriter().flush();
        }
    }

}

