package models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
@TableName(nameOfTable = "cat")
public class Cat {
    @ColumnName(name = "id", primary = true, identity = true)
    Long id;
    @ColumnName(name = "name")
    String name;
    @ColumnName(name = "owner")
    String owner;
    @ColumnName(name = "color")
    String color;
    @ColumnName(name = "age")
    Integer age;
    @ColumnName(name = "cost")
    Long cost;
}