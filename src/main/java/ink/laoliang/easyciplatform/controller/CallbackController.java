package ink.laoliang.easyciplatform.controller;

import ink.laoliang.easyciplatform.service.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/callback")
public class CallbackController {

    @Autowired
    private CallbackService callbackService;

    @GetMapping(value = "/github")
    public String callback(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("code");
        String userToken = httpServletRequest.getParameter("state");
        return callbackService.callback(code, userToken);
    }
}
