package com.beyblade.scoreboard.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.Collections;

@Configuration
public class GoogleCredentialsConfig {

    @Bean
    public Sheets getSheets() throws Exception {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new ClassPathResource("credentials.json").getInputStream()
        ).createScoped(Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("SpringBoot-GoogleSheet")
                .build();
    }
}
