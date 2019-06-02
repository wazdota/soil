package com.soil.download.controller;

import com.soil.download.bean.FilesName;
import com.soil.download.enums.ErrorCode;
import com.soil.download.response.ApiResult;
import com.soil.download.service.DlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class DlController {

    private DlService dlService;

    @Autowired
    public DlController(DlService dlService){
        this.dlService = dlService;
    }

    @RequestMapping(value = "/file", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult files(){
        return dlService.findAllFiles();
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @RequestParam("name") String fileName){
        String realPath = "/usr/mysqlbackup";
        File file = new File(realPath,fileName);
        FileInputStream is = null;
        BufferedInputStream bs = null;
        OutputStream os = null;
        try {
            if (file.exists()) {
                response.setHeader("Content-Type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                is = new FileInputStream(file);
                bs =new BufferedInputStream(is);
                os = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while((len = bs.read(buffer)) != -1){
                    os.write(buffer,0,len);
                }
            }else {
                response.sendError(404,"File Not Found");
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            try{
                if(is != null){
                    is.close();
                }
                if( bs != null ){
                    bs.close();
                }
                if( os != null){
                    os.flush();
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/db", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public ApiResult backup(){
        return dlService.backup();
    }
}
