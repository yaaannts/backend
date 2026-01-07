package com.customs.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "declarations")
public class Declaration {

    @Id
    private String id;

    @Indexed(unique = true)
    private String declNo;

    @NotBlank(message = "Given name is required")
    private String givenName;

    @NotBlank(message = "Family name is required")
    private String familyName;

    @NotBlank(message = "Passport is required")
    @Indexed
    private String passport;

    private LocalDate dob;
    private String email;
    private String phone;

    @NotNull(message = "Arrival date is required")
    private LocalDate arrivalDate;

    @NotBlank(message = "Flight number is required")
    private String flightNo;

    private String countryFrom;
    private String entryPort;
    private Boolean cbDiplomat;

    @Valid
    private List<DeclarationItem> items;

    @NotBlank(message = "Signature is required")
    private String signedName;

    private String qrCodeData;

    @CreatedDate
    private Instant createdAt;

    // Status tracking
    private DeclarationStatus status = DeclarationStatus.PENDING;

    public enum DeclarationStatus {
        PENDING, APPROVED, FLAGGED, REJECTED
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDeclNo() { return declNo; }
    public void setDeclNo(String declNo) { this.declNo = declNo; }

    public String getGivenName() { return givenName; }
    public void setGivenName(String givenName) { this.givenName = givenName; }

    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }

    public String getPassport() { return passport; }
    public void setPassport(String passport) { this.passport = passport; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getArrivalDate() { return arrivalDate; }
    public void setArrivalDate(LocalDate arrivalDate) { this.arrivalDate = arrivalDate; }

    public String getFlightNo() { return flightNo; }
    public void setFlightNo(String flightNo) { this.flightNo = flightNo; }

    public String getCountryFrom() { return countryFrom; }
    public void setCountryFrom(String countryFrom) { this.countryFrom = countryFrom; }

    public String getEntryPort() { return entryPort; }
    public void setEntryPort(String entryPort) { this.entryPort = entryPort; }

    public Boolean getCbDiplomat() { return cbDiplomat; }
    public void setCbDiplomat(Boolean cbDiplomat) { this.cbDiplomat = cbDiplomat; }

    public List<DeclarationItem> getItems() { return items; }
    public void setItems(List<DeclarationItem> items) { this.items = items; }

    public String getSignedName() { return signedName; }
    public void setSignedName(String signedName) { this.signedName = signedName; }

    public String getQrCodeData() { return qrCodeData; }
    public void setQrCodeData(String qrCodeData) { this.qrCodeData = qrCodeData; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public DeclarationStatus getStatus() { return status; }
    public void setStatus(DeclarationStatus status) { this.status = status; }

    // Calculate total value
    public double getTotalValue() {
        if (items == null) return 0;
        return items.stream()
                .mapToDouble(item -> item.getQty() * item.getVal())
                .sum();
    }
}