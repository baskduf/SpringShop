package my.shop.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileManager fileManager;

    @ResponseBody
    @RequestMapping("/images/{imageName}")
    public UrlResource responseUrl(@PathVariable String imageName) throws MalformedURLException {
        return new UrlResource("file:"+fileManager.getFullPath(imageName, FileEnum.IMAGE));
    }

    @ResponseBody
    @RequestMapping( "/validation")
    public String audioValidation(@RequestBody String audioUuidName) throws Exception {
        return audioUuidName;
    }

}
