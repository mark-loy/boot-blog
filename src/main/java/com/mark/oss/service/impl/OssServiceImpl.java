package com.mark.oss.service.impl;

import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.mark.base.enums.CustomExceptionEnum;
import com.mark.base.exception.CustomException;
import com.mark.oss.service.OssService;
import com.mark.oss.utils.OssConstantUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/26 16:23
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * 文件上传
     * @param multipartFile 复杂文件对象
     * @return String
     */
    @Override
    public String fileUpload(MultipartFile multipartFile) {

        // 从常量类中获取配置信息
        String endpoint = OssConstantUtil.END_POINT;
        String key = OssConstantUtil.KEY;
        String secret = OssConstantUtil.SECRET;
        String bucketName = OssConstantUtil.BUCKET_NAME;
        String filePatten = OssConstantUtil.FILE_PATTEN;

        // 构建oss对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, key, secret);

        // 构建返回的文件地址
        String url;

        try {
            // 生成日期文件夹 如：2020/12/23
            String filePath = DateUtil.format(new Date(), "yyyy/MM/dd");

            // 设置随机文件名 如： uuid.jpg
            // 获取源文件名称
            String originalName = multipartFile.getOriginalFilename();
            // 获取源文件后缀名
            assert originalName != null;
            String fileType = originalName.substring(originalName.lastIndexOf("."));
            // 生成uuid
            String fileName = UUID.randomUUID().toString().replace("-", "");
            // 拼接
            filePath = filePatten + "/" + filePath + "/" + fileName + fileType;

            // 构建文件上传对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, multipartFile.getInputStream());

            // 文件上传
            ossClient.putObject(putObjectRequest);
            // 关闭资源连接
            ossClient.shutdown();

            url = "https://" + bucketName + "." + endpoint + "/" + filePath;

        } catch (IOException e) {
            throw new CustomException(CustomExceptionEnum.FILE_UPLOAD_ERROR);
        }
        return url;
    }

    /**
     * 删除图片
     * @param filename 文件路径 + 名称
     */
    @Override
    public void deleteFile(String filename) {
        // 从常量类中获取配置信息
        String endpoint = OssConstantUtil.END_POINT;
        String key = OssConstantUtil.KEY;
        String secret = OssConstantUtil.SECRET;
        String bucketName = OssConstantUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, key, secret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, filename);

        ossClient.shutdown();
    }

    /**
     * 删除图片
     * @param url 全路径
     */
    @Override
    public void deleteFileUrl(String url) {
        // 对url进行截取，去除域名部分
        String fileUrl = url.substring(url.indexOf("://") + 3);
        // 截取fileUrl前的/
        String filename = fileUrl.substring(fileUrl.indexOf("/") + 1);
        // 删除文件
        deleteFile(filename);
    }
}
