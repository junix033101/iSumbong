package com.example.isumbong;

public class INPUTS {

    private String vnum;//victim info
    private String Img_accident;//accident info
    private String Text_license;//accident info
    private String Img_license; //accident info
    private String Plate;
    private String Statement;


    public INPUTS(String vnum, String text_license, String img_accident, String img_license, String plate, String statement) {
        this.vnum = vnum;
        this.Img_accident = img_accident;
        this.Text_license = text_license;
        this.Img_license = img_license;
        this.Plate = plate;
        this.Statement = statement;

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



    public String getImg_accident() {
        return Img_accident;
    }

    public void setImg_accident(String img_accident) {
        Img_accident = img_accident;
    }


    public String getImg_license() {
        return Img_license;
    }

    public void setImg_license(String img_license) {
        Img_license = img_license;
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
