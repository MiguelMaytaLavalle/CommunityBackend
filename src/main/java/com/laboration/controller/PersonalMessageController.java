package com.laboration.controller;

import com.laboration.DTO.PMWrapper;
import com.laboration.service.PMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RestController
public class PersonalMessageController {
    @Autowired
    private PMService service;
    @PostMapping(path="/send")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody
    PMWrapper createPM(@RequestBody PMWrapper pm){
        System.out.println(pm.getContent() + " " + pm.getReceiver() + " " + pm.getSender());
        return service.createPM(pm);
    }
    @GetMapping(path="/inbox/private/{sendername}/{receivername}")
    @CrossOrigin(origins = "http://localhost:3000")
    public @ResponseBody
    List<PMWrapper> getPMsFromSender(@PathVariable String sendername, @PathVariable String receivername){
        return service.getPMsFromSender(sendername, receivername);
    }
   /* @GetMapping(path="/inbox/{pmId}")
    public @ResponseBody
    PMWrapper getSpecificPM(@PathVariable int pmId){
        return service.getSpecificPM(pmId);
    }*/


}
