package com.brighties.emailsenderservice.service;

import com.brighties.emailsenderservice.event.ReservationCreatedEvent;
import com.brighties.emailsenderservice.grpc.StudentGrpcClient;
import com.brighties.emailsenderservice.grpc.TeacherGrpcClient;
import org.springframework.stereotype.Service;
import student.StudentInfoResponse;
import teacher.TeacherInfoResponse;

@Service
public class EmailSenderService {

    private final StudentGrpcClient studentGrpcClient;
    private final TeacherGrpcClient teacherGrpcClient;
    private final EmailService emailService;

    public EmailSenderService(StudentGrpcClient studentGrpcClient,
                              TeacherGrpcClient teacherGrpcClient,
                              EmailService emailService) {
        this.studentGrpcClient = studentGrpcClient;
        this.teacherGrpcClient = teacherGrpcClient;
        this.emailService = emailService;
    }

    public void processReservationCreatedEvent(ReservationCreatedEvent event) {
        StudentInfoResponse student = studentGrpcClient.getStudentInfo(event.getStudentId());
        TeacherInfoResponse teacher = teacherGrpcClient.getTeacherInfo(event.getTeacherId());

        if (!student.getExists()) {
            System.err.println("Student nie istnieje, ID: " + event.getStudentId());
            return;
        }

        // Email do ucznia
        emailService.sendEmail(
                student.getEmail(),
                "Potwierdzenie rezerwacji",
                String.format("""
                    Cześć %s %s,

                    Twoja rezerwacja u nauczyciela %s %s została potwierdzona.
                    Data: %s
                    Godzina: %s - %s

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
                        event.getDate(),
                        event.getStartTime(),
                        event.getEndTime(),
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
                    Data: %s
                    Godzina: %s - %s

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
                        event.getDate(),
                        event.getStartTime(),
                        event.getEndTime(),
                        student.getEmail(),
                        student.getPhone()
                )
        );
    }
}
