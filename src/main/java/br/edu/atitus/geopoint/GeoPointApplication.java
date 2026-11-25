package br.edu.atitus.geopoint;

import br.edu.atitus.geopoint.controllers.PointController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = PointController.class
        )
)
public class GeoPointApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeoPointApplication.class, args);
    }

}
