package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Admin;
import komersa.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin create(Admin admin) {
        log.info("Admin create: {}", admin);

        return adminRepository.save(admin);
    }

    public Admin getById(Long id) {
        log.info("Admin get by id: {}", id);
        return adminRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Admin with id: " + id + " does not exist"));
    }

    public Page<Admin> getAll(Pageable pageable) {
        log.info("Admin get all: {}", pageable);
        return adminRepository.findAll(pageable);
    }

    public Admin updateById(Long id, Admin admin) {
        getById(id);
        admin.setId(id);

        log.info("Admin update by id: {}", admin);
        return adminRepository.save(admin);
    }

    public Boolean deleteById(Long id) {
        log.info("Admin delete by id: {}", id);
        adminRepository.deleteById(id);
        return true;
    }
}
