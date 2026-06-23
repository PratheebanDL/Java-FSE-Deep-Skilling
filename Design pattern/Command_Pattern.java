interface Command {
    void execute();
    void undo();
}

class Light {
    public void turnOn() {
        System.out.println("The living room light is fully illuminated.");
    }

    public void turnOff() {
        System.out.println("The living room light is extinguished.");
    }
}

class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}

class RemoteControl {
    private Command slot;
    private Command previousCommand;

    public void setCommand(Command command) {
        this.slot = command;
    }

    public void pressButton() {
        if (slot != null) {
            slot.execute();
            previousCommand = slot;
        }
    }

    public void pressUndo() {
        if (previousCommand != null) {
            System.out.print("Undoing operation -> ");
            previousCommand.undo();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();
        Light livingRoomLight = new Light();

        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        remote.setCommand(lightOn);
        remote.pressButton();

        remote.setCommand(lightOff);
        remote.pressButton();
        
        remote.pressUndo();
    }
}