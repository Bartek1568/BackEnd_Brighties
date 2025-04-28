package com.brighties.reservationservice;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.exception.AvailabilitySlotIsAlreadyReservedException;
import com.brighties.reservationservice.exception.StudentNotFoundException;
import com.brighties.reservationservice.exception.TeacherNotFoundException;
import com.brighties.reservationservice.grpc.AvailabilityGrpcClient;
import com.brighties.reservationservice.model.Reservation;
import com.brighties.reservationservice.repository.ReservationRepository;
import com.brighties.reservationservice.service.ReservationEventPublisher;
import com.brighties.reservationservice.service.ReservationService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private StudentGrpcClient studentGrpcClient;

    @Mock
    private TeacherGrpcClient teacherGrpcClient;

    @Mock
    private AvailabilityGrpcClient availabilityGrpcClient;

    @Mock
    private ReservationEventPublisher eventPublisher;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private ReservationRequestDTO reservationRequestDTO;

    @BeforeEach
    public void setup() {
        reservation = Reservation.builder()
                .id(1L)
                .studentId(1L)
                .teacherId(2L)
                .availabilitySlotId(3L)
                .status("CONFIRMED")
                .note("Needs help with math")
                .build();

        reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setStudentId(1L);
        reservationRequestDTO.setTeacherId(2L);
        reservationRequestDTO.setAvailabilityId(3L);
        reservationRequestDTO.setStatus("CONFIRMED");
        reservationRequestDTO.setNote("Needs help with math");
    }

    @Test
    @Order(1)
    public void createReservation_shouldReturnResponseDTO() {
        // given
        given(studentGrpcClient.checkStudentExists(1L)).willReturn(true);
        given(teacherGrpcClient.checkTeacherExists(2L)).willReturn(true);
        given(availabilityGrpcClient.checkSlotAvailable(3L)).willReturn(true);
        given(reservationRepository.save(any(Reservation.class))).willReturn(reservation);

        // when
        ReservationResponseDTO result = reservationService.createReservation(reservationRequestDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getTeacherId()).isEqualTo(2L);
    }

    @Test
    @Order(2)
    public void createReservation_shouldThrowIfStudentNotFound() {
        // given
        given(studentGrpcClient.checkStudentExists(1L)).willReturn(false);

        // then
        assertThatThrownBy(() -> reservationService.createReservation(reservationRequestDTO))
                .isInstanceOf(StudentNotFoundException.class);
    }

    @Test
    @Order(3)
    public void createReservation_shouldThrowIfTeacherNotFound() {
        // given
        given(studentGrpcClient.checkStudentExists(1L)).willReturn(true);
        given(teacherGrpcClient.checkTeacherExists(2L)).willReturn(false);

        // then
        assertThatThrownBy(() -> reservationService.createReservation(reservationRequestDTO))
                .isInstanceOf(TeacherNotFoundException.class);
    }

    @Test
    @Order(4)
    public void createReservation_shouldThrowIfSlotUnavailable() {
        // given
        given(studentGrpcClient.checkStudentExists(1L)).willReturn(true);
        given(teacherGrpcClient.checkTeacherExists(2L)).willReturn(true);
        given(availabilityGrpcClient.checkSlotAvailable(3L)).willReturn(false);

        // then
        assertThatThrownBy(() -> reservationService.createReservation(reservationRequestDTO))
                .isInstanceOf(AvailabilitySlotIsAlreadyReservedException.class);
    }

    @Test
    @Order(5)
    public void deleteReservation_shouldCallRepository() {
        // given
        willDoNothing().given(reservationRepository).deleteById(1L);

        // when
        reservationService.deleteReservation(1L);

        // then
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    @Order(6)
    public void updateReservation_shouldReturnUpdatedDTO() {
        // given
        given(reservationRepository.findById(1L)).willReturn(Optional.of(reservation));
        reservationRequestDTO.setNote("Updated note");
        reservation.setNote("Updated note");
        given(reservationRepository.save(any())).willReturn(reservation);

        // when
        ReservationResponseDTO updated = reservationService.updateReservation(1L, reservationRequestDTO);

        // then
        assertThat(updated).isNotNull();
        assertThat(updated.getNote()).isEqualTo("Updated note");
    }

    @Test
    @Order(7)
    public void getReservationsByStudentId_shouldReturnList() {
        // given
        given(reservationRepository.findByStudentId(1L)).willReturn(List.of(reservation));

        // when
        List<ReservationResponseDTO> reservations = reservationService.getReservationsByStudentId(1L);

        // then
        assertThat(reservations).isNotEmpty();
        assertThat(reservations.get(0).getStudentId()).isEqualTo(1L);
    }

    @Test
    @Order(8)
    public void getReservationsByTeacherId_shouldReturnList() {
        // given
        given(reservationRepository.findByTeacherId(2L)).willReturn(List.of(reservation));

        // when
        List<ReservationResponseDTO> reservations = reservationService.getReservationsByTeacherId(2L);

        // then
        assertThat(reservations).isNotEmpty();
        assertThat(reservations.get(0).getTeacherId()).isEqualTo(2L);
    }

    @Test
    @Order(9)
    public void getReservationsByStudentIdAndTeacherId_shouldReturnList() {
        // given
        given(reservationRepository.findByStudentIdAndTeacherId(1L, 2L)).willReturn(List.of(reservation));

        // when
        List<ReservationResponseDTO> reservations = reservationService.getReservationsByStudentIdAndTeacherId(1L, 2L);

        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getStudentId()).isEqualTo(1L);
        assertThat(reservations.get(0).getTeacherId()).isEqualTo(2L);
    }
}
