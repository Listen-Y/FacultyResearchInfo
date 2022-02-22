package com.faculty_research_info_mis.server.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.util.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description: If you don't work hard, you will be a loser.
 * User: Listen-Y.
 * Date: 2022-02-22
 * Time: 21:06
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Log log = LogFactory.get();


    /**
     * @description 文件上传，入参可以根据具体业务进行添加
     */
    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public Result<?> upLoadFile(@RequestBody MultipartFile file) {
        log.info("测试MultipartFile实现文件上传");
        // 获取文件的完整名称，文件名+后缀名
        System.out.println(file.getOriginalFilename());
        // 文件传参的参数名称
        System.out.println(file.getName());
        // 文件大小，单位：字节
        System.out.println(file.getSize());
        // 获取文件类型，并非文件后缀名
        System.out.println(file.getContentType());
        try {
            // MultipartFile 转 File
            File resultFile = FileUtil.MultipartFileToFile(file);
            System.out.println(resultFile.getName());

            // File 转 MultipartFile
            MultipartFile resultMultipartFile = FileUtil.FileToMultipartFile(resultFile);
            System.out.println(resultMultipartFile.getSize());

        } catch (IOException e) {
            log.info("文件转换异常");
        }
        return Result.success();
    }
}
