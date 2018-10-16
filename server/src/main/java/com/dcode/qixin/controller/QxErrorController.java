package com.dcode.qixin.controller;

import com.dcode.qixin.core.Result;
import com.dcode.qixin.core.ResultGenerator;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
//@RequestMapping("${server.error.path:${error.path:/error}}")
public class QxErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error001";
    }

    @RequestMapping
    @ResponseBody
    public Result doHandleError(Exception e) {
        return ResultGenerator.genFailResult("操作失败001: " + e.toString());
    }
}
