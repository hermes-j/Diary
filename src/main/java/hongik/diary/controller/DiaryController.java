package hongik.diary.controller;

import hongik.diary.dto.DiaryPostDto;
import hongik.diary.service.DiaryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Getter @Setter
@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity postDiary(@RequestBody @Validated DiaryPostDto diaryPostDto) {
        int pid = diaryService.createDiary(diaryPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pid);
    }
}
