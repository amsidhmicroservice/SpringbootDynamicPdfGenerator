package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.service.PdfGenerationService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {

    private final Configuration freeMarkerConfig;

    @Override
    public byte[] generatePdf(Map<String, Object> data) throws IOException {

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Load Freemarker template
                Template template = freeMarkerConfig.getTemplate("template.ftl");

                // Process template with data model
                StringWriter stringWriter = new StringWriter();
                template.process(data, stringWriter);

                // Write template content to PDF
                String templateContent = stringWriter.toString();
                writeParagraph(contentStream, templateContent, 100, 700);
            } catch (TemplateException e) {
                log.error("Template Exception occurred {}", e.getMessage(), e);
            }

            // Save PDF to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    private void writeParagraph(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        // Replace unsupported characters
        text = text.replace('\r', ' '); // Replace carriage return with space
        text = text.replace('\n', ' '); // Replace line feed with space

        contentStream.showText(text);
        contentStream.endText();
    }
}
