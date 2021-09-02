package com.cloudpi.cloudpi_backend.files_info.entities.java;

import lombok.*;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Builder
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