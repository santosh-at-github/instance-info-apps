package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@SpringBootApplication
public class WebAppExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppExampleApplication.class, args);
    }
}

@RestController
class InstanceInfoController {

    @GetMapping("/instance-info")
    public String getInstanceInfo() {
        String instanceId = getInstanceMetadata("instance-id");
        String instanceType = getInstanceMetadata("instance-type");
        String region = getInstanceMetadata("placement/availability-zone").replaceAll("[a-z]$", "");
	String cpuArchitecture = System.getProperty("os.arch");
	String currentTime = convertEpochToDateTimeString(System.currentTimeMillis());

        return "Instance ID: " + instanceId + "<br>" +
                "Instance Type: " + instanceType + "<br>" +
                "Region: " + region + "<br>" +
		"CPU Architecture: " + cpuArchitecture + "<br>" +
		"Current Time: " + currentTime;
    }

    private String getInstanceMetadata(String path) {
        try {
            String url = "http://169.254.169.254/latest/meta-data/" + path;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                reader.close();
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Unknown";
    }
    private String convertEpochToDateTimeString(long epochTimeMillis) {
        Instant instant = Instant.ofEpochMilli(epochTimeMillis);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }
}

