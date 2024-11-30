package hongik.diary.service;

import hongik.diary.dto.DiaryPostDto;
import hongik.diary.entity.Diary;
import hongik.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public int createDiary(DiaryPostDto diaryPostDto) {
        Diary diary = new Diary();
        diary.setContent(diaryPostDto.getContent());

        return diaryRepository.save(diary).getPid();
    }
}
