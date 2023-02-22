package Model;

import UserManagement.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class contains all the information related to a notification
 */
public class Notification {
    private final UUID id;
    private Tweet tweetTrigger;
    private User userToNotify;
    private User userWhoNotifies;
    private LocalDateTime date;
    private notificationType type;


    public Notification() {
        id = UUID.randomUUID();
        date =LocalDateTime.now();
        //Falta inicializar el resto
    }
}
