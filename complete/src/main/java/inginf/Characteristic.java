package inginf;
public class Characteristic {
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
    public Characteristic(String name) {
        setName(name);
    }    
}
