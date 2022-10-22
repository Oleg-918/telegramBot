package usatu.bot.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usatu.bot.telegrambot.model.Lab;

@Repository
public interface LabRepository extends JpaRepository<Lab, Integer> {
    Lab findByNumber(Integer number);
}
