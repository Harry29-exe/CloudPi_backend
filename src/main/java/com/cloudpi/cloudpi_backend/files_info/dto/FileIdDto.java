package com.cloudpi.cloudpi_backend.files_info.dto;

import lombok.Data;
import java.util.*;

@Data
public class FileIdDto {
    private Long fileId;
    private String fileName;
    private Date lastModified;
}