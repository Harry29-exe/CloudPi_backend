package com.cloudpi.cloudpi_backend.files.filesystem.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class MoveFileRequest {

    private @NotNull UUID fileId;
    private @NotBlank String newPath;
//    private

}
