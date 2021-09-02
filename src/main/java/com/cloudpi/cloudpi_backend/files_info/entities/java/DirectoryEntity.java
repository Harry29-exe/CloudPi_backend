package com.cloudpi.cloudpi_backend.files_info.entities.java;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("DIRECTORY")
public class DirectoryEntity extends FilesystemObjectEntity {
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    List<FilesystemObjectEntity> children;
}
