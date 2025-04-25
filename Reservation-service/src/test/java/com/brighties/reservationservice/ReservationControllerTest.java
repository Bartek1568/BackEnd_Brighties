package com.brighties.reservationservice;

import com.brighties.reservationservice.controller.ReservationController;
import com.brighties.reservationservice.dto.ReservationRequestDTO;
import com.brighties.reservationservice.dto.ReservationResponseDTO;
import com.brighties.reservationservice.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationService reservationService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ReservationService reservationService() {
            return Mockito.mock(ReservationService.class);
        }
    }

    @Autowired
    private ObjectMapper objectMapper;

    private ReservationRequestDTO requestDTO;
    private ReservationResponseDTO responseDTO;

    @BeforeEach
    public void setup() {
        requestDTO = new ReservationRequestDTO();
        requestDTO.setStudentId(1L);
        requestDTO.setTeacherId(2L);
        requestDTO.setAvailabilityId(3L);
        requestDTO.setStatus("CONFIRMED");
        requestDTO.setNote("Test note");

        responseDTO = new ReservationResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setStudentId(1L);
        responseDTO.setTeacherId(2L);
        responseDTO.setAvailabilityId(3L);
        responseDTO.setStatus("CONFIRMED");
        responseDTO.setNote("Test note");
    }

    @Test
    @Order(1)
    public void createReservationTest() throws Exception {
        given(reservationService.createReservation(any(ReservationRequestDTO.class))).willReturn(responseDTO);

        ResultActions response = mockMvc.perform(post("/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.studentId", is(1)))
                .andExpect(jsonPath("$.teacherId", is(2)))
                .andExpect(jsonPath("$.status", is("CONFIRMED")));
    }

    @Test
    @Order(2)
    public void getReservationsByTeacherIdTest() throws Exception {
        given(reservationService.getReservationsByTeacherId(2L)).willReturn(List.of(responseDTO));

        ResultActions response = mockMvc.perform(get("/reservation/teacher/{teacherId}", 2L));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @Order(3)
    public void getReservationsByStudentIdTest() throws Exception {
        given(reservationService.getReservationsByStudentId(1L)).willReturn(List.of(responseDTO));

        ResultActions response = mockMvc.perform(get("/reservation/student/{studentId}", 1L));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @Order(4)
    public void getReservationsByStudentAndTeacherIdTest() throws Exception {
        given(reservationService.getReservationsByStudentIdAndTeacherId(1L, 2L)).willReturn(List.of(responseDTO));

        ResultActions response = mockMvc.perform(get("/reservation/student-teacher/{studentId}/{teacherId}", 1L, 2L));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    @Order(5)
    public void updateReservationTest() throws Exception {
        requestDTO.setNote("Updated note");
        responseDTO.setNote("Updated note");

        given(reservationService.updateReservation(eq(1L), any(ReservationRequestDTO.class)))
                .willReturn(responseDTO);

        ResultActions response = mockMvc.perform(put("/reservation/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.note", is("Updated note")));
    }

    @Test
    @Order(6)
    public void deleteReservationTest() throws Exception {
        willDoNothing().given(reservationService).deleteReservation(1L);

        ResultActions response = mockMvc.perform(delete("/reservation/{id}", 1L));

        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
