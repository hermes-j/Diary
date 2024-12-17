package hongik.diary.service;

import hongik.diary.entity.Diary;
import hongik.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    // 글 작성
    public void write(Diary diary) {
        diaryRepository.save(diary);
    }

    // 글 리스트 조회
    public Page<Diary> list(Pageable pageable) {
        return diaryRepository.findAll(pageable);
    }

    // 게시글 상세보기
    public Diary view(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid post ID."));
    }

    // 글 삭제
    public void delete(Long id) {
        diaryRepository.deleteById(id);
    }
}
