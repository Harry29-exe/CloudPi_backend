package com.cloudpi.cloudpi_backend.files_info.entities.java;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("DIRECTORY")
public class RootDirectoryEntity extends FilesystemIdEntity {
    @Column(nullable = false)
    String systemPath;
}
