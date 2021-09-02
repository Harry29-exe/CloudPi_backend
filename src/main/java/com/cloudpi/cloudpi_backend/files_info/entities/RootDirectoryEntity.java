package com.cloudpi.cloudpi_backend.files_info.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DiscriminatorValue("DIRECTORY")
public class RootDirectoryEntity extends FilesystemIdEntity {
    @Column(nullable = false)
    String systemPath;
}
