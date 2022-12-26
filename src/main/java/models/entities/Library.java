package models.entities;

@TableName(nameOfTable = "library")
public class Library {
    @ColumnName(name = "id_of_user")
    Long idOfUser;
    @ColumnName(name = "id_of_cat")
    Long idOfCat;
}
