package com.example.isumbong;

public class INPUTS {

    private String vnum;
    private String Text_license;
    private String Plate;
    private String Statement;

    public INPUTS(String vnum, String text_license, String plate, String statement) {
        this.vnum = vnum;
        Text_license = text_license;
        Plate = plate;
        Statement = statement;
    }
    public INPUTS(){

    }

    public String getVnum() {
        return vnum;
    }

    public void setVnum(String vnum) {
        this.vnum = vnum;
    }

    public String getText_license() {
        return Text_license;
    }

    public void setText_license(String text_license) {
        Text_license = text_license;
    }

    public String getPlate() {
        return Plate;
    }

    public void setPlate(String plate) {
        Plate = plate;
    }

    public String getStatement() {
        return Statement;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }

}
