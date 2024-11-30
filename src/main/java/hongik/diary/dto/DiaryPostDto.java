package hongik.diary.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiaryPostDto {
    @NotEmpty
    private String content;
}
