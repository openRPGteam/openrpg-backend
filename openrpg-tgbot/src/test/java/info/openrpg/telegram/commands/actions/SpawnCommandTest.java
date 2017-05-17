package info.openrpg.telegram.commands.actions;

import com.google.common.base.Joiner;
import info.openrpg.database.models.Player;
import info.openrpg.database.repositories.PlayerRepository;
import info.openrpg.image.processing.RequestSender;
import info.openrpg.telegram.commands.Message;
import info.openrpg.telegram.constants.Command;
import info.openrpg.telegram.io.InlineButton;
import info.openrpg.telegram.io.InputMessage;
import info.openrpg.telegram.io.MessageWrapper;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.User;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class SpawnCommandTest {
    private static final Long CHAT_ID = 13L;
    private static final String USER_NAME = "DarkCasual";
    private static final String PHOTO_NAME = "spawn";
    private static final InputStream STREAM = mock(InputStream.class);

    private SpawnCommand command;

    @BeforeMethod
    public void setUp() throws Exception {
        PlayerRepository playerRepository = mock(PlayerRepository.class);
        RequestSender requestSender = mock(RequestSender.class);
        when(requestSender.spawnPlayer(CHAT_ID)).thenReturn(Optional.of(STREAM));

        Player player = Player.builder()
                .firstName("Dark")
                .lastName("Casual")
                .userName(USER_NAME)
                .id(CHAT_ID.intValue())
                .build();
        when(playerRepository.findPlayerByUsername(USER_NAME)).thenReturn(Optional.of(player));
        command = new SpawnCommand(playerRepository, requestSender);
    }

    @Test
    public void testExecute() throws Exception {
        User user = mock(User.class);
        when(user.getUserName()).thenReturn(USER_NAME);
        InputMessage inputMessage = new InputMessage(Command.START, CHAT_ID, user);

        List<MessageWrapper> wrappers = command.execute(inputMessage);

        Assert.assertFalse(wrappers.isEmpty());
        SendPhoto photo = wrappers.get(0).getPhoto();
        Assert.assertNotNull(photo);
        Assert.assertEquals(photo.getPhotoName(), PHOTO_NAME);
        Assert.assertEquals(photo.getChatId(), String.valueOf(CHAT_ID));
        Assert.assertEquals(photo.getReplyMarkup(), InlineButton.moveButtonList());
    }

    @Test
    public void testHandleCrash() throws Exception {
        InputMessage message = new InputMessage("", 13L, new User());
        Assert.assertEquals(command.handleCrash(new RuntimeException(), message), Collections.emptyList());
    }

}