package com.pF.E_commerce.repository;

import com.pF.E_commerce.modal.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findFirstByEmail(String email);

    // Get the most recent OTP for an email (recommended)
    @Query("SELECT v FROM VerificationCode v WHERE v.email = :email ORDER BY v.createdAt DESC")
    List<VerificationCode> findByEmailOrderByCreatedAtDesc(@Param("email") String email);

    // Get the latest OTP directly (alternative approach)
//    @Query("SELECT * FROM VerificationCode v WHERE v.email = :email ORDER BY v.createdAt DESC LIMIT 1")
//    Optional<VerificationCode> findLatestByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM verification_code WHERE email = :email ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<VerificationCode> findLatestByEmail(@Param("email") String email);

    // Clean up old OTPs for an email
    @Modifying
    @Transactional
    @Query("DELETE FROM VerificationCode v WHERE v.email = :email")
    void deleteByEmail(@Param("email") String email);
    VerificationCode findByEmail(String email);
    VerificationCode findByOtp (String otp);
}
