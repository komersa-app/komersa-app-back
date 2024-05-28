package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Appointment;
import komersa.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CarService carService;
    private final UserService userService;
    private final UserService userService;
    private final AppointmentService appointmentService;

    public AppointmentService(AppointmentService appointmentService, UserService userService, UserService userService, CarService carService, AppointmentRepository appointmentRepository) {
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.userService = userService;
        this.carService = carService;
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment create(Appointment appointment) {
        log.info("Appointment create: {}", appointment);
        appointment.setCar(carService.getById(appointment.getCar().getId()));
        appointment.setAdmin(userService.getById(appointment.getAdmin().getId()));
        appointment.setVisitor(userService.getById(appointment.getVisitor().getId()));
        appointment.setAppointment(appointmentService.getById(appointment.getAppointment().getId()));
        return appointmentRepository.save(appointment);
    }

    public Appointment getById(Long id) {
        log.info("Appointment get by id: {}", id);
        return appointmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Appointment with id: " + id + " does not exist"));
    }

    public Page<Appointment> getAll(Pageable pageable) {
        log.info("Appointment get all: {}", pageable);
        return appointmentRepository.findAll(pageable);
    }

    public Appointment updateById(Long id, Appointment appointment) {
        getById(id);
        appointment.setId(id);
        appointment.setCar(carService.getById(appointment.getCar().getId()));
        appointment.setAdmin(userService.getById(appointment.getAdmin().getId()));
        appointment.setVisitor(userService.getById(appointment.getVisitor().getId()));
        appointment.setAppointment(appointmentService.getById(appointment.getAppointment().getId()));
        log.info("Appointment update by id: {}", appointment);
        return appointmentRepository.save(appointment);
    }

    public Boolean deleteById(Long id) {
        log.info("Appointment delete by id: {}", id);
        appointmentRepository.deleteById(id);
        return true;
    }
}
