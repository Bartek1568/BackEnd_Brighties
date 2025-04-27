package com.brighties.emailsenderservice.service;

import com.brighties.reservationservice.event.ReservationCreatedEvent;
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
        System.out.println("Student info: " + student);
        System.out.println("Teacher info: " + teacher);
        System.out.println("wiadomość doszla do emailsenderservice");
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
