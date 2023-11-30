package inginf;
import java.util.ArrayList;
public class ItemInstance {

    String Name;
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        if (name == null || name.isEmpty()){
            throw new IllegalArgumentException(
                "Name must not be null");
        }
        Name = name;
    }
    double[] Position = new double[12];

    public double[] getPosition() {
        return Position;
    }

    Item Represents = null;
    public Item getRepresents() {
        return Represents;
    }
    void setRepresents(Item represents) {
        Represents = represents;
    }

    ArrayList<Characteristic> UsedBy = 
        new ArrayList<Characteristic>();
    public ArrayList<Characteristic> getUsedBy() {
        return UsedBy;
    }

    public boolean isInConfig(
        ArrayList<Characteristic> config) {
        if (getUsedBy().isEmpty()) {
            return true;
        }
        for (Characteristic c : config) {
            if (getUsedBy().contains(c)) {
                return true;
            }
        }
        return false;
    }
    public ItemInstance(String name, Item represents) {
        setName(name);
        setRepresents(represents);
    }
    public void printStructure(int inlineCount) {
        for (int i = 0; i < inlineCount; i++) {
            System.out.print("  ");
        }
        System.out.println(Name + ":");
        getRepresents().printStructure(inlineCount + 1);
    }
    public void printConfStructure(
        ArrayList<Characteristic> configuration, int inlineCount) {
        for (int i = 0; i < inlineCount; i++) {
            System.out.print("  ");
        }
        System.out.println(Name + ":");
        getRepresents().printConfStructure(
            configuration, inlineCount + 1);  
    }

}