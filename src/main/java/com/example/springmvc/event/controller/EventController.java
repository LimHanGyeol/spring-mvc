package com.example.springmvc.event.controller;

import com.example.springmvc.event.domain.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring Web Mvc
 * 스프링 MVC 소개, Request Form Submit, SessionAttributes, SessionAttribute, Multi Form Submit
 * SessionAttributes 어노테이션을 사용하면 HttpSession 을 사용하고 값을 넣어주지 않아도,
 * 자동으로 세션에 값을 넣어준다.
 */
@RequiredArgsConstructor
@Controller
@SessionAttributes("event")
public class EventController {

    // private final EventService eventService;

//    @GetMapping("/events")
//    public String events(Model model) {
//        model.addAttribute("events", eventService.getEvents());
//        return "events";
//    }

    @GetMapping("/events/form/name")
    public String eventsFormName(Model model) {
        Event event = new Event(2L);
        model.addAttribute("event", event);

        return "form-name";
    }

    /**
     * database save 를 가정하여 Event 를 Mocking 한다.
     * 해당 메서드에서 list 를 get 해오는 작업의 코드를 작성하면,
     * 새로고침할 경우 요청을 다시 보낼 것이냐는 경고성 alert 를 보여준다.
     * 이런 경우를 대비하여 "Post Redirect Get" 이라는 기법을 사용한다.
     * Post 요청을 보내면 redirect 로 list 로 보내고, list 에서 미리 생성한 목록을 출력한다.
     */
    @PostMapping("/events/form/name")
    public String create(@Valid @ModelAttribute Event event,
                         BindingResult bindingResult) {
        System.out.println(event.toString());
        if (bindingResult.hasErrors()) {
            return "form-name";
        }
        return "redirect:/events/form/limit";
    }

    @GetMapping("/events/form/limit")
    public String eventsFormLimit(@ModelAttribute Event event, Model model) {
        model.addAttribute("event", event);
        return "form-limit";
    }

    @PostMapping("/events/form/limit")
    public String createFormLimit(@Valid @ModelAttribute Event event,
                                  BindingResult bindingResult,
                                  SessionStatus sessionStatus,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "form-limit";
        }
        sessionStatus.setComplete();
        redirectAttributes.addAttribute("name", event.getName());
        redirectAttributes.addAttribute("limitOfEnrollment", event.getLimitOfEnrollment());
        return "redirect:/events/list";
    }

    /**
     * 원래는 database 에서 select 해와야 한다.
     * 하지만 DB 설정을 하지 않아서 결과물 리스트를 수동으로 만들어 보여준다.
     *
     * RedirectAttributes 의 값은 @RequestParam 으로 받을 수 있다.
     * 이를 ModelAttributes 로도 받을 수 있는데, 이때 조심해야 한다.
     * SessionAttributes 에서 준 키값과 RedirectAttributes 모델을 같은 이름을 사용하면 안된다.
     * 우리는 createFormLimit 에서 session 을 Complete 하여 비웠는데,
     * RedirectAttributes 에서 같은 이름을 사용하면 해당 이름을 session 에서 찾는다.
     * 그래서 에러가 난다.
     * 이를 원활하게 사용하려면 SessionAttributes 에서 사용한 이름과 다른 이름을 부여해야 한다.
     * @RequestParam String name,
     * @RequestParam int limitOfEnrollment,
     */
    @GetMapping("/events/list")
    public String getEvents(@ModelAttribute("newEvent") Event event,
                            Model model, @SessionAttribute LocalDateTime visitTime) {
        System.out.println(visitTime);
        Event spring = new Event(1L, "spring", 10);

        List<Event> events = new ArrayList<>();
        events.add(spring);
        events.add(event);

        model.addAttribute("events", events);

        return "/list";
    }


}
