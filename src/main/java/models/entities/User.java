package models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName(nameOfTable = "users")
public class User {
    @ColumnName(name = "id",identity = true,primary = true)
    Long id;
    @ColumnName(name = "nick",uniq = true,notNull = true)
    String nick;
    @ColumnName(name = "first_name")
    String firstName;
    @ColumnName(name = "last_name")
    String lastName;
    @ColumnName(name = "mail",uniq = true, notNull = true)
    String mail;
    @ColumnName(name = "password",notNull = true)
    String password;
    @ColumnName(name = "admin",defaultBooleanFalse = true)
    boolean isAdmin;
    @ColumnName(name = "salt")
    String salt;
}
