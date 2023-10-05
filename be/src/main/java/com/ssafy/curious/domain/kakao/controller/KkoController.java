package com.ssafy.curious.domain.kakao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/kakao")
public class KkoController {


    @RestController
    public class KKORestAPI {

        // 카카오톡 오픈빌더로 리턴할 스킬 API
        @RequestMapping(method = RequestMethod.POST, headers = {"Accept=application/json"})
        public String callAPI(@RequestBody Map<String, Object> params) {

            try {
                System.out.println("여기를 들어옴");
                ObjectMapper mapper = new ObjectMapper();
                String jsonInString = mapper.writeValueAsString(params);
                int x = 0;
                System.out.println(jsonInString);

                return "정상적으로 작동되었습니다";
            } catch (Exception e) {
                System.out.println("에러 발생 " + e);

                return "에러발생";
            }


        }
    }
}
