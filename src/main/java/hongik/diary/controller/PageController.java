package hongik.diary.controller;

import hongik.diary.service.DiaryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Getter @Setter
@Controller
@RequiredArgsConstructor
public class PageController {
    private final DiaryService diaryService;

    @GetMapping("/login")
    public String getLoginPage() { return "login"; }

    @GetMapping("/main")
    public String getMainPage() { return "main"; }

    @GetMapping("/mypage")
    public String getMyPage() { return "mypage"; }

    @GetMapping("/signup")
    public String getSignupPage() { return "signup"; }
}
