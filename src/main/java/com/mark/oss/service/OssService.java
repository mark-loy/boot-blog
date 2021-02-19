package com.mark.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/26 16:23
 */
public interface OssService {

    /**
     * 图片上传
     * @param multipartFile 复杂文件对象
     * @return String
     */
    String fileUpload(MultipartFile multipartFile);

    /**
     * 删除图片
     * @param filename 文件路径 + 名称
     */
    void deleteFile(String filename);

    /**
     * 删除图片
     * @param url 全路径
     */
    void deleteFileUrl(String url);
}
