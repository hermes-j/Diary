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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 접근 권한이 없는 포스트입니다.")); // 없는 글
    }

    // 글 삭제
    public void delete(Long id, Long uid) {
        Diary diary = diaryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않거나 접근 권한이 없는 포스트입니다.")); // 없는 글

        // validation
        if(!diary.getWriterId().equals(uid)) {
            throw new IllegalStateException("존재하지 않거나 접근 권한이 없는 포스트입니다."); // 타 사용자의 글
        }
        // 두 경우의 오류 메시지를 통일하여 포스트의 존재 여부를 파악할 수 없도록 함
        diaryRepository.deleteById(id);
    }
}
