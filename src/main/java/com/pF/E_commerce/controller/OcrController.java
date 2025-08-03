package com.pF.E_commerce.controller;

import com.pF.E_commerce.response.OcrResult;
import com.pF.E_commerce.service.impl.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    @PostMapping("/handwriting")
    public ResponseEntity<OcrResult> recognizeHandwriting(@RequestParam("file") MultipartFile file) {
        try {
            OcrResult result = ocrService.analyzeHandwriting(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new OcrResult(List.of("Error: " + e.getMessage())));
        }
    }
}