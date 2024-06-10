package komersa.use.service;

import komersa.dto.mapper.VisitorDtoMapper;
import komersa.dto.request.VisitorDtoRequest;
import komersa.service.VisitorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VisitorServiceTest {
    private final VisitorService visitorService;

    @Autowired
    public VisitorServiceTest(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    private static VisitorDtoRequest doe = new VisitorDtoRequest(
            "doe",
            "doe@gmail.com"
    );

    @Test
    void findAllOk() {
        assertFalse(visitorService.getAll(Pageable.unpaged()).isEmpty());
    }
    @Test
    void saveOk() {
        for (int i = 0; i < 50; i++) {
            assertNotNull(visitorService.create(VisitorDtoMapper.toModel(new VisitorDtoRequest(
                    doe.getName() + i,
                    doe.getEmail() + i
            ))));
        }
    }
}
