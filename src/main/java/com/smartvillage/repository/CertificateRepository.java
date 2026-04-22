package com.smartvillage.repository;

import com.smartvillage.entity.Certificate;
import com.smartvillage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByUser(User user);
    Optional<Certificate> findByCertificateNumber(String certificateNumber);
    List<Certificate> findByCertificateType(String certificateType);
}
