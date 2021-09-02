package com.cloudpi.cloudpi_backend.files_info.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@DiscriminatorValue("FILE")
public class FileEntity extends FilesystemObjectEntity {
    @Column(nullable = false)
    Long size;
    @Column(nullable = false)
    Date modifiedAt;
}