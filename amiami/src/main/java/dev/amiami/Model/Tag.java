package dev.amiami.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

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

    @JsonBackReference
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AmiAmiImage> images = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AmiAmiVideo> videos = new ArrayList<>();
}