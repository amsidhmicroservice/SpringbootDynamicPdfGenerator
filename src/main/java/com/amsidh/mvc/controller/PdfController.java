package com.amsidh.mvc.controller;

import com.amsidh.mvc.service.impl.PdfGenerationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PdfController {

    private final PdfGenerationServiceImpl pdfGenerationService;

    @GetMapping(value = "/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE + ";charset=UTF-8")
    public ByteArrayResource generatePdf() throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "John Doe");
        data.put("age", 30);
        return new ByteArrayResource(pdfGenerationService.generatePdf(data));
    }
}

