package hongik.diary.controller;

import hongik.diary.dto.LoginDTO;
import hongik.diary.entity.User;
import hongik.diary.repository.UserRepository;
import hongik.diary.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/login") // 로그인 페이지
    public String getLoginPage(HttpServletRequest request,
                               HttpSession session,
                               Model model) {
        if(session.getAttribute("loginUser") != null) { // 이미 로그인되어 있는 경우
            return "redirect:/diary/list";
        }
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/signup") && !referer.contains("/login")) {
            request.getSession().setAttribute("prevPage", referer);
        } // 이전 페이지를 기억한다.
        model.addAttribute("login", new LoginDTO());

        return "login";
    }

    @GetMapping("/signup") // 회원가입 페이지
    public String getSignupPage(HttpSession session) {
        if(session.getAttribute("loginUser") != null) { // 이미 로그인되어 있는 경우
            return "redirect:/diary/list";
        }
        return "signup";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("login") LoginDTO loginDTO,
            HttpServletRequest request,
            HttpSession session,
            Model model) {
        boolean login = userService.login(loginDTO);
        if(login) {
            String username = loginDTO.getUsername();
            User user = userRepository.findByUsername(username);
            session.setAttribute("loginUser", user);

            String prevPage = (String) request.getSession().getAttribute("prevPage");
            request.getSession().removeAttribute("prevPage");

            return "redirect:" + (prevPage != null ? prevPage : "/diary/list"); // 로그인 성공 시 기억했던 이전 페이지로 돌아옴, default는 목록 화면
        }

        model.addAttribute("loginError", "ID 또는 PW가 올바르지 않습니다.");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/login";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/login";
    }

}
