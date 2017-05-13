package info.openrpg.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Credentials {
    private final String botName;
    private final String token;
    private final String imageServerHostname;
    private final int imageServerPort;
}
