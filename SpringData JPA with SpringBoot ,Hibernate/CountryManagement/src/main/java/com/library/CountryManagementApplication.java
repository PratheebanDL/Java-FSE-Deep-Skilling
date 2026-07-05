package com.library;

import com.library.model.Country;
import com.library.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CountryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountryManagementApplication.class, args);
    }

    /**
     * CommandLineRunner exercises all 5 CountryService features once the
     * app has started, so you can see them working in the console output.
     */
    @Bean
    public CommandLineRunner demo(CountryService countryService) {
        return args -> {

            System.out.println("\n=== 1. Find a country based on country code ===");
            Country india = countryService.findByCode("IN");
            System.out.println(india.getCoCode() + " -> " + india.getCoName());

            System.out.println("\n=== 2. Add new country ===");
            Country added = countryService.addCountry("XX", "Testland");
            System.out.println("Added: " + added.getCoCode() + " -> " + added.getCoName());

            System.out.println("\n=== 3. Update country ===");
            Country updated = countryService.updateCountry("XX", "Testlandia");
            System.out.println("Updated: " + updated.getCoCode() + " -> " + updated.getCoName());

            System.out.println("\n=== 4. Delete country ===");
            countryService.deleteCountry("XX");
            System.out.println("Deleted country with code XX");

            System.out.println("\n=== 5. Find countries matching partial name (\"stan\") ===");
            List<Country> matches = countryService.findByPartialName("stan");
            matches.forEach(c -> System.out.println(c.getCoCode() + " -> " + c.getCoName()));
        };
    }
}
