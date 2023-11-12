package planet;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Planet {
    @Id
    private String id;
    @Column
    private String name;
}
