class InvestmentForecast
{
    public double calculateInvestmentValue(
            double initialAmount,
            double annualRate,
            int investmentYears)
    {
        if (investmentYears <= 0)
        {
            return initialAmount;
        }

        return calculateInvestmentValue(
                initialAmount * (1 + annualRate),
                annualRate,
                investmentYears - 1);
    }
}

public class Main
{
    public static void main(String[] args)
    {
        InvestmentForecast investmentForecast =
                new InvestmentForecast();

        double initialAmount = 10000.00;
        double annualGrowthRate = 0.05;
        int investmentYears = 5;

        double futureInvestmentValue =
                investmentForecast.calculateInvestmentValue(
                        initialAmount,
                        annualGrowthRate,
                        investmentYears);

        System.out.println(
                "Initial Investment : $"
                + initialAmount);

        System.out.println(
                "Annual Growth Rate : "
                + (annualGrowthRate * 100)
                + "%");

        System.out.println(
                "Investment Period  : "
                + investmentYears
                + " Years");

        System.out.printf(
                "Future Value       : $%.2f%n",
                futureInvestmentValue);
    }
}