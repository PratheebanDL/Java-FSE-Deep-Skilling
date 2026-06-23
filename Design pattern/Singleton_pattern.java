class ApplicationLogger
{
    private static ApplicationLogger loggerInstance;

    private ApplicationLogger()
    {
        System.out.println("User Logged In");
    }

    public static ApplicationLogger getLoggerInstance()
    {
        if (loggerInstance == null)
        {
            loggerInstance = new ApplicationLogger();
        }

        return loggerInstance;
    }
}

public class main
{
    public static void main(String[] args)
    {
        ApplicationLogger firstLogger =
                ApplicationLogger.getLoggerInstance();

        ApplicationLogger secondLogger =
                ApplicationLogger.getLoggerInstance();
    }
}