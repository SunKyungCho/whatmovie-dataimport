package me.toyproject.whatmoviedataimport.application;


import me.toyproject.whatmoviedataimport.config.schedule.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    SchedulerService schedulerService;

    @GetMapping("")
    public String test() {
        schedulerService.addSchedule();
        return "success";
    }
}
