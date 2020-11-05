package com.joey.musicplayer.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://localhost")
public class EndPoint {
    @MessageMapping("/history/{username}")
    @SendTo("/topic/histories/{username}")
    public String chat(String data) {
        return data;
    }
}