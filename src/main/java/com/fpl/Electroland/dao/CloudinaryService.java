package com.fpl.Electroland.dao;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public List<String> uploadMultipleFiles(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            // Kiểm tra nếu file không có nội dung thì bỏ qua
            if (file.isEmpty()) {
                continue;
            }

            try {
                // Upload file lên Cloudinary
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String url = uploadResult.get("url").toString();
                urls.add(url);
            } catch (IOException e) {
                // Log lỗi và tiếp tục với file khác nếu có lỗi
                System.err.println("Error uploading file: " + file.getOriginalFilename());
                e.printStackTrace();
            }
        }

        return urls;
    }
}
