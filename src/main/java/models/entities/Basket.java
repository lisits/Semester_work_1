package models.entities;

@TableName(nameOfTable = "Basket")
public class Basket {
    @ColumnName(name = "id_of_order")
    Long idOfOrder;
    @ColumnName(name = "id_of_cat")
    Long idOfCat;
}
