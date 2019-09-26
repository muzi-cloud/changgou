package com.changgou.file.feign;

import com.changgou.file.config.FeignMutipartFormEncoder;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zl
 * @version V1.0
 * @Description: TODO
 * @date 2019/9/24 15:16
 **/
@FeignClient(name = "file",configuration = FeignMutipartFormEncoder.class)
 public interface FileFeign {
        /**
         * 上传文件
         * @param file
         * @return
         * @throws IOException
         */
        @RequestMapping(value = "upload",method = RequestMethod.POST,produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public Result upload(@RequestPart("file") MultipartFile file) throws IOException;
}

