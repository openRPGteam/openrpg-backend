package info.openrpg.database.models.world;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Data
public class Location implements Serializable{
    private final int x;
    private final int y;
}
