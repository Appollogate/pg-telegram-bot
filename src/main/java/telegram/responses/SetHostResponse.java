package telegram.responses;

import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.Bot;
import telegram.settings.BotStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetHostResponse extends Response {

    @Override
    public BotStatus execute(Bot bot, Message message) {
        var host = message.getText();
        if (!isHostValid(host)) { // validate host ip
            sendMessage(bot, message.getChat().getId(), "В качестве хоста ожидался валидный IP-адрес. Пожалуйста, попробуйте ещё раз.");
            return BotStatus.AWAITING_HOST;
        }
        var userId = message.getFrom().getId();
        bot.getUserSettings(userId).setHost(host);
        sendMessage(bot, message.getChat().getId(),
                "Хост успешно сохранён как " + bot.getUserSettings(userId).getHost() + ".");
        return BotStatus.DEFAULT;
    }

    boolean isHostValid(String host) {
        Pattern pattern = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");
        Matcher matcher = pattern.matcher(host);
        return matcher.find();
    }
}
