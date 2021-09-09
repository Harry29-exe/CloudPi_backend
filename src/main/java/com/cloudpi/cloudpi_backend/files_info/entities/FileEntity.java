package com.cloudpi.cloudpi_backend.files_info.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

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