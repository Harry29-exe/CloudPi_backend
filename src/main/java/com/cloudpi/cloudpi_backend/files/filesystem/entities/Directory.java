package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@DiscriminatorValue("D")
public class Directory extends PathObject {

    public Directory(
            Directory parent,
            @NonNull VFilesystemRoot root,
            @NonNull String path
    ) {
        super(path, parent, root, 0L, new Date(), new Date());
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Directory> childrenDirectories;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<File> childrenFiles;

}
