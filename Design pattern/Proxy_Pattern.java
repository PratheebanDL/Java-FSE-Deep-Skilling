interface Image
{
    void display(String imageName);
}

class RealImage implements Image
{
    private String imageName;

    public RealImage(String imageName)
    {
        this.imageName = imageName;
        loadImageFromServer();
    }

    public void loadImageFromServer()
    {
        System.out.println("Loading image from server: " + imageName);
    }

    @Override
    public void display(String imageName)
    {
        System.out.println("Displaying image: " + imageName);
    }
}

class ProxyImage implements Image
{
    private RealImage realImage;
    private String imageName;

    public ProxyImage(String imageName)
    {
        this.imageName = imageName;
    }

    @Override
    public void display(String imageName)
    {
        if (realImage == null)
        {
            realImage = new RealImage(imageName);
        }

        realImage.display(imageName);
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Image imageViewer = new ProxyImage("sample.jpg");

        imageViewer.display("sample.jpg");
    }
}