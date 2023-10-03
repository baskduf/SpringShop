package my.shop.item;

import jakarta.persistence.*;
import lombok.Data;
import my.shop.file.FileEnum;
import my.shop.file.FileManager;
import my.shop.file.FileResource;

import java.io.IOException;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private String artist;

    private Integer price;

    private Integer discountPrice;

    private String description;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private FileResource audioResource;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    private FileResource imageResource;


    public static Item createItem(ItemUploadForm itemUploadForm, FileResource imageResource, FileResource audioResource) throws IOException {
        Item item = new Item();
        item.setItemName(itemUploadForm.getItemName());
        item.setArtist(itemUploadForm.getArtist());
        item.setPrice(itemUploadForm.getPrice());
        item.setDescription(itemUploadForm.getDescription());
        item.setDiscountPrice(itemUploadForm.getDiscountPrice());
        item.setImageResource(imageResource);
        item.setAudioResource(audioResource);
        return item;
    }
}
