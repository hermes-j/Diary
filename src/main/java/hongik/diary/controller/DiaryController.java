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

    @GetMapping("/write")
    public String write() {
        return "write";
    }

    @PostMapping("/writedo")
    public String writeDo(@ModelAttribute Diary diary, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        diary.setWriterId(loginUser.getUid());
        diaryService.write(diary);
        return "redirect:/diary/list";
    }

    @GetMapping("/list")
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

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
        model.addAttribute("diary", diaryService.view(id));
        return "view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        diaryService.delete(id);
        return "redirect:/diary/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(Model model, @PathVariable("id") Long id) {
        model.addAttribute("diary", diaryService.view(id));
        return "modify";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Diary diary) {
        Diary diaryTemp = diaryService.view(id);
        diaryTemp.setContent(diary.getContent());
        diaryService.write(diaryTemp);
        return "redirect:/diary/list";
    }
}
