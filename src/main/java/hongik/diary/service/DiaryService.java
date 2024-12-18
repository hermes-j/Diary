package hongik.diary.service;

import hongik.diary.entity.Diary;
import hongik.diary.entity.User;
import hongik.diary.repository.DiaryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final HttpSession session;

    // 글 작성
    public void write(Diary diary) {
        diaryRepository.save(diary);
    }

    // 글 리스트 조회, 자신이 작성한 글만.
    public Page<Diary> list(Pageable pageable) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        Long writerId = loginUser.getUid();
        return diaryRepository.findByWriterId(writerId, pageable);
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
