package my.shop.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.file.FileEnum;
import my.shop.file.FileManager;
import my.shop.file.FileResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/edit/{editItemId}")
    public String itemEditView(@PathVariable Long editItemId, Model model) throws Exception {
        Item item = itemRepository.findById(editItemId).orElseThrow(Exception::new);
        if (item != null) {
            ItemUploadForm itemUploadForm = new ItemUploadForm();
            itemUploadForm.setItemName(item.getItemName());
            itemUploadForm.setPrice(item.getPrice());
            itemUploadForm.setArtist(item.getArtist());
            itemUploadForm.setDescription(item.getDescription());
            itemUploadForm.setDiscountPrice(item.getDiscountPrice());
            model.addAttribute("item", itemUploadForm);
        }
        return "web/item_upload";
    }

    @PostMapping("/edit/{editItemId}")
    public String itemEditProcess(@PathVariable Long editItemId, @Validated @ModelAttribute("item") ItemUploadForm itemUploadForm, BindingResult bindingResult,
                                  @RequestParam("imageFile")MultipartFile imageFile,
                                  @RequestParam("audioFile")MultipartFile audioFile) throws Exception {
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

        Item item = itemRepository.findById(editItemId).orElseThrow(Exception::new);
        if (item != null) {
            FileResource imageResource = fileManager.storeFile(imageFile, FileEnum.IMAGE);
            FileResource audioResource = fileManager.storeFile(audioFile, FileEnum.AUDIO);

            item.setItemName(itemUploadForm.getItemName());
            item.setDescription(itemUploadForm.getDescription());
            item.setPrice(itemUploadForm.getPrice());
            item.setArtist(itemUploadForm.getArtist());
            item.setDiscountPrice(item.getDiscountPrice());
            item.setAudioResource(audioResource);
            item.setImageResource(imageResource);

            itemRepository.save(item);
        }

        log.info("edited Item = {}", item);

        return "redirect:/";
    }
}
