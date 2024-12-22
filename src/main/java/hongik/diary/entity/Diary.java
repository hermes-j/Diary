package hongik.diary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // primaryKey
    private Long id;
    private String content;
    private Long writerId;
    @CreationTimestamp // 자동으로 현재 시각으로 저장
    private Date date;
}
