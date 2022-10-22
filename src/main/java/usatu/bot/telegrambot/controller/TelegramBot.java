package usatu.bot.telegrambot.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import usatu.bot.telegrambot.config.BotConfig;
import usatu.bot.telegrambot.sevice.LabService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {


    private BotConfig config;

    private LabService labService;

    private final String INFO = "Привет! Этот бот предназначен для обмена лабораторными работами по физике. " +
            "Чтобы добавить л.р. нажми на \"Прикрепить,\" введи номер лабораторной и прикрепи соответсвующий файл." +
            "Чтобы найти необходимую лабораторную нажми на \"Поиск,\" введи номер лабораторной и скачай соответсвующий файл.";

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "Поиск", "/get":
                    sendMessage(chatId, "Введите номер Лабораторной");
//                    Вот тут возникает трабл, тк непонятно как считать значение из сообщения, была пропиисана неверная логика, дуюлировать не стал
                    break;

                case "Прикрепить", "/add":
                    sendMessage(chatId, "Введите номер Лабораторной");
//                    Вот тут возникает трабл, тк непонятно как считать значение из сообщения, была пропиисана неверная логика, дуюлировать не стал
                    sendMessage(chatId, "Прикрепите PDF-файл");

                    break;

                case "/info", "Информация":
                    sendMessage(chatId, INFO);
                    break;

                default:
                    sendMessage(chatId, "Sorry, command was not recognized");
            }
        }
    }


    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi, " + name + ", nice to meet you!";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add("Прикрепить");
        row.add("Поиск");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("Информация");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        message.setReplyMarkup(keyboardMarkup);
        executeMessage(message);
    }

    private void executeMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
