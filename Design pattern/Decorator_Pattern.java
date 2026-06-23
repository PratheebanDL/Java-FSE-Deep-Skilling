interface NotificationService
{
    String sendNotification();
}

class EmailNotification implements NotificationService
{
    @Override
    public String sendNotification()
    {
        return "Notification sent through Email";
    }
}

abstract class NotificationDecorator implements NotificationService
{
    protected NotificationService notificationService;

    public NotificationDecorator(NotificationService notificationService)
    {
        this.notificationService = notificationService;
    }

    @Override
    public String sendNotification()
    {
        return notificationService.sendNotification();
    }
}

class SMSNotificationDecorator extends NotificationDecorator
{
    public SMSNotificationDecorator(NotificationService notificationService)
    {
        super(notificationService);
    }

    @Override
    public String sendNotification()
    {
        return super.sendNotification() + ", SMS";
    }
}

class SlackNotificationDecorator extends NotificationDecorator
{
    public SlackNotificationDecorator(NotificationService notificationService)
    {
        super(notificationService);
    }

    @Override
    public String sendNotification()
    {
        return super.sendNotification() + " and Slack";
    }
}

public class Main
{
    public static void main(String[] args)
    {
        NotificationService notificationService =
                new EmailNotification();

        System.out.println(notificationService.sendNotification());

        notificationService =
                new SMSNotificationDecorator(notificationService);

        System.out.println(notificationService.sendNotification());

        notificationService =
                new SlackNotificationDecorator(notificationService);

        System.out.println(notificationService.sendNotification());
    }
}