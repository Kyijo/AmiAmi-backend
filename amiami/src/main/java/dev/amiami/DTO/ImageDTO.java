package dev.amiami.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImageDTO {
    private MultipartFile image;
    private String nameOfImage;
    private String name;
    private List<String> tags;
}
