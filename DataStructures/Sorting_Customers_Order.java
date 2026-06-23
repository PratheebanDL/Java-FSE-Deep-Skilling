class Order
{
    private int orderId;
    private String customerName;
    private double orderAmount;

    public Order(int orderId,
                 String customerName,
                 double orderAmount)
    {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderAmount = orderAmount;
    }

    public double getOrderAmount()
    {
        return orderAmount;
    }

    @Override
    public String toString()
    {
        return "Order ID: " + orderId
                + " | Customer: " + customerName
                + " | Amount: $" + orderAmount;
    }
}

class OrderSorter
{
    public static void bubbleSort(Order[] orderList)
    {
        int size = orderList.length;

        for (int i = 0; i < size - 1; i++)
        {
            for (int j = 0; j < size - i - 1; j++)
            {
                if (orderList[j].getOrderAmount()
                    > orderList[j + 1].getOrderAmount())
                {
                    Order tempOrder = orderList[j];
                    orderList[j] = orderList[j + 1];
                    orderList[j + 1] = tempOrder;
                }
            }
        }
    }

    public static void quickSort(Order[] orderList,
                                 int lowIndex,
                                 int highIndex)
    {
        if (lowIndex < highIndex)
        {
            int pivotIndex =
                    partition(orderList,
                              lowIndex,
                              highIndex);

            quickSort(orderList,
                      lowIndex,
                      pivotIndex - 1);

            quickSort(orderList,
                      pivotIndex + 1,
                      highIndex);
        }
    }

    private static int partition(Order[] orderList,
                                 int lowIndex,
                                 int highIndex)
    {
        double pivotValue =
                orderList[highIndex].getOrderAmount();

        int smallerElementIndex = lowIndex - 1;

        for (int currentIndex = lowIndex;
             currentIndex < highIndex;
             currentIndex++)
        {
            if (orderList[currentIndex]
                    .getOrderAmount() <= pivotValue)
            {
                smallerElementIndex++;

                Order tempOrder =
                        orderList[smallerElementIndex];

                orderList[smallerElementIndex] =
                        orderList[currentIndex];

                orderList[currentIndex] =
                        tempOrder;
            }
        }

        Order tempOrder =
                orderList[smallerElementIndex + 1];

        orderList[smallerElementIndex + 1] =
                orderList[highIndex];

        orderList[highIndex] = tempOrder;

        return smallerElementIndex + 1;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Order[] bubbleSortOrders =
        {
            new Order(101, "Alice", 250.50),
            new Order(102, "Bob", 89.99),
            new Order(103, "Charlie", 450.00),
            new Order(104, "David", 120.75)
        };

        Order[] quickSortOrders =
        {
            new Order(101, "Alice", 250.50),
            new Order(102, "Bob", 89.99),
            new Order(103, "Charlie", 450.00),
            new Order(104, "David", 120.75)
        };

        System.out.println("--- BUBBLE SORT ---");

        OrderSorter.bubbleSort(bubbleSortOrders);

        for (Order order : bubbleSortOrders)
        {
            System.out.println(order);
        }

        System.out.println("\n--- QUICK SORT ---");

        OrderSorter.quickSort(
                quickSortOrders,
                0,
                quickSortOrders.length - 1);

        for (Order order : quickSortOrders)
        {
            System.out.println(order);
        }
    }
}