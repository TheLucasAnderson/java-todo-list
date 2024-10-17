package br.com.thelucasanderson.todolist.task;

import br.com.thelucasanderson.todolist.user.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {
    private UUID userId;

    @Column(length = 50)
    private String title;
    
    private String description;
    private String status;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("Title deve conter 50 caracteres no maximo");
        }

        this.title = title;
    }
}
