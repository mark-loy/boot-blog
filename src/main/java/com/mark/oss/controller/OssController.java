package com.mark.oss.controller;

import com.mark.common.entity.Result;
import com.mark.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/26 16:22
 */
@Api(value = "对象存储管理", tags = {"对象存储服务接口"})
@RestController
@RequestMapping("/api/oss/file")
public class OssController {

    @Resource
    private OssService ossService;

    /**
     * 文件上传
     * @param multipartFile 文件
     * @return Result
     */
    @ApiOperation(value = "文件上传")
    @PostMapping
    public Result fileUpload(@ApiParam(value = "文件") @RequestParam("file") MultipartFile multipartFile) {

        String url = ossService.fileUpload(multipartFile);

        return Result.ok().data("url", url);
    }

    /**
     * 删除上传文件
     * @param filename 路径 + 文件名  格式：images/muke/blog/dev/2021/01/26/0a68556dd6a7432da32ec4c55f02913c.jpg
     * @return Result
     */
    @ApiOperation("删除上传文件")
    @DeleteMapping
    public Result deleteFile(@ApiParam(value = "路径 + 文件名", example = "images/muke/blog/dev/2021/01/26/0a68556dd6a7432da32ec4c55f02913c.jpg") @RequestParam("filename") String filename) {
        ossService.deleteFile(filename);
        return Result.ok();
    }

    /**
     * 删除上传文件
     * @param url 全路径 格式：https://xxxx.aliyuncs.com/images/muke/blog/dev/2021/01/26/0a68556dd6a7432da32ec4c55f02913c.jpg
     * @return Result
     */
    @ApiOperation("删除上传文件")
    @DeleteMapping("/url")
    public Result deleteFileUrl(@ApiParam(value = "全路径", example = "https://xxxx.aliyuncs.com/images/muke/blog/dev/2021/01/26/0a68556dd6a7432da32ec4c55f02913c.jpg") @RequestParam("url") String url) {
        ossService.deleteFileUrl(url);
        return Result.ok();
    }
}
