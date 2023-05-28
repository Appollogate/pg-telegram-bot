package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.bots.AbsSender;
import telegram.settings.BotStatus;

public class StartCommand extends Command {

    public StartCommand() {
        super("start", "получить инструкцию для начала работы");
    }
    @Override
    public BotStatus execute(AbsSender absSender, Chat chat) {
        sendMessage(absSender, chat.getId(), """
                Приветствую! Я - бот, способный подключаться к удаленной СУБД PostgreSQL и получать от неё данные.

                Для того, чтобы начать со мной работать, предоставьте мне следующие параметры, чтобы я смог установить подключение:
                
                - имя (IP-адрес) хоста через команду /sethost
                - номер порта через команду /setport
                - название базы данных через команду /setdbname
                - имя пользователя через команду /setusername
                - пароль через команду /setpassword
                
                После этого воспользуйтесь командой /sql, чтобы задать sql-запрос и получить результат его выполнения.""");
        return BotStatus.DEFAULT;
    }
}
