package models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName(nameOfTable = "order_of_cats")
public class Order {
    @ColumnName(name = "id", identity = true, primary = true)
    Long id;
    @ColumnName(name = "id_of_user")
    Long UserId;
    @ColumnName(name = "date")
    Date date;
    @ColumnName(name = "time")
    Time time;
    @ColumnName(name="total_cost")
    Long totalCost;
}