package com.soil.download.service;

import com.soil.download.bean.FilesName;
import com.soil.download.enums.ErrorCode;
import com.soil.download.response.ApiResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DlService {

    public ApiResult findAllFiles(){
        File file = new File("/usr/mysqlbackup");
        String[] files = file.list();
        if(files == null){
            return new ApiResult(ErrorCode.OK);
        }
        List<FilesName> list = new ArrayList<>();
        for (String f : files){
            if(f.endsWith(".sql")){
                FilesName filesName = new FilesName();
                filesName.setName(f);
                list.add(filesName);
            }
        }
        return new ApiResult<>(ErrorCode.OK, list);
    }

    public ApiResult backup(){
        String filePath="/usr/mysqlbackup";
        String dbName="db_soil";//备份的数据库名
        String username="root";//用户名
        String password="123456";//密码
        File uploadDir = new File(filePath);
        if (!uploadDir.exists())
            uploadDir.mkdirs();

        String date = (new java.text.SimpleDateFormat("yyyy-M-d_H")).format(new Date());
        String cmd =  "/usr/local/mysql/bin/mysqldump -u"+ username +"  -p "+password + dbName + " -r "
                + filePath + "/" + dbName+date+ ".sql";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            return new ApiResult<>(ErrorCode.CREATED,dbName+date);
        } catch (Exception e) {
            return new ApiResult(ErrorCode.INVALID_REQUEST);
        }
    }
}
