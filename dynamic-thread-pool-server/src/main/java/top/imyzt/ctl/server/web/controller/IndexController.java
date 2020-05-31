package top.imyzt.ctl.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author imyzt
 * @date 2020/05/31
 * @description PING 端口
 */
@Slf4j
@RestController
public class IndexController {

    @GetMapping({"ping", "index", "", "health", "ready"})
    public String ping(HttpServletRequest request) {
        return request.getRequestURI();
    }
}