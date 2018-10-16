package com.dcode.qixin.controller;


import com.dcode.qixin.entity.GetVersionEntity;
import com.dcode.qixin.model.VersionApp;
import com.dcode.qixin.service.UserOddsService;
import com.dcode.qixin.service.VersionAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping(value = "/version")
public class VersionController {

    @Autowired
    protected VersionAppService versionAppService;

    @ResponseBody
    @RequestMapping(value = "/getVersion", produces = {"application/json;charset=UTF-8"})
    public Object getVersion(String version){

//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        request.getRequestURI();
//        http://113.111.186.38:8090/version/DownloadApp
//        HttpServletResponse response = requestAttributes.getResponse();

        VersionApp versionApp = versionAppService.getLastVersion();

        String currVersion = versionApp.getVersionName();// "1.0.7"; //  update
//        String apkUrl = "http://www.ememed.net/uploads/appfile/remoteAr.apk";
        String apkUrl = versionApp.getFileUrl(); // "http://119.28.232.88:8090/version/DownloadApp";
//        String apkUrl = "http://58.62.0.200:8010/download/qx.apk";
        String updateLog = versionApp.getUpdateLog(); // "1，更新\r\n2，优化\r\n3，修改功能。";
        boolean isConstraint = versionApp.getIsConstraint() == 1 ? true : false;  //是否强制更新

        GetVersionEntity entity = new GetVersionEntity();
        if(version.equals(currVersion)) {
            entity.setUpdate("No");
        }else {
            entity.setUpdate("Yes");
        }
        entity.setNew_version(currVersion);
        entity.setApk_file_url(apkUrl);
        entity.setUpdate_log(updateLog);
        entity.setConstraint(isConstraint);

        return  entity;
    }

    @RequestMapping(value = "/DownloadApp", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> Download(HttpServletResponse res) throws IOException {

            String filePath = "qx.apk";
            FileSystemResource file = new FileSystemResource(filePath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(file.getInputStream()));
    }

    @RequestMapping(value = "/DownloadApp2", method = RequestMethod.GET)
    public void Download2(HttpServletResponse res) {
        String fileName = "qx.apk";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
//            bis = new BufferedInputStream(new FileInputStream(new File("d://" + fileName)));
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }
}
