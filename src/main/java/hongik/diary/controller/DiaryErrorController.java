package hongik.diary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Collection;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DiaryErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;
    private final WebRequest webRequest;

    @RequestMapping("/error") // 에러 발생 시 /error 페이지에 메시지 전달
    public String error(WebRequest request, Model model) {
        // 에러 속성
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));

        String message = errorAttributes.values().toString();
        // 메시지 설정
        model.addAttribute("errorMessage", message);
        return "error";
    }
}
