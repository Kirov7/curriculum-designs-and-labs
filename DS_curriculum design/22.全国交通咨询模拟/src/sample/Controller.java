package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Controller {

    @FXML
    private RadioButton rbtnFast;

    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton rbtnCheap;

    @FXML
    private RadioButton rbtnLessChange;

    @FXML
    private TextField tfStartStation1;

    @FXML
    private TextField tfEndStation1;

    @FXML
    private Button btnInquire;

    @FXML
    private TextField tfCityURL;

    @FXML
    private Button btnChangeCityURL;

    @FXML
    private TextField tfDeleteCity;

    @FXML
    private Button btnDeleteCity;

    @FXML
    private TextField tfInsertCity;

    @FXML
    private Button btnInsertCity;

    @FXML
    private TextField tfTrainURL;

    @FXML
    private Button btnTrainURL;

    @FXML
    private TextField tfTrainChange;

    @FXML
    private Button btnTrain;

    @FXML
    private TextField tfStartStation;

    @FXML
    private TextField tfEndStation;

    @FXML
    private Button btnDeleteTrain;

    @FXML
    private TextArea taResult;

    @FXML
    private ImageView viewShow;

    public ImageView showPicture(){
        Image image=new Image("file:image/image.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        return imageView;
    }

    @FXML
    void btnChangeCityURLEvent(ActionEvent event) {
        String newURL = tfCityURL.getText();
        if (Utils.setURL(newURL)) {
            AlertWindow_base.display("提示", "更改成功！回到主界面稍等片刻即可查询该信息！");
        } else
            AlertWindow_base.display("提示", "输入不规范，请重新输入！");
    }

    @FXML
    void btnDeleteCityEvent(ActionEvent event) {
        String city = tfDeleteCity.getText();
        try {
            if (Utils.deleteCity(city)) {
                AlertWindow_base.display("提示", "删除成功！回到主界面稍等片刻即可查询该信息！");
            } else
                AlertWindow_base.display("提示", "输入不规范，请重新输入！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteTrainEvent(ActionEvent event) {
        String start = tfStartStation.getText();
        String end = tfEndStation.getText();

        boolean flag = false;

        try {
            flag = Utils.deleteInfo(start, end);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag)
            AlertWindow_base.display("提示", "删除成功！");
        else
            AlertWindow_base.display("提示", "不存在该路线，删除失败！");

    }

    @FXML
    void btnInquireEvent(ActionEvent event) {
        String select = "";
        String rbtu = group.getSelectedToggle().toString();
        if (String.valueOf(rbtu.charAt(rbtu.length() - 2)).equals("达"))
            select = "最快到达";
        if (String.valueOf(rbtu.charAt(rbtu.length() - 2)).equals("销"))
            select = "最小开销";

        MGraph mg = null;
        try {
            mg = Utils.readInfo();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }


        String startStation = tfStartStation1.getText();
        String endStation = tfEndStation1.getText();

//				System.out.println("startStation"+startStation+"        endStation"+endStation);

        if (!mg.stationName.contains(startStation) && !mg.stationName.contains(endStation)) {
            AlertWindow_base.display("错误", "起始站和终点站都不存在，请重新输入或编辑新信息！");
        } else if (startStation.equals(endStation)) {
            AlertWindow_base.display("错误", "起始站和终点站不能相同，请重新输入！");
        } else if (!mg.stationName.contains(startStation) && mg.stationName.contains(endStation)) {
            AlertWindow_base.display("错误", "起始站不存在，请重新输入或编辑新信息！");

        } else if (mg.stationName.contains(startStation) && !mg.stationName.contains(endStation)) {
            AlertWindow_base.display("错误", "终点站不存在，请重新输入或编辑新信息！");
        } else {
            String path = null;    //路径图
            StringBuilder sb = new StringBuilder();
            int costInfo;
            int startIndex = mg.stationName.indexOf(startStation);    //始发站对应的索引
            int endIndex = mg.stationName.indexOf(endStation);        //终点站对应的索引
            path = mg.path[startIndex][endIndex];

            if (select.equals("最快到达")) {
                costInfo = mg.timeMatrix[startIndex][endIndex];
            } else {
                costInfo = mg.DistancMatrix[startIndex][endIndex];
            }
            try {
                sb = Utils.getPathInfo(path, select, costInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            taResult.setText("车次  始发站  终点站  发车时间 到站时间 费用\r\n");
            taResult.appendText(sb.toString());
        }

    }


    @FXML
    void btnInsertCityEvent(ActionEvent event) {
        String info = tfInsertCity.getText();
        String cityInsert = "TEMP-->" + info +"-->  -->  -->  -->  ";
        try {
            Utils.addInfo(cityInsert);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AlertWindow_base.display("提示", "添加成功！");
    }

    @FXML
    void btnTrainEvent(ActionEvent event) {
        String info = tfTrainChange.getText();
        boolean flag = false;

        try {
            flag = Utils.addInfo(info);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (flag)
            AlertWindow_base.display("提示", "添加成功！回到主界面稍等片刻即可查询该信息！");
        else
            AlertWindow_base.display("提示", "输入不规范，请重新输入！");
    }


    @FXML
    void btnTrainURLEvent(ActionEvent event) {
        String newURL = tfTrainURL.getText();
        if (Utils.setURL(newURL)) {
            AlertWindow_base.display("提示", "更改成功！回到主界面稍等片刻即可查询该信息！");
        } else
            AlertWindow_base.display("提示", "输入不规范，请重新输入！");
    }

}
