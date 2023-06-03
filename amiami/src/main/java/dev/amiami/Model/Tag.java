package dev.amiami.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String backgroundImage;

    @ManyToMany(mappedBy = "tags")
    private List<AmiAmiImage> images = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
    private List<AmiAmiVideo> videos = new ArrayList<>();
}
