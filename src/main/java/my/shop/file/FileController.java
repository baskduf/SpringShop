package my.shop.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.auth.Auth;
import my.shop.haveitem.HaveItemService;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import my.shop.member.Member;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Slf4j
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileManager fileManager;
    private final ItemRepository itemRepository;
    private final HaveItemService haveItemService;

    @ResponseBody
    @RequestMapping("/images/{imageName}")
    public UrlResource responseUrl(@PathVariable String imageName) throws MalformedURLException {
        return new UrlResource("file:"+fileManager.getFullPath(imageName, FileEnum.IMAGE));
    }

    @ResponseBody
    @RequestMapping("/validation")
    public String audioValidation(@RequestBody String itemId, @Auth Member member) throws Exception {
        Long id = Long.parseLong(itemId);
        if (member != null) {
            Item item = itemRepository.findById(id).orElseThrow(Exception::new);
            if (item != null) {
                if (haveItemService.haveItem(member, item)) {
                    log.info("member = {} , playing = {}", member.getName(), item.getAudioResource().getViewFileName());
                    return item.getAudioResource().getServerFileName();
                }
            }
        }
        throw new Exception();
    }

}
