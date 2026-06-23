import java.util.HashMap;

class Product
{
    private int productId;
    private String productName;
    private int productQuantity;
    private int productPrice;

    public Product(int productId,
                   String productName,
                   int productQuantity,
                   int productPrice)
    {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public int getProductId()
    {
        return productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public int getProductQuantity()
    {
        return productQuantity;
    }

    public int getProductPrice()
    {
        return productPrice;
    }

    public void setProductQuantity(int productQuantity)
    {
        this.productQuantity = productQuantity;
    }

    public void setProductPrice(int productPrice)
    {
        this.productPrice = productPrice;
    }

    public void displayProduct()
    {
        System.out.println("Product ID      : " + productId);
        System.out.println("Product Name    : " + productName);
        System.out.println("Product Quantity: " + productQuantity);
        System.out.println("Product Price   : " + productPrice);
        System.out.println();
    }
}

class InventoryManager
{
    private final HashMap<Integer, Product> productInventory =
            new HashMap<>();

    public void addProduct(Product product)
    {
        if (productInventory.containsKey(product.getProductId()))
        {
            System.out.println(
                    "Product already exists : "
                    + product.getProductName());
        }
        else
        {
            productInventory.put(
                    product.getProductId(),
                    product);

            System.out.println("Product added successfully");
        }
    }

    public void updateProduct(int productId,
                              int updatedPrice,
                              int updatedQuantity)
    {
        Product product =
                productInventory.get(productId);

        if (product != null)
        {
            product.setProductPrice(updatedPrice);
            product.setProductQuantity(updatedQuantity);

            System.out.println(
                    "Product updated : "
                    + product.getProductId());
        }
        else
        {
            System.out.println(
                    "Product not found for update");
        }
    }

    public void deleteProduct(int productId)
    {
        Product product =
                productInventory.get(productId);

        if (product != null)
        {
            productInventory.remove(productId);

            System.out.println(
                    "Product removed successfully");
        }
        else
        {
            System.out.println(
                    "Product not found for deletion");
        }
    }

    public void displayInventory()
    {
        if (productInventory.isEmpty())
        {
            System.out.println(
                    "Inventory is empty");
        }
        else
        {
            for (Product product :
                    productInventory.values())
            {
                product.displayProduct();
            }
        }
    }
}

public class Main
{
    public static void main(String[] args)
    {
        InventoryManager inventoryManager =
                new InventoryManager();

        Product laptop =
                new Product(
                        1,
                        "Gaming Laptop",
                        15,
                        1200);

        Product mouse =
                new Product(
                        2,
                        "Wireless Mouse",
                        150,
                        25);

        Product keyboard =
                new Product(
                        3,
                        "Mechanical Keyboard",
                        45,
                        89);

        inventoryManager.addProduct(laptop);
        inventoryManager.addProduct(mouse);
        inventoryManager.addProduct(keyboard);

        inventoryManager.displayInventory();

        inventoryManager.updateProduct(
                1,
                2000,
                20);

        inventoryManager.deleteProduct(3);

        inventoryManager.displayInventory();
    }
}