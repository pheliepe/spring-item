package inginf;
import java.util.ArrayList;

public class Item {
    public int Id;
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }   
    public Item() {
        
    }
    
    String Nomenclature;
    public String getNomenclature() {
        return Nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        if (nomenclature == null || nomenclature.isEmpty()){
            throw new IllegalArgumentException(
                "Nomenclature must not be empty");
        }
        Nomenclature = nomenclature;
    }

    String Description;
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        if (description == null || description.isEmpty()){
            throw new IllegalArgumentException(
                "Description must not be empty");
        }
        Description = description;
    }
    String Material;
    public String getMaterial() {
        return Material;
    }
    public void setMaterial(String material) {
        if (material == null || material.isEmpty()){
            throw new IllegalArgumentException(
                "Material must not be empty");
        }
        Material = material;
    }


    int WeightedWeight = -1;
    public int getWeightedWeight() {
        return WeightedWeight;
    }
    public void setWeightedWeight(int weightedWeight) {
        if (weightedWeight < 0){
            throw new IllegalArgumentException(
                "WeightedWeight must not be negative");
        }
        WeightedWeight = weightedWeight;
    }
    int CalculatedWeight = -1;
    public int getCalculatedWeight() {
        return CalculatedWeight;
    }
    public void setCalculatedWeight(int calculatedWeight) {
        if (calculatedWeight < 0){
            throw new IllegalArgumentException(
                "CalculatedWeight must not be negative");
        }
        CalculatedWeight = calculatedWeight;
    }
    int EstimatedWeight = -1;

    public int getEstimatedWeight() {
        return EstimatedWeight;
    }

    public void setEstimatedWeight(int estimatedWeight) {
        if (estimatedWeight < 0){
            throw new IllegalArgumentException(
                "EstimatedWeight must not be negative");
        }
        EstimatedWeight = estimatedWeight;
    }

    int getBestWeight(){
        if (WeightedWeight >=0){
            return WeightedWeight;
        }
        else if (CalculatedWeight >=0){
            return CalculatedWeight;
        }
        else if (EstimatedWeight >=0){
            return EstimatedWeight;
        }
        else{
            // Schön wäre auch eine Exception
            return 0;
        }
    }

    ArrayList<ItemInstance> Uses = new ArrayList<ItemInstance>();
    public ArrayList<ItemInstance> getUses() {
        return Uses;
    }

    public Item(String nomenclature, 
        String description, 
        String material){
        setNomenclature(nomenclature);
        setDescription(description);
        setMaterial(material);
    }

   public void printConfStructure(
    ArrayList<Characteristic> configuration){
        printConfStructure(configuration,0);
    }

    void printConfStructure(
        ArrayList<Characteristic> configuration,
        int inlineCount){
        for (int i = 0; i < inlineCount; i++) {
            System.out.print("  ");
        }
        System.out.println(
            getNomenclature() + " " +
            getDescription() + " (" +
            getMaterial() + "/" + calcWeight() + " g)");
        
        for (ItemInstance itemInstance : 
                calcConfUses(configuration)) {
            itemInstance.printConfStructure(
                configuration, inlineCount+1);
        }
    }
    private ArrayList<ItemInstance> 
        calcConfUses(ArrayList<Characteristic> configuration) {
        ArrayList<ItemInstance> result = 
            new ArrayList<ItemInstance>();
        for (ItemInstance itemInstance : getUses()) {
            if (itemInstance.isInConfig(configuration)){
                result.add(itemInstance);
            }
        }
        return result;
    }

    public void printStructure(){
        printStructure(0);
    }

    void printStructure(int inlineCount){
        for (int i = 0; i < inlineCount; i++) {
            System.out.print("  ");
        }
        System.out.println(
            getNomenclature() + " " +
            getDescription() + " (" +
            getMaterial() + "/" + calcWeight() + " g)");
        
        for (ItemInstance itemInstance : getUses()) {
            itemInstance.printStructure(inlineCount+1);
        }
    }
    int calcWeight()
    {
        int sum = getBestWeight();
        for (ItemInstance itemInstance : getUses()) {
            sum += itemInstance.getRepresents().calcWeight();
        }
        return sum;
    }
}