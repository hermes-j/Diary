package hongik.diary.controller;

import hongik.diary.entity.Diary;
import hongik.diary.entity.User;
import hongik.diary.service.DiaryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/write") // 글 작성 화면 가져오기
    public String write() {
        return "write";
    }

    @PostMapping("/writedo") // 글을 DB에 등록
    public String writeDo(@ModelAttribute Diary diary, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 비로그인 시 리다이렉트
        }
        diary.setWriterId(loginUser.getUid());
        diaryService.write(diary);
        return "redirect:/diary/list";
    }

    @GetMapping("/list") // 글 목록 화면 (메인 화면). 페이지 형태로 작성자의 글 조회하여 반환
    public String list(Model model,
                       HttpSession session,
                       @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Diary> list = diaryService.list(pageable);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "list";
    }

    @GetMapping("/view/{id}") // 글 상세조회 화면
    public String view(Model model, @PathVariable("id") Long id, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 비로그인 시 리다이렉트
        }
        Diary diary = diaryService.view(id);
        if (!diary.getWriterId().equals(loginUser.getUid())) {
            throw new IllegalStateException("존재하지 않거나 접근 권한이 없는 포스트입니다."); // 타 사용자의 글 접근
        }
        model.addAttribute("diary", diaryService.view(id));
        return "view";
    }

    @GetMapping("/delete/{id}") // 삭제
    public String delete(@PathVariable("id") Long id, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 비로그인 시 리다이렉트
        }
        diaryService.delete(id, loginUser.getUid());
        return "redirect:/diary/list";
    }

    @GetMapping("/modify/{id}") // 글 수정 페이지
    public String modify(Model model, @PathVariable("id") Long id, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login"; // 비로그인 시 리다이렉트
        }
        Diary diary = diaryService.view(id);
        if (!diary.getWriterId().equals(loginUser.getUid())) {
            throw new IllegalStateException("존재하지 않거나 접근 권한이 없는 포스트입니다."); // 타 사용자의 글 접근
        }
        model.addAttribute("diary", diary);
        return "modify";
    }

    @PostMapping("/update/{id}") // 글 수정 POST
    public String update(@PathVariable("id") Long id, Diary diary) {
        Diary diaryTemp = diaryService.view(id);
        diaryTemp.setContent(diary.getContent());
        diaryService.write(diaryTemp);
        return "redirect:/diary/list";
    }
}
