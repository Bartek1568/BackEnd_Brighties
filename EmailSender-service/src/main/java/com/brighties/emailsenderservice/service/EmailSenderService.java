package com.brighties.emailsenderservice.service;

import com.brighties.reservationservice.event.ReservationCreatedEvent;
import com.brighties.emailsenderservice.grpc.UserGrpcClient;
import org.springframework.stereotype.Service;
import user.UserInfoByRoleResponse;
import user.UserInfoResponse;
import user.UserServiceGrpc;

@Service
public class EmailSenderService {

    private final UserGrpcClient userGrpcClient;
    private final EmailService emailService;

    public EmailSenderService(UserGrpcClient userGrpcClient, EmailService emailService) {
        this.userGrpcClient = userGrpcClient;
        this.emailService = emailService;
    }

    public void processReservationCreatedEvent(ReservationCreatedEvent event) {
        Long studentId = event.getStudentId();
        Long teacherId = event.getTeacherId();

        if (!userGrpcClient.checkUserExistsByRole(studentId, "STUDENT")) {
            System.err.println("Student nie istnieje, ID: " + studentId);
            return;
        }

        if (!userGrpcClient.checkUserExistsByRole(teacherId, "TEACHER")) {
            System.err.println("Teacher nie istnieje, ID: " + teacherId);
            return;
        }

        UserInfoByRoleResponse student = userGrpcClient.getUserInfoByRole(studentId, "STUDENT");

        UserInfoByRoleResponse teacher = userGrpcClient.getUserInfoByRole(teacherId, "TEACHER");

        // Email do ucznia
        emailService.sendEmail(
                student.getEmail(),
                "Potwierdzenie rezerwacji",
                String.format("""
                    Cześć %s %s,

                    Twoja rezerwacja u nauczyciela %s %s została potwierdzona.
                  

                    Dane kontaktowe nauczyciela:
                    Email: %s
                    Telefon: %s

                    Pozdrawiamy,
                    Zespół Brighties
                    """,
                        student.getName(),
                        student.getSurname(),
                        teacher.getName(),
                        teacher.getSurname(),
                        teacher.getEmail(),
                        teacher.getPhone()
                )
        );

        // Email do nauczyciela
        emailService.sendEmail(
                teacher.getEmail(),
                "Nowa rezerwacja",
                String.format("""
                    Cześć %s %s,

                    Uczeń %s %s zarezerwował u Ciebie lekcję.
              

                    Dane kontaktowe ucznia:
                    Email: %s
                    Telefon: %s

                    Pozdrawiamy,
                    Zespół Brighties
                    """,
                        teacher.getName(),
                        teacher.getSurname(),
                        student.getName(),
                        student.getSurname(),
                        student.getEmail(),
                        student.getPhone()
                )
        );
    }
}
