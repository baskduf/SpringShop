package my.shop.item;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemUploadForm {

    @NotEmpty
    private String itemName;

    @NotEmpty
    private String artist;

    @NotNull
    private Integer price;

    @NotNull
    private Integer discountPrice;

    @NotEmpty
    private String description;

    private MultipartFile audioFile;

    private MultipartFile imageFile;

}
