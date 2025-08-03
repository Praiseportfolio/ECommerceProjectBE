package com.pF.E_commerce.service.impl;

import com.pF.E_commerce.response.OcrResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OcrService {

    @Value("${azure.cognitive.endpoint}")
    private String endpoint;

    @Value("${azure.cognitive.key}")
    private String subscriptionKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public OcrResult analyzeHandwriting(MultipartFile file) {
        try {
            // 1. Send image to Azure Vision API
            RequestBody body = RequestBody.create(file.getBytes(), MediaType.parse("application/octet-stream"));
            Request request = new Request.Builder()
                    .url(endpoint + "vision/v3.2/read/analyze")
                    .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                    .addHeader("Content-Type", "application/octet-stream")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Failed to submit image: " + response.body().string());
            }

            String operationUrl = response.header("Operation-Location");

            // 2. Poll the result
            while (true) {
                Request pollRequest = new Request.Builder()
                        .url(operationUrl)
                        .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                        .get()
                        .build();

                Response pollResponse = client.newCall(pollRequest).execute();
                String json = pollResponse.body().string();
                JsonNode root = mapper.readTree(json);
                String status = root.path("status").asText();

                if ("succeeded".equalsIgnoreCase(status)) {
                    List<String> rawLines = new ArrayList<>();
                    for (JsonNode result : root.path("analyzeResult").path("readResults")) {
                        for (JsonNode line : result.path("lines")) {
                            rawLines.add(line.get("text").asText());
                        }
                    }
                    List<String> refined = refineLines(rawLines);
                    return new OcrResult(refined);
                } else if ("failed".equalsIgnoreCase(status)) {
                    throw new RuntimeException("Text recognition failed.");
                } else {
                    TimeUnit.SECONDS.sleep(1); // Wait before polling again
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Output errors to console
            return new OcrResult(List.of("Error during OCR processing: " + e.getMessage()));
        }
    }

    private List<String> refineLines(List<String> rawLines) {
        List<String> refined = new ArrayList<>();

        for (String line : rawLines) {
            if (line == null || line.trim().isEmpty()) continue;
            if (line.toLowerCase().contains("shopping")) continue;
            if (line.toLowerCase().contains("list")) continue;

            // Remove all digits
            String cleaned = line.replaceAll("\\d+", "").trim();

            if (!cleaned.isEmpty()) {
                refined.add(cleaned);
            }
        }

        return refined;
    }
}