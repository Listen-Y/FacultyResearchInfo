package com.faculty_research_info_mis.server.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.faculty_research_info_mis.server.component.Result;
import com.faculty_research_info_mis.server.util.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

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
    @PostMapping("/photo/{fileName}")
    public Result<?> upLoadFile(@RequestParam("file") MultipartFile file,
                                @PathVariable String fileName) {
        log.info("MultipartFile实现文件上传_" + fileName);
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
            File resultFile = FileUtil.MultipartFileToFile(file, fileName);
            if (resultFile != null) {
                log.info(resultFile.getAbsolutePath());
            }
        } catch (IOException e) {
            log.info("文件转换异常");
        }
        return Result.success();
    }

    /**
     * @description 文件下载，入参可以根据具体业务进行添加，比如下载具体编码的文件
     */
    public void downLoadFile() throws IOException {
        log.info("测试文件下载至浏览器默认地址");
        File file = new File("测试文件.xlsx");
        FileSystemResource fileSource = new FileSystemResource(file);
        FileUtil.downLoadFile(fileSource.getInputStream(), URLEncoder.encode(Objects.requireNonNull(fileSource.getFilename()), "UTF-8"));
    }
}
