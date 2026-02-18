package com.project.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.project.entity.Certificate;
import com.project.entity.User;
import com.project.exception.ResourceNotFoundException;
import com.project.repository.CertificateRepository;
import com.project.repository.UserRepository;
import com.project.service.CertificateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String generateCertificate(Long internId) {

        // 1️⃣ Get Intern
        User intern = userRepository.findById(internId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Intern not found with id " + internId)
                );

        // 2️⃣ Prevent duplicate certificate
        if (certificateRepository.existsByIntern(intern)) {
            return "Certificate already generated";
        }

        try {

            // 3️⃣ Create folder if not exists
            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = "certificate_" + intern.getId() + ".pdf";
            String filePath = uploadDir + File.separator + fileName;

            // 4️⃣ Create PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // ===== ADD BORDER =====
            float width = pdfDocument.getDefaultPageSize().getWidth();
            float height = pdfDocument.getDefaultPageSize().getHeight();

            com.itextpdf.kernel.pdf.canvas.PdfCanvas canvas =
                    new com.itextpdf.kernel.pdf.canvas.PdfCanvas(pdfDocument.getFirstPage());

            canvas.setLineWidth(4f);
            canvas.rectangle(30, 30, width - 60, height - 60);
            canvas.stroke();

            // ===== TITLE =====
            document.add(new Paragraph("\n\nCERTIFICATE OF COMPLETION")
                    .setBold()
                    .setFontSize(32)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("This is proudly presented to")
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph(intern.getName())
                    .setBold()
                    .setFontSize(26)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            document.add(new Paragraph(
                    "For successfully completing the Internship Program\n" +
                    "with dedication and outstanding performance.")
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph("Issued on: " + LocalDate.now())
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

            // 5️⃣ Save certificate in database
            Certificate certificate = Certificate.builder()
                    .title("Internship Completion Certificate")
                    .issuedDate(LocalDate.now().toString())
                    .fileUrl(fileName)
                    .intern(intern)
                    .build();

            certificateRepository.save(certificate);

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Error generating certificate", e);
        }
    }
}