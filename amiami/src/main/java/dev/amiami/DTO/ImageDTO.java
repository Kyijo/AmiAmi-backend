package dev.amiami.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageDTO {
    private String file;
    private String name;
    private String username;
    private List<String> tags;
}
