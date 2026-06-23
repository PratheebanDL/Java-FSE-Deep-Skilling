class InventoryProduct
{
    private int productId;
    private String productName;
    private String productCategory;

    public InventoryProduct(int productId,
                            String productName,
                            String productCategory)
    {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
    }

    public int getProductId()
    {
        return productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public String getProductCategory()
    {
        return productCategory;
    }
}

class InventorySearch
{
    public static InventoryProduct linearSearch(
            InventoryProduct[] inventoryProducts,
            int searchProductId)
    {
        for (int currentIndex = 0;
             currentIndex < inventoryProducts.length;
             currentIndex++)
        {
            if (inventoryProducts[currentIndex]
                    .getProductId() == searchProductId)
            {
                return inventoryProducts[currentIndex];
            }
        }

        return null;
    }

    public static InventoryProduct binarySearch(
            InventoryProduct[] inventoryProducts,
            int searchProductId)
    {
        int lowIndex = 0;
        int highIndex = inventoryProducts.length - 1;

        while (lowIndex <= highIndex)
        {
            int middleIndex =
                    lowIndex + (highIndex - lowIndex) / 2;

            int middleProductId =
                    inventoryProducts[middleIndex]
                            .getProductId();

            if (middleProductId == searchProductId)
            {
                return inventoryProducts[middleIndex];
            }
            else if (middleProductId < searchProductId)
            {
                lowIndex = middleIndex + 1;
            }
            else
            {
                highIndex = middleIndex - 1;
            }
        }

        return null;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        InventoryProduct[] unsortedInventoryProducts =
        {
            new InventoryProduct(
                    305,
                    "Intel Core i7 Processor",
                    "Computer Components"),

            new InventoryProduct(
                    101,
                    "Samsung SSD 1TB",
                    "Storage Devices"),

            new InventoryProduct(
                    404,
                    "ASUS Gaming Monitor",
                    "Display Devices"),

            new InventoryProduct(
                    202,
                    "Logitech Mechanical Keyboard",
                    "Computer Accessories")
        };

        InventoryProduct[] sortedInventoryProducts =
        {
            new InventoryProduct(
                    101,
                    "Samsung SSD 1TB",
                    "Storage Devices"),

            new InventoryProduct(
                    202,
                    "Logitech Mechanical Keyboard",
                    "Computer Accessories"),

            new InventoryProduct(
                    305,
                    "Intel Core i7 Processor",
                    "Computer Components"),

            new InventoryProduct(
                    404,
                    "ASUS Gaming Monitor",
                    "Display Devices")
        };

        InventoryProduct linearSearchResult =
                InventorySearch.linearSearch(
                        unsortedInventoryProducts,
                        202);

        if (linearSearchResult != null)
        {
            System.out.println(
                    "Product found using Linear Search");
        }
        else
        {
            System.out.println(
                    "Product not found using Linear Search");
        }

        InventoryProduct binarySearchResult =
                InventorySearch.binarySearch(
                        sortedInventoryProducts,
                        404);

        if (binarySearchResult != null)
        {
            System.out.println(
                    "Product found using Binary Search");
        }
        else
        {
            System.out.println(
                    "Product not found using Binary Search");
        }
    }
}