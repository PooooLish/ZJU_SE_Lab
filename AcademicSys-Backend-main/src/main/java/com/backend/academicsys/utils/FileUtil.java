package com.backend.academicsys.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.OSSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FileUtil {

    // 如果需要使用的话保护好accessKey和secretKey，这是我私人账号的QAQ，勿泄露
    static String accessKey = "LTAI5tLuANfvRVR4K7fuDfcY";

    static String secretKey = "kZ8JUWci9PJQUrfkYtTBFRo2th9IGD";

    static String bucket = "academic-sys";

    static String endpoint = "oss-cn-hangzhou.aliyuncs.com";

    // 如需使用本地存储，修改此处，或创建对应的文件夹
    static String localPathPrefix = "D:\\photos\\";

    public static String uploadPhotoFile(String localPath, Long schoolId) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey);
        String suffix = localPath.substring(localPath.lastIndexOf("."));
        // 文件名为schoolId的MD5值，确保安全
        // 一个账号只能上传一个头像
        String fileName =  "userPhoto/" + Md5Util.getMD5String(schoolId.toString()) + suffix;
        String url = "https://" + bucket + "." + endpoint + "/" + fileName;
        try {

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new FileInputStream(localPath));

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }

    public static String savePhotoFile(MultipartFile file, Long schoolId) {
        String suffix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName = localPathPrefix + Md5Util.getMD5String(schoolId.toString()) + suffix;
        java.io.File dest = new java.io.File(fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static void deletePhotoFile(String localPath) {
        java.io.File file = new java.io.File(localPath);
        if (file.exists()) {
            file.delete();
        }
    }
}
