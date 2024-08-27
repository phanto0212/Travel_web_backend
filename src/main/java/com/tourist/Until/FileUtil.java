package com.tourist.Until;




import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public class FileUtil {
    public static String convertFileToBase64String(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.encodeBase64String(bytes);
    }
}

