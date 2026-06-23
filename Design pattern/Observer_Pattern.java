import java.util.ArrayList;
import java.util.List;

interface Observer
{
    void update(String stockName, double stockPrice);
}

interface Stock
{
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}

class StockMarket implements Stock
{
    private final List<Observer> observerList = new ArrayList<>();

    private String stockName;
    private double stockPrice;

    @Override
    public void registerObserver(Observer observer)
    {
        observerList.add(observer);
        System.out.println("Observer registered successfully");
    }

    @Override
    public void removeObserver(Observer observer)
    {
        observerList.remove(observer);
        System.out.println("Observer removed successfully");
    }

    @Override
    public void notifyObservers()
    {
        for (Observer observer : observerList)
        {
            observer.update(stockName, stockPrice);
        }
    }

    public void updateStockPrice(String stockName, double stockPrice)
    {
        this.stockName = stockName;
        this.stockPrice = stockPrice;

        notifyObservers();
    }
}

class MobileApplication implements Observer
{
    private final String userName;

    public MobileApplication(String userName)
    {
        this.userName = userName;
    }

    @Override
    public void update(String stockName, double stockPrice)
    {
        System.out.println(
            userName + " : " + stockName + " -> Rs." + stockPrice
        );
    }
}

class WebApplication implements Observer
{
    private final String websiteUrl;

    public WebApplication(String websiteUrl)
    {
        this.websiteUrl = websiteUrl;
    }

    @Override
    public void update(String stockName, double stockPrice)
    {
        System.out.println(
            websiteUrl + " : " + stockName + " -> Rs." + stockPrice
        );
    }
}

public class Main
{
    public static void main(String[] args)
    {
        StockMarket stockMarket = new StockMarket();

        Observer mobileUser =
                new MobileApplication("PremiumUser");

        Observer webUser =
                new WebApplication("portal.nasdaq.com");

        stockMarket.registerObserver(mobileUser);
        stockMarket.registerObserver(webUser);

        stockMarket.updateStockPrice("AAPL", 185.50);
        stockMarket.updateStockPrice("GOOGL", 172.30);

        stockMarket.removeObserver(webUser);

        stockMarket.updateStockPrice("TSLA", 220.15);
    }
}