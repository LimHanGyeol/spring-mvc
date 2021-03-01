package com.example.springmvc.event.controller;

import com.example.springmvc.event.domain.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring Web Mvc
 * 스프링 MVC 소개, Request Form Submit
 */
@RequiredArgsConstructor
@Controller
public class EventController {

    // private final EventService eventService;

//    @GetMapping("/events")
//    public String events(Model model) {
//        model.addAttribute("events", eventService.getEvents());
//        return "events";
//    }

    @GetMapping("/events/form")
    public String eventForm(Model model) {
        model.addAttribute("event", new Event(1L, "spring", 50));
        return "form";
    }

    @PostMapping("/events")
    public String create(@Valid @ModelAttribute Event event,
                         BindingResult bindingResult,
                         Model model) {
        System.out.println(event.toString());
        if (bindingResult.hasErrors()) {
            return "form";
        }

        // database save 를 가정하여 Event 를 Mocking 한다.
        // 해당 메서드에서 list 를 get 해오는 작업의 코드를 작성하면,
        // 새로고침할 경우 요청을 다시 보낼 것이냐는 경고성 alert 를 보여준다.
        // 이런 경우를 대비하여 "Post Redirect Get" 이라는 기법을 사용한다.
        // Post 요청을 보내면 redirect 로 list 로 보내고, list 에서 미리 생성한 목록을 출력한다.

        return "redirect:/events/list";
    }

    @GetMapping("/events/list")
    public String getEvents(Model model) {
        // 원래는 database 에서 select 해와야 한다.
        Event event = new Event(1L, "spring", 10);

        List<Event> events = new ArrayList<>();
        events.add(event);

        model.addAttribute("events", events);

        return "/list";
    }


}
