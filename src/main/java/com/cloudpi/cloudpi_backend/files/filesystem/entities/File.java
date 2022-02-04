package com.cloudpi.cloudpi_backend.files.filesystem.entities;

import com.cloudpi.cloudpi_backend.files.filesystem.pojo.FileType;
import com.cloudpi.cloudpi_backend.files.physical.entities.DriveEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("F")
public class File extends PathObject {

    public File(@NonNull Directory parent,
                @NonNull VFilesystemRoot root,
                @NonNull DriveEntity drive,
                @NotBlank String path,
                @Nullable FileType fileType,
                @NonNull Long size
    ) {
        super(path, parent, root, size, new Date(), new Date());
        this.drive = drive;
        this.fileType = fileType == null ? FileType.UNDEFINED : fileType;
    }

//    @Column(nullable = true)
//    private DriveEntity thumbnailDrive;

    @Column
    private Boolean hasThumbnail = false;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private @NonNull FileType fileType = FileType.UNDEFINED;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "drive_files")
    private @NonNull DriveEntity drive;

}