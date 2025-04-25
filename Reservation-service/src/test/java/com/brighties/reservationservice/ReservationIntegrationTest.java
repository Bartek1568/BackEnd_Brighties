package com.brighties.reservationservice;

import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.grpc.StudentGrpcClient;
import com.brighties.reservationservice.grpc.TeacherGrpcClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class ReservationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private StudentGrpcClient studentGrpcClient = Mockito.mock(StudentGrpcClient.class);
    private TeacherGrpcClient teacherGrpcClient = Mockito.mock(TeacherGrpcClient.class);

    private static Long createdReservationId;

    @BeforeEach
    public void setup() {
        // Mocks for gRPC dependencies
        Mockito.when(studentGrpcClient.checkStudentExists(2L)).thenReturn(true);
        Mockito.when(teacherGrpcClient.checkTeacherExists(1L)).thenReturn(true);
    }

    @Test
    @Order(1)
    public void shouldCreateReservation() throws Exception {
        ReservationRequestDTO request = new ReservationRequestDTO();
        request.setTeacherId(1L);
        request.setStudentId(2L);
        request.setAvailabilityId(3L);
        request.setStatus("CONFIRMED");
        request.setNote("Potrzebuję pomocy z algebrą");

        Mockito.when(studentGrpcClient.checkStudentExists(2L)).thenReturn(true);
        Mockito.when(teacherGrpcClient.checkTeacherExists(1L)).thenReturn(true);

        ResultActions response = mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("CONFIRMED")))
                .andExpect(jsonPath("$.note", is("Potrzebuję pomocy z algebrą")));

        String content = response.andReturn().getResponse().getContentAsString();
        ReservationResponseDTO responseDTO = objectMapper.readValue(content, ReservationResponseDTO.class);
        createdReservationId = responseDTO.getId();
    }

    @Test
    @Order(2)
    public void shouldGetReservationById() throws Exception {
        mockMvc.perform(get("/reservation/{id}", createdReservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdReservationId.intValue())));
    }

    @Test
    @Order(3)
    public void shouldGetAllReservations() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @Order(4)
    public void shouldUpdateReservation() throws Exception {
        ReservationRequestDTO update = new ReservationRequestDTO();
        update.setTeacherId(1L);
        update.setStudentId(2L);
        update.setAvailabilityId(3L);
        update.setStatus("CANCELLED");
        update.setNote("Jednak nie dam rady");

        Mockito.when(studentGrpcClient.checkStudentExists(2L)).thenReturn(true);
        Mockito.when(teacherGrpcClient.checkTeacherExists(1L)).thenReturn(true);

        mockMvc.perform(put("/reservations/{id}", createdReservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("CANCELLED")))
                .andExpect(jsonPath("$.note", is("Jednak nie dam rady")));
    }

    @Test
    @Order(5)
    public void shouldDeleteReservation() throws Exception {
        mockMvc.perform(delete("/reservations/{id}", createdReservationId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/reservations/{id}", createdReservationId))
                .andExpect(status().isNotFound());
    }
}
