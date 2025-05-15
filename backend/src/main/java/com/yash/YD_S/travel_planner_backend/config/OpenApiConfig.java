package com.yash.YD_S.travel_planner_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Travel Itinerary Planner API")
                        .version("1.0")
                        .description("""
                    Backend API for planning and managing travel itineraries.

                    **Contributors:**
                    - [Yash](https://github.com/YD-S)
                    - [Charlotte](https://github.com/char-projects)

                    For more information, visit the GitHub repository or the external documentation.
                    """)
                        .termsOfService("https://github.com/YD-S/Travel_Itinerary_Planner/blob/main/LICENSE")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project GitHub Repository")
                        .url("https://github.com/YD-S/Travel_Itinerary_Planner"));
    }
}