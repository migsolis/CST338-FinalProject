package msolis.program.com.cst338finalproject.ReqClasses;

import android.os.Parcel;
import android.os.Parcelable;

// stores relevant information for a machine, a lot of it is for future functionality
// this app only used the description.
public class MachineCenter implements Listable, Parcelable {

    private int assetID;
    private String description;
    private String manufacturer;
    private String model;
    private String serialNum;

    public MachineCenter(String description) {
        this.description = description;
    }

    public MachineCenter(String description, String manufacturer) {
        this.description = description;
        this.manufacturer = manufacturer;
    }

    public MachineCenter(int assetID, String description, String manufacturer, String model, String serialNum) {
        this.assetID = assetID;
        this.description = description;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNum = serialNum;
    }

    protected MachineCenter(Parcel in) {
        assetID = in.readInt();
        description = in.readString();
        manufacturer = in.readString();
        model = in.readString();
        serialNum = in.readString();
    }

    public static final Creator<MachineCenter> CREATOR = new Creator<MachineCenter>() {
        @Override
        public MachineCenter createFromParcel(Parcel in) {
            return new MachineCenter(in);
        }

        @Override
        public MachineCenter[] newArray(int size) {
            return new MachineCenter[size];
        }
    };

    public int getAssetID() {
        return assetID;
    }

    public boolean setAssetID(int assetID) {
        if(assetID <= 0)
            return false;
        this.assetID = assetID;
        return true;
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        if(description == null || description.equals(""))
            return false;
        this.description = description;
        return true;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public boolean setManufacturer(String manufacturer) {
        if(manufacturer == null || manufacturer.equals(""))
            return false;
        this.manufacturer = manufacturer;
        return true;
    }

    public String getModel() {
        return model;
    }

    public boolean setModel(String model) {
        if(model == null || model.equals(""))
            return false;
        this.model = model;
        return true;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public boolean setSerialNum(String serialNum) {
        if(serialNum == null || serialNum.equals(""))
            return false;
        this.serialNum = serialNum;
        return true;
    }

    @Override
    public String itemDesc() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(assetID);
        dest.writeString(description);
        dest.writeString(manufacturer);
        dest.writeString(model);
        dest.writeString(serialNum);
    }
}
