package com.cloudpi.cloudpi_backend.files.permissions.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "file_permissions")
public class FilePermissionEntity extends DriveObjectPermissionEntity {
}
