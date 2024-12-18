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

    @GetMapping("/login")
    public String getLoginPage(HttpServletRequest request, Model model) {

        String referer = request.getHeader("Referer");
        if (referer != null && !referer.contains("/signup") && !referer.contains("/login")) {
            request.getSession().setAttribute("prevPage", referer);
        }
        model.addAttribute("login", new LoginDTO());

        return "login";
    }

    @GetMapping("/signup")
    public String getSignupPage() {
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

            return "redirect:" + (prevPage != null ? prevPage : "/diary/list");
        }

        model.addAttribute("loginError", "Invalid id or pw");
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
