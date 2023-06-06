package dev.amiami.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class AmiAmiVideo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        @Column(unique = true)
        private String url;

        @JsonManagedReference
        @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinTable(name = "video_tag",
                joinColumns = @JoinColumn(name = "video_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
        private List<Tag> tags = new ArrayList<>();

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private AmiAmiUser user;
}