package hongik.diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiaryApplication {
    /*
    @SpringBootApplication 어노테이션으로 인해 스프링부트, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
    여기부터 설정을 읽어나가므로 항상 최상단에 위치시킬 것
     */
    public static void main(String[] args) {
        SpringApplication.run(DiaryApplication.class, args); // 내장 WAS 실행, 항상 같은 환경에서의 배포 목적
    }

}
