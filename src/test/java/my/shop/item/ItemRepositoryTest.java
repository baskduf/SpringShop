package my.shop.item;

import jakarta.persistence.Table;
import my.shop.file.FileEnum;
import my.shop.file.FileResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    void addItems() {
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setItemName("test" + i);
            item.setDescription("test description");
            item.setPrice(100);
            item.setDiscountPrice(100);
            item.setArtist("test artist");
            itemRepository.save(item);
        }
    }

    @Test
    void pageTest() {
        addItems();
        PageRequest pageRequest = PageRequest.of(1, 5);
        Page<Item> allByOrderByIdDesc = itemRepository.findAllByOrderByIdDesc(pageRequest);
        System.out.println(allByOrderByIdDesc);

    }

    @Test
    void itemAddTest() {
        Item item = new Item();
        item.setItemName("test");
        item.setDescription("test description");
        item.setPrice(100);
        item.setDiscountPrice(100);
        item.setArtist("test artist");

        FileResource fileResource = new FileResource();
        fileResource.setFileEnum(FileEnum.IMAGE);
        fileResource.setServerFileName("image serverFileName");
        fileResource.setViewFileName("image viewFileName");
        item.setImageResource(fileResource);

        FileResource audioResource = new FileResource();
        audioResource.setFileEnum(FileEnum.AUDIO);
        audioResource.setServerFileName("audio serverFileName");
        audioResource.setViewFileName("audio viewFileName");
        item.setAudioResource(audioResource);

        itemRepository.save(item);
//
//        System.out.println(itemRepository.findByItemName(item.getItemName()).getAudioResource());

    }
}