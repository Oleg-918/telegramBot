package usatu.bot.telegrambot.sevice;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usatu.bot.telegrambot.model.Lab;
import usatu.bot.telegrambot.repository.LabRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LabService {

    private LabRepository labRepository;

    public void saveLab(Lab lab) {
        labRepository.save(lab);
    }

    public Lab getLab(Integer number) {
        return labRepository.findByNumber(number);
    }
}
