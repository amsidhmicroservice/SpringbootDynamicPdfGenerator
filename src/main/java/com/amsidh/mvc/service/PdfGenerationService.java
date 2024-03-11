package com.amsidh.mvc.service;

import java.io.IOException;
import java.util.Map;

public interface PdfGenerationService {
    public byte[] generatePdf(Map<String, Object> data) throws IOException;
}
