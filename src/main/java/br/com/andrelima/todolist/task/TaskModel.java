package br.com.andrelima.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="td_tasks")
public class TaskModel {
    /*
     * Usuario
     * Descrição
     * Titulo
     * Data de inicio
     * Data de termino
     * Prioridade
     * Id do usuario
     */

    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;
    private String description;

    @Column(length=50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    @CreationTimestamp
    private LocalDateTime createAt;

    private UUID idUser;


}
