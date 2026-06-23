class Computer
{
    private final String CPU;
    private final String Storage;
    private final String RAM;
    private final boolean Bluetooth;
    private final boolean Graphics;
    
    private Computer(Builder builder)
    {
        this.CPU = builder.CPU;
        this.Storage = builder.Storage;
        this.RAM = builder.RAM;
        this.Bluetooth = builder.Bluetooth;
        this.Graphics = builder.Graphics;
    }
    
    public void disp()
    {
        System.out.println("Build Completed");
    }
    
    static class Builder
    {
        private String CPU;
        private String Storage;
        private String RAM;
        private boolean Bluetooth;
        private boolean Graphics;
        
        public Builder(String CPU, String RAM, String Storage)
        {
            this.CPU = CPU;
            this.RAM = RAM;
            this.Storage = Storage;
        }
        
        public Builder setgraphics(boolean enable)
        {
            this.Graphics = enable;
            return this;
        }
        
        public Builder setbluetooth(boolean enable)
        {
            this.Bluetooth = enable;
            return this;
        }
        
        public Computer build()
        {
            return new Computer(this);
        }
    }
}

public class Main
{
    public static void main(String[] args) 
    {
        Computer gaming = new Computer.Builder("Intel I9", "2TB RAM", "2TB Storage").setgraphics(true).setbluetooth(true).build();
        gaming.disp();
    }
}