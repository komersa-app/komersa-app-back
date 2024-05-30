package komersa.use.service;

import komersa.dto.mapper.AdminDtoMapper;
import komersa.dto.request.AdminDtoRequest;
import komersa.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AdminServiceTest {
    private final AdminService adminService;

    @Autowired
    public AdminServiceTest(AdminService adminService) {
        this.adminService = adminService;
    }

    private static AdminDtoRequest john = new AdminDtoRequest(
            "john",
            "john@gmail.com",
            "password"
    );

    @Test
    void findAllOk() {
        assertFalse(adminService.getAll(Pageable.unpaged()).isEmpty());
    }
    @Test
    void saveAdminOk() {
        for (int i = 0; i < 50; i++) {
            assertNotNull(adminService.create(AdminDtoMapper.toModel(new AdminDtoRequest(
                    john.getName() + i,
                    john.getEmail() + i,
                    john.getPassword() + i

            ))));
        }
    }
}
