package my.shop.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.file.FileEnum;
import my.shop.file.FileManager;
import my.shop.file.FileResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final FileManager fileManager;
    private final ItemRepository itemRepository;

    @GetMapping("/add")
    public String itemAddView(@ModelAttribute("item") ItemUploadForm itemUploadForm) {
        return "web/item_upload";
    }

    @PostMapping("/add")
    public String itemAddProcess(@Validated @ModelAttribute("item") ItemUploadForm itemUploadForm, BindingResult bindingResult,
                                 @RequestParam("imageFile")MultipartFile imageFile,
                                 @RequestParam("audioFile")MultipartFile audioFile) throws IOException {
        if (imageFile.isEmpty()) {
            bindingResult.rejectValue("imageFile", "RequiredUploadImageFile");
        }
        if (audioFile.isEmpty()) {
            bindingResult.rejectValue("audioFile", "RequiredUploadAudioFile");
        }
        if (bindingResult.hasErrors()) {
            return "web/item_upload";
        }

        // 검증 완료
        FileResource imageResource = fileManager.storeFile(imageFile, FileEnum.IMAGE);
        FileResource audioResource = fileManager.storeFile(audioFile, FileEnum.AUDIO);
        Item item = Item.createItem(itemUploadForm, imageResource, audioResource);
        itemRepository.save(item);

        log.info("saved Item = {}", item);

        return "redirect:/";
    }
}
