package info.openrpg.telegram.commands;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CommandChooserTest {
    private CommandChooser commandChooser;

    @BeforeMethod
    public void setUp() throws Exception {
        commandChooser = new CommandChooser();
    }

    @Test
    public void testVoidCommand() throws Exception {
        assertEquals(commandChooser.chooseCommand("/"), TelegramCommand.NOTHING);
    }

    @Test
    public void testVoidCommandWithoutSLash() throws Exception {
        assertEquals(commandChooser.chooseCommand(""), TelegramCommand.NOTHING);
    }

    @Test
    public void testHelp() throws Exception {
        assertEquals(commandChooser.chooseCommand("/help"), TelegramCommand.HELP);
    }

    @Test
    public void testPlayerInfoWithoutArg() throws Exception {
        assertEquals(commandChooser.chooseCommand("/player_info"), TelegramCommand.PLAYER_INFO);
    }

    @Test
    public void testStart() throws Exception {
        assertEquals(commandChooser.chooseCommand("/start"), TelegramCommand.START);
    }
}