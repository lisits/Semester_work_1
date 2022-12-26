package models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(nameOfTable = "files")
public class File {
    @ColumnName(name = "id")
    private Long id;
    @ColumnName(name = "original_name")
    private String originalName;
    @ColumnName(name = "size")
    private Long size;
    @ColumnName(name = "uuid")
    private String uuid;
    @ColumnName(name = "mime_type")
    private String type;
//    @ColumnName(name = "is_main")
//    private Boolean isMain;
    @ColumnName(name = "user_id")
    private Long userId;

    public File(String originalName, Long size, String type) {
        this.originalName = originalName;
        this.size = size;
        this.type = type;
    }
}
