package my.shop.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class FileManager {

    @Value("${audio.file.path}")
    private String audioPath;

    @Value("${image.file.path}")
    private String imagePath;

    private String getFullPath(String filename, FileEnum fileEnum) {
        if (fileEnum == FileEnum.AUDIO) {
            return audioPath + filename;
        } else if (fileEnum == FileEnum.IMAGE) {
            return imagePath + filename;
        }

        return null;
    }

    public FileResource storeFile(MultipartFile multipartFile, FileEnum fileEnum) throws IOException {
        if (!multipartFile.isEmpty()) {
            String originalFilename = multipartFile.getOriginalFilename();
            String uuidName = createStoreFileName(originalFilename);
            String fullPath = getFullPath(uuidName, fileEnum);

            log.info("save? path = {}", fullPath);
            multipartFile.transferTo(new File(fullPath));

            FileResource fileResource = new FileResource();
            fileResource.setServerFileName(uuidName);
            fileResource.setViewFileName(originalFilename);
            return fileResource;
        }
        return null;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

}
