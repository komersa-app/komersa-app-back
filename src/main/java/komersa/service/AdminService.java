package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.exception.NotFoundException;
import komersa.model.Admin;
import komersa.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AdminService implements UserDetailsService {
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

    public Admin getByName(String name) {
        log.info("Admin get by name: {}", name);
        return adminRepository.findByName(name).orElseThrow(()->new EntityNotFoundException("Admin with name: " + name + " does not exist"));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByName(username).orElseThrow(() ->
                new NotFoundException(String.format("Admin does not exist, email: %s", username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getName())
                .password(admin.getPassword())
                .build();
    }
}
