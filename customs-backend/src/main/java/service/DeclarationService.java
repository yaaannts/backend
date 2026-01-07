package com.customs.service;

import com.customs.dto.SubmitResponse;
import com.customs.model.Declaration;
import com.customs.repository.DeclarationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeclarationService {

    private static final Logger log = LoggerFactory.getLogger(DeclarationService.class);

    private final DeclarationRepository repository;
    private final QRCodeService qrCodeService;

    public DeclarationService(DeclarationRepository repository, QRCodeService qrCodeService) {
        this.repository = repository;
        this.qrCodeService = qrCodeService;
    }

    public SubmitResponse submitDeclaration(Declaration declaration) {
        log.info("Processing declaration for passport: {}", maskPassport(declaration.getPassport()));

        // Generate unique declaration number
        String declNo = generateDeclarationNumber();
        declaration.setDeclNo(declNo);
        declaration.setCreatedAt(Instant.now());

        // Generate QR code content
        String qrContent = buildQRContent(declaration);
        String qrImage = qrCodeService.generateQRCodeBase64(qrContent);
        declaration.setQrCodeData(qrImage);

        // Risk assessment (simplified)
        if (declaration.getTotalValue() > 10000) {
            declaration.setStatus(Declaration.DeclarationStatus.FLAGGED);
            log.warn("Declaration {} flagged: high value items", declNo);
        }

        // Save to database
        Declaration saved = repository.save(declaration);
        log.info("Declaration {} saved successfully", declNo);

        return new SubmitResponse(true, saved.getDeclNo(), qrImage, "Declaration submitted successfully");
    }

    public Optional<Declaration> findByDeclNo(String declNo) {
        return repository.findByDeclNo(declNo);
    }

    private String generateDeclarationNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String uniquePart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "CD-" + datePart + "-" + uniquePart;
    }

    private String buildQRContent(Declaration decl) {
        return String.format(
            "CUSTOMS-DECL|%s|%s|%s %s|%s|%.2f",
            decl.getDeclNo(),
            decl.getPassport(),
            decl.getGivenName(),
            decl.getFamilyName(),
            decl.getArrivalDate(),
            decl.getTotalValue()
        );
    }

    private String maskPassport(String passport) {
        if (passport == null || passport.length() < 4) return "****";
        return passport.substring(0, 2) + "****" + passport.substring(passport.length() - 2);
    }
}