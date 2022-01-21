package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.text.NumberFormat;
import java.util.*;

public class Controller {
    // 操作数1，为了程序的安全，初值一定设置，这里我们设置为0。
    String str1 = "0";
    // 操作数2
    String str2 = "0";
    // 运算结果
    String result = "";

    // 以下k1至k2为状态开关
    // 开关1用于选择输入方向，将要写入str1或str2
    int k1 = 1;



    int polynomialNums = 0;
    boolean changeover = false;
    boolean isResult = false;
    boolean evaluation = false;
    boolean evaluation1 = false;

    @SuppressWarnings("rawtypes")
    ArrayList list = new ArrayList(20);
    Stack<String> bracketStack = new Stack<>();
    //外层是大的多项式,内层是每个大多项式的各个小项
    ArrayList<ArrayList<String>> Lists = new ArrayList<ArrayList<String>>() {{
        add(new ArrayList<>());
    }};
    TreeMap<Double, Double> sortedMap = new TreeMap<>((o1, o2) -> -Double.compare(o1, o2));

    @FXML
    private Button btnHiahiahia;

    @FXML
    private Button btnLeftBracket;

    @FXML
    private Button btnRightBracket;

    @FXML
    private Button btnSP;

    @FXML
    private Button btnSimp;

    @FXML
    private Button btnSeven;

    @FXML
    private Button btnEight;

    @FXML
    private Button btnNine;

    @FXML
    private Button btnPower;

    @FXML
    private Button btnValue;

    @FXML
    private Button btnFour;

    @FXML
    private Button btnFive;

    @FXML
    private Button btnSix;

    @FXML
    private Button btnMinus;

    @FXML
    private Button btnDrivation;

    @FXML
    private Button btnOne;

    @FXML
    private Button btnTwo;

    @FXML
    private Button btnThree;

    @FXML
    private Button btnPlus;

    @FXML
    private Button btnX;

    @FXML
    private Button btnPOM;

    @FXML
    private Button btnZero;

    @FXML
    private Button btnPoint;

    @FXML
    private Button btnEqual;

    @FXML
    private RadioButton rbtnPolynomial;

    @FXML
    private ToggleGroup rbtnGroup;

    @FXML
    private RadioButton rbtnValue;

    @FXML
    private RadioButton rbtnDrivation;

    @FXML
    private TextField TfResult;

    @FXML
    private TextArea taOperation;

    @FXML
    void keyBoardEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.EQUALS)
            do_equals_event();

        System.out.println(event.getCode());
        if (event.isShiftDown() && event.getCode() == KeyCode.DIGIT6){
            do_number_event("^");
            return;
        }
        if (event.isShiftDown() && event.getCode() == KeyCode.DIGIT9){
            do_bracket_event("(");
            return;
        }
        if (event.isShiftDown() && event.getCode() == KeyCode.DIGIT0){
            do_bracket_event(")");
            return;
        }

        if (event.getCode() == KeyCode.DIGIT1
                || event.getCode() == KeyCode.DIGIT2
                || event.getCode() == KeyCode.DIGIT3
                || event.getCode() == KeyCode.DIGIT4
                || event.getCode() == KeyCode.DIGIT5
                || event.getCode() == KeyCode.DIGIT6
                || event.getCode() == KeyCode.DIGIT7
                || event.getCode() == KeyCode.DIGIT8
                || event.getCode() == KeyCode.DIGIT9
                || event.getCode() == KeyCode.DIGIT0
                || event.getCode() == KeyCode.NUMPAD1
                || event.getCode() == KeyCode.NUMPAD2
                || event.getCode() == KeyCode.NUMPAD3
                || event.getCode() == KeyCode.NUMPAD4
                || event.getCode() == KeyCode.NUMPAD5
                || event.getCode() == KeyCode.NUMPAD6
                || event.getCode() == KeyCode.NUMPAD7
                || event.getCode() == KeyCode.NUMPAD8
                || event.getCode() == KeyCode.NUMPAD9
                || event.getCode() == KeyCode.NUMPAD0
                || event.getCode() == KeyCode.X){
            do_number_event (String.valueOf(event.getCode().name().charAt(event.getCode().name().length() - 1)));
            return;
        }

        if (event.getCode() == KeyCode.SUBTRACT) {
            do_mark_event("-");
        } else if (event.getCode() == KeyCode.ADD) {
            do_mark_event("+");
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            do_backspace_event();
        } else if (event.getCode() == KeyCode.DECIMAL) {
            do_decimalPoint_event();
        }
    }

    @FXML
    void btnDerivationEvent(ActionEvent event) {
        rbtnDrivation.fire();
        do_equals_event();
    }


    @FXML
    void btnLeftBracketEvent(ActionEvent event) {
        do_bracket_event(event);
    }


    @FXML
    void btnPOMEvent(ActionEvent event) {
        String tfText = TfResult.getText();
        String taText = taOperation.getText();
        if (tfText.equals("")) {
            if (taOperation.getText().endsWith(" + ")) {
                taOperation.deleteText(taText.length() - 3, taText.length());
                taOperation.appendText(" " + "-" + " ");
            } else if (taOperation.getText().endsWith(" - ")) {
                taOperation.deleteText(taText.length() - 3, taText.length());
                taOperation.appendText(" " + "+" + " ");
            }
        }
    }

    @FXML
    void btnPowerEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnRightBracketEvent(ActionEvent event) {
        do_bracket_event(event);
    }

    @FXML
    void btnSimpEvent(ActionEvent event) {
        rbtnPolynomial.fire();
        do_equals_event();
    }

    @FXML
    void btnValueEvent(ActionEvent event) {
        rbtnValue.fire();
        do_equals_event();
    }

    @FXML
    void btnXEvent(ActionEvent event) {
        do_number_event(event);
    }


    @FXML
    void rbtnDrivationEvent(ActionEvent event) {

    }

    @FXML
    void rbtnPolynomialEvent(ActionEvent event) {

    }

    @FXML
    void rbtnValueEvent(ActionEvent event) {
    }

    @FXML
    void btnPiEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnEEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnCEvent(ActionEvent event) {
        do_clear_event(event);
    }

    @FXML
    void btnEightEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnEqualEvent(ActionEvent event) {
        do_equals_event();
    }

    @FXML
    void btnFiveEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnFourEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnHiahiahiaEvent(ActionEvent event) throws InterruptedException {
        if (!TfResult.getText().equals("ψ(｀∇´)ψ hia~hia~hia~hia~hia~ ")) {
            do_clear_event(event);
            TfResult.setText("ψ(｀∇´)ψ hia~hia~hia~hia~hia~ ");
        } else
            do_clear_event(event);
    }

    @FXML
    void btnLgEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnLnEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnMinusEvent(ActionEvent event) {
        do_mark_event(event);
    }

    @FXML
    void btnMultEvent(ActionEvent event) {
        do_mark_event(event);
    }

    @FXML
    void btnNineEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnOneEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnPlusEvent(ActionEvent event) {
        do_mark_event(event);
    }

    @FXML
    void btnPointEvent(ActionEvent event) {
        do_decimalPoint_event();
    }

    @FXML
    void btnSevenEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnSixEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnSPEvent(ActionEvent event) {
        do_backspace_event();
    }

    @FXML
    void btnThreeEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnTwoEvent(ActionEvent event) {
        do_number_event(event);
    }

    @FXML
    void btnZeroEvent(ActionEvent event) {
        do_number_event(event);
    }

    public void do_bracket_event(ActionEvent event) {
        String ss = ((Button) event.getSource()).getText();
        do_bracket_event(ss);
    }

    private void do_bracket_event(String ss){
        String tfText = TfResult.getText();
        String taText = taOperation.getText();
        if (isResult){
            TfResult.setText("");
            taOperation.setText("");
            isResult = false;
        }
        //左括号:如果上一个list不为空的话就开启一个新的list即开启新的一个多项式
        if (ss.equals("(")) {
            if (!taOperation.getText().endsWith(")")) {
                if (bracketStack.isEmpty()) {
                    bracketStack.push("(");
                    taOperation.appendText("(");
                    if (taText.endsWith(" - ")) {
                        changeover = true;
                    }
                    if (Lists.get(polynomialNums).size() == 0) {
                        return;
                    }
                    polynomialNums++;
                    Lists.add(new ArrayList<>());
                }
            }
            return;
        }
        if (!bracketStack.empty()) {
            if (!tfText.endsWith("^") || !taOperation.getText().endsWith("(")) {
                bracketStack.pop();
                TfResult.setText("");
                taOperation.appendText(tfText + ")");
            }

            //括起来时把该多项式中的最后一项加入到ArrayList里面,开启新的一个多项式
            if (!tfText.equals("")) {
                ArrayList<String> list = Lists.get(polynomialNums);
                if (changeover) {
                    if (taText.endsWith(" + ") || taOperation.getText().endsWith("("))
                        list.add("-" + tfText);
                    else
                        list.add(tfText);
                } else {
                    if (taText.endsWith(" - "))
                        list.add("-" + tfText);
                    else
                        list.add(tfText);
                }
            }
            changeover = false;
            polynomialNums++;
            Lists.add(new ArrayList<>());
        }
    }

    public void do_number_event(ActionEvent event) {
        // 获取事件源（按钮）的文本值
        String ss = ((Button) event.getSource()).getText();
        do_number_event(ss);

    }

    public void do_number_event(String ss){
        String tfText = TfResult.getText();
        if (isResult){
            TfResult.setText("");
            taOperation.setText("");
            sortedMap = new TreeMap<>((o1, o2) -> -Double.compare(o1, o2));
            isResult = false;
        }
        //右括号后面不可以加数字和X
        if (tfText.endsWith(")"))
            return;
        //X后面不可以跟X
        if (Objects.equals(ss, "X") && tfText.contentEquals("X"))
            return;
        //^后面不可以跟X
        if (tfText.endsWith("X") && !Objects.equals(ss, "^"))
            return;
        //^只能跟在X后面
        if (!tfText.endsWith("X") && Objects.equals(ss, "^"))
            return;
        //)后面不可以跟数字
        if (taOperation.getText().endsWith(")"))
            return;
        //指数负号后面只能跟数字
        if (tfText.endsWith("-") && ss.matches("[0-9]")) {
            TfResult.appendText(ss);
            return;
        }

        if (Objects.equals(str1, "0"))
            str1 = ss;
        else
            str1 = str1 + ss;
        TfResult.setText(str1);

    }

    /**
     * 操作结果：运算符号键处理事件
     *
     * @param event
     */
    public void do_mark_event(ActionEvent event) {
        String ss2 = ((Button) event.getSource()).getText();
        do_mark_event(ss2);
    }

    private void do_mark_event(String ss2){

        if (isResult){
            TfResult.setText("");
            isResult = false;
        }

        StringBuilder taText = new StringBuilder(taOperation.getText());
        String tfText = TfResult.getText();
        //指数的负号
        if (tfText.endsWith("X^") && ss2.equals("-")) {
            TfResult.appendText("-");
            return;
        }
        //两个区域都没有数据,输出第一个负号
        if (Objects.equals(taOperation.getText(), "") && tfText.equals("")) {
            if (Objects.equals(ss2, "-")) {
                taOperation.setText(taText.append(" - ").toString());
            }
            return;
        }
        //tf区为空,改变ta区域中上一个+/-号
        if (tfText.equals("")) {
            if (taOperation.getText().endsWith(" + ") || taOperation.getText().endsWith(" - ")) {
                taOperation.deleteText(taText.length() - 3, taText.length());
                taOperation.appendText(" " + ss2 + " ");
                if (taOperation.getText().equals(" + "))
                    taOperation.deleteText(taText.length() - 3, taText.length());
                return;
            }
        }
        //前一个字符为^或^-则不输出
        if (tfText.endsWith("^") || tfText.endsWith("^-"))
            return;


        if (!tfText.equals("")) {
            ArrayList<String> list = Lists.get(polynomialNums);
            if (changeover) {
                if (taOperation.getText().endsWith(" + ") || taOperation.getText().endsWith("("))
                    list.add("-" + tfText);
                else
                    list.add(tfText);
            } else {
                if (taOperation.getText().endsWith(" - "))
                    list.add("-" + tfText);
                else
                    list.add(tfText);
            }
        }
        //正常添加
        taText.append(tfText).append(" ").append(ss2).append(" ");
        taOperation.setText(taText.toString());
        TfResult.setText("");
        str1 = "";

    }

    /**
     * 操作结果：清除键处理事件
     *
     * @param event
     */
    public void do_clear_event(ActionEvent event) {
        k1 = 1;
        str1 = "0";
        str2 = "0";
        result = "";
        TfResult.setText(result);
        taOperation.setText(result);
        list.clear();
        polynomialNums = 0;
        bracketStack.clear();
        Lists = new ArrayList<>();
        Lists.add(new ArrayList<>());
        TreeMap<Double, Double> sortedMap = new TreeMap<>((o1, o2) -> -Double.compare(o1, o2));
    }

    /**
     * 操作结果：等号键处理事件
     *
     */
    public void do_equals_event() {
        String tfText = TfResult.getText();
        String taText = taOperation.getText();
        if (isResult){
            TfResult.setText("");
        }

        if (!resultPreOperate())
            return;
        String rbtu = rbtnGroup.getSelectedToggle().toString();
        switch (rbtu.charAt(rbtu.length() - 2)) {
            case '式': {
                Polynomial();
                break;
            }
            case '值': {
                if (taOperation.getText().contains("->求值,") && taOperation.getText().endsWith("X = "))
                    return;
                if (!evaluation){
                    taOperation.appendText("->求值, X = ");
                    str1 = "";
                    evaluation = true;
                    return;
                }
                if (taText.endsWith("- "))
                    Value("-" + tfText);
                else
                    Value(tfText);
                sortedMap = new TreeMap<>((o1, o2) -> -Double.compare(o1, o2));
                break;
            }
            case '导': {
                if (taOperation.getText().contains("->求导,") && taOperation.getText().endsWith(") ="))
                    return;
                if (!evaluation1){
                    taOperation.appendText("->求导, f'(x) = ");
                    str1 = "";
                    evaluation1 = true;
                    return;
                }
                derivation();
                sortedMap = new TreeMap<>((o1, o2) -> -Double.compare(o1, o2));
                break;
            }
        }


        // 还原各个开关的状态
        k1 = 1;
        str1 = "0";
        str2 = "0";
        result = "";
        isResult = true;
        evaluation = false;
        evaluation1 = false;
        bracketStack.clear();
        polynomialNums = 0;
        Lists = new ArrayList<>();
        Lists.add(new ArrayList<>());


    }

    /**
     * 操作结果：小数点键处理事件
     *
     */
    public void do_decimalPoint_event() {
        String tfText = TfResult.getText();
        String[] strs;
        //小数点加在X系数位置上
        if (!tfText.contains("X")){
            if (tfText.contains("."))
                return;
            str1 = str1 + ".";
            TfResult.setText(str1);
            return;
        }
        //小数点加在指数上
        if (!tfText.endsWith("X")){
            strs = tfText.split("X");
            if (strs[1].contains("."))
                return;
            str1 = str1 + ".";
            TfResult.setText(str1);
        }
    }

    /**
     * 操作结果：删除最后一个字符
     *
     */
    public void do_backspace_event() {
        if (k1 == 1) {
            str1 = str1.substring(0, str1.length() - 1);
            TfResult.setText(str1);
        }
        if (k1 == 2) {
            str2 = str2.substring(0, str2.length() - 1);
            TfResult.setText(str2);
        }
    }


    /**
     * 计算多项式
     */
    public void Polynomial() {

        String coefficient = "";
        String exponent = "";

        //把多项式的数据录入到map当中
        for (ArrayList<String> strs : Lists) {
            if (strs.size() == 0)
                continue;
            for (String str : strs) {
                if (str.equals(""))
                    continue;
                //常数项
                if (!str.contains("X")) {
                    if (sortedMap.containsKey(0.0)) {
                        sortedMap.put(0.0, Double.parseDouble(str) + sortedMap.get(0.0));
                    } else {
                        sortedMap.put(0.0, Double.parseDouble(str));
                    }
                    continue;
                }

                //省略了指数1
                if (!str.contains("^")) {
                    String strTemp;
                    //是否已经存在指数为一的项
                    if (sortedMap.containsKey(1.0)) {
                        if (str.equals("-X"))
                            sortedMap.put(1.0, sortedMap.get(1.0) - 1);
                        else if (str.equals("X"))
                            sortedMap.put(1.0, sortedMap.get(1.0) + 1);
                        else
                            sortedMap.put(1.0, Double.parseDouble(str.replace("X", "")) + sortedMap.get(1.0));
                    } else {
                        if (str.equals("-X"))
                            sortedMap.put(1.0, -1.0);
                        else if (str.equals("X"))
                            sortedMap.put(1.0, 1.0);
                        else
                            sortedMap.put(1.0, Double.parseDouble(str.replace("X", "")));                    }
                    continue;
                }
                //使用X^分离系数和指数

                //如果省略了系数1
                String[] strsTemp = str.split("X\\^");

                if (str.startsWith("X")) {
                    if (sortedMap.containsKey(Double.parseDouble(strsTemp[1])))
                        sortedMap.put(Double.parseDouble(strsTemp[1]), sortedMap.get(Double.parseDouble(strsTemp[1])) + 1.0);
                    else
                        sortedMap.put(Double.parseDouble(strsTemp[1]), 1.0);
                    continue;
                }

                if (str.startsWith("-X")) {
                    if (sortedMap.containsKey(Double.parseDouble(strsTemp[1])))
                        sortedMap.put(Double.parseDouble(strsTemp[1]), sortedMap.get(Double.parseDouble(strsTemp[1])) - 1.0);
                    else
                        sortedMap.put(Double.parseDouble(strsTemp[1]), -1.0);
                    continue;
                }

                if (sortedMap.containsKey(Double.parseDouble(strsTemp[1])))
                    sortedMap.put(Double.parseDouble(strsTemp[1]), sortedMap.get(Double.parseDouble(strsTemp[1])) + Double.parseDouble(strsTemp[0]));
                else
                    sortedMap.put(Double.parseDouble(strsTemp[1]), Double.parseDouble(strsTemp[0]));
            }
        }

        for (Map.Entry<Double, Double> Entry : sortedMap.entrySet()) {
            //判断系数是否为整数并赋值,如果系数为1且非常数项则省略,如果为整数则化为整型
            if (Entry.getValue().equals(1.0) && !Entry.getKey().equals(0.0)){
                coefficient = "";
            }else if (String.valueOf(Entry.getValue()).endsWith(".0")){
                coefficient = String.valueOf(Entry.getValue()).replace(".0", "");
            }else {
                coefficient = String.valueOf(Entry.getValue());
            }
            //判断指数是否为整数并赋值,如果为整数则化为整型
            if (String.valueOf(Entry.getKey()).endsWith(".0")){
                exponent = String.valueOf(Entry.getKey()).replace(".0", "");
            }else {
                exponent = String.valueOf(Entry.getKey());
            }

            //系数为0的项不打印省略掉
            if (Entry.getValue() == 0.0)
                continue;
            //如果指数为0则说明是常数项,只打印系数
            if (Entry.getKey() == 0) {
                if (Entry.getValue().toString().startsWith("-")) {
                    TfResult.appendText(coefficient);
                } else {
                    if (TfResult.getText().equals("")) {
                        TfResult.appendText(coefficient);
                    } else
                        TfResult.appendText("+" + coefficient);
                }
                continue;
            }
            //如果指数为1则省略掉指数部分
            if (Entry.getKey() == 1) {
                //如果系数带负号
                if (Entry.getValue().toString().startsWith("-")) {
                    TfResult.appendText(coefficient + "X");
                }
                //如果系数不带负号不是首项则加上加号
                else {
                    if (TfResult.getText().equals("")) {
                        TfResult.appendText(coefficient + "X");
                    } else
                        TfResult.appendText("+" + coefficient + "X");
                }
                continue;
            }
            //如果系数带负号
            if (Entry.getValue().toString().startsWith("-")) {
                TfResult.appendText(coefficient + "X^" + exponent);
            }
            //系数不带负号,若不是第一项则加上加号
            else {
                if (TfResult.getText().equals("")) {
                    TfResult.appendText(coefficient + "X^" + exponent);
                } else
                    TfResult.appendText("+" + coefficient + "X^" + exponent);
            }
        }
        //如果无结果输出则显示0
        if (TfResult.getText().equals(""))
            TfResult.appendText("0");
    }

    private void Value(String value){

        String strTemp = "";
        String[] strsTemp;
        double valueResult = 0;

        for (ArrayList<String> strs : Lists) {
            if (strs.size() == 0)
                continue;
            for (String str : strs) {
                if (str.equals(""))
                    continue;
                //如果省略了指数和系数即为X
                if (str.equals("X")){
                    valueResult += Double.parseDouble(value);
                    continue;
                }
                if (str.equals("-X")){
                    valueResult += -Double.parseDouble(value);
                    continue;
                }
                //不包含X即常数项
                if (!str.contains("X")){
                    valueResult += Double.parseDouble(str);
                    continue;
                }
                //去除X
                strTemp = str.replace("X","");
                //不包含^即指数为1
                if (!str.contains("^")){
                    valueResult += Double.parseDouble(strTemp) * Double.parseDouble(value);
                    continue;
                }
                //起始为X即系数为1,只需要计算X^x
                if (str.startsWith("X")){
                    strTemp = str.replace("X^","");
                    valueResult += Math.pow(Double.parseDouble(value),Double.parseDouble(strTemp));
                    continue;
                }
                //-X^x的情况
                if (str.startsWith("-X")){
                    strTemp = str.replace("-X^","");
                    valueResult += -Math.pow(Double.parseDouble(value),Double.parseDouble(strTemp));
                    continue;
                }
                //此时指数和系数一定没有省略
                strsTemp = strTemp.split("\\^");
                valueResult += Double.parseDouble(strsTemp[0]) * Math.pow(Double.parseDouble(value),Double.parseDouble(strsTemp[1]));
            }
            TfResult.setText(NumberFormat.getInstance().format(valueResult));
        }
        if (TfResult.getText().equals(""))
            TfResult.appendText("0");
    }

    private void derivation(){
        //系数
        String coefficient = "";
        //指数
        String exponent = "";

        if (taOperation.getText().equals(""))
            return;
        for (ArrayList<String> strs : Lists) {
            if (strs.size() == 0)
                continue;
            for (String str : strs) {
                if (str.equals(""))
                    continue;
                //常数项
                if (!str.contains("X")) {
                    if (sortedMap.containsKey(0.0)) {
                        sortedMap.put(0.0, Double.parseDouble(str) + sortedMap.get(0.0));
                    } else {
                        sortedMap.put(0.0, Double.parseDouble(str));
                    }
                    continue;
                }

                //省略了指数1
                if (!str.contains("^")) {
                    String strTemp;
                    //是否已经存在指数为一的项
                    if (sortedMap.containsKey(1.0)) {
                        if (str.equals("-X"))
                            sortedMap.put(1.0, sortedMap.get(1.0) - 1);
                        else if (str.equals("X"))
                            sortedMap.put(1.0, sortedMap.get(1.0) + 1);
                        else
                            sortedMap.put(1.0, Double.parseDouble(str.replace("X", "")) + sortedMap.get(1.0));
                    } else {
                        if (str.equals("-X"))
                            sortedMap.put(1.0, -1.0);
                        else if (str.equals("X"))
                            sortedMap.put(1.0, 1.0);
                        else
                            sortedMap.put(1.0, Double.parseDouble(str.replace("X", "")));                    }
                    continue;
                }
                //使用X^分离系数和指数

                //如果省略了系数1
                String[] strsTemp = str.split("X\\^");

                if (str.startsWith("X")) {
                    if (sortedMap.containsKey(Double.parseDouble(strsTemp[1])))
                        sortedMap.put(Double.parseDouble(strsTemp[1]), sortedMap.get(Double.parseDouble(strsTemp[1])) + 1.0);
                    else
                        sortedMap.put(Double.parseDouble(strsTemp[1]), 1.0);
                    continue;
                }

                if (str.startsWith("-X")) {
                    if (sortedMap.containsKey(Double.parseDouble(strsTemp[1])))
                        sortedMap.put(Double.parseDouble(strsTemp[1]), sortedMap.get(Double.parseDouble(strsTemp[1])) - 1.0);
                    else
                        sortedMap.put(Double.parseDouble(strsTemp[1]), -1.0);
                    continue;
                }

                if (sortedMap.containsKey(Double.parseDouble(strsTemp[1])))
                    sortedMap.put(Double.parseDouble(strsTemp[1]), sortedMap.get(Double.parseDouble(strsTemp[1])) + Double.parseDouble(strsTemp[0]));
                else
                    sortedMap.put(Double.parseDouble(strsTemp[1]), Double.parseDouble(strsTemp[0]));
            }
        }

        for (Map.Entry<Double, Double> Entry : sortedMap.entrySet()) {
            //判断系数是否为整数并赋值,如果系数为1且非常数项则省略,如果为整数则化为整型
            if ((Entry.getValue() * Entry.getKey()) == 1.0 && !Entry.getKey().equals(0.0)) {
                coefficient = "";
            } else if (String.valueOf(Entry.getValue() * Entry.getValue()).endsWith(".0")) {
                coefficient = String.valueOf(Entry.getKey() * Entry.getValue()).replace(".0", "");
            } else {
                coefficient = String.valueOf(Entry.getKey() * Entry.getValue());
            }
            //判断指数是否为整数并赋值,如果为整数则化为整型
            if (String.valueOf(Entry.getKey() - 1).endsWith(".0")) {
                exponent = String.valueOf(Entry.getKey() - 1).replace(".0", "");
            } else {
                exponent = String.valueOf(Entry.getKey() - 1);
            }
            //系数为0的项不打印省略掉
            if (Entry.getValue() == 0.0)
                continue;
            //如果指数为0则说明是常数项,则不打印
            if (Entry.getKey() == 0) {
                continue;
            }
            //如果指数为1则省略掉X
            if (Entry.getKey() == 1) {
                //如果系数带负号
                if (String.valueOf(Entry.getValue()*Entry.getKey()).startsWith("-")) {
                    TfResult.appendText(coefficient);
                }
                //如果系数不带负号不是首项则加上加号
                else {
                    if (TfResult.getText().equals("")) {
                        TfResult.appendText(coefficient);
                    } else
                        TfResult.appendText("+" + coefficient);
                }
                continue;
            }
            //如果系数带负号
            if (String.valueOf(Entry.getValue()*Entry.getKey()).startsWith("-")) {
                TfResult.appendText(coefficient + "X^" + exponent);
            }
            //系数不带负号,若不是第一项则加上加号
            else {
                if (TfResult.getText().equals("")) {
                    TfResult.appendText(coefficient + "X^" + exponent);
                } else
                    TfResult.appendText("+" + coefficient + "X^" + exponent);
            }
        }
        //如果无结果输出则显示0
        if (TfResult.getText().equals(""))
            TfResult.appendText("0");
    }

    private Boolean resultPreOperate(){
        String taText = taOperation.getText();
        String tfText = TfResult.getText();
        //如果输入框中的是指数后的-或者指数负号^则不能输入
        if (TfResult.getText().endsWith("-") || TfResult.getText().endsWith("^"))
            return false;
        //如果能输入的话,将最后一项加入到Lists里面
        if (evaluation) {
            taOperation.appendText(TfResult.getText());
        }else {
            taOperation.appendText(TfResult.getText());
            ArrayList<String> list = Lists.get(polynomialNums);
            if (changeover) {
                if (taText.endsWith(" + ") || taText.endsWith("("))
                    list.add("-" + tfText);
                else
                    list.add(tfText);
            } else {
                if (taText.endsWith(" - "))
                    list.add("-" + tfText);
                else
                    list.add(tfText);
            }
        }
        TfResult.setText("");

        if (taOperation.getText().endsWith(" ")) {
            taOperation.deleteText(taText.length() - 3, taText.length());
        }

        //删除多余的运算符号
        if (taOperation.getText().endsWith(" ")) {
            taOperation.deleteText(taText.length() - 3, taText.length());
        }

        //补全未闭合的括号
        while (!bracketStack.isEmpty()) {
            //删除空白内容的括号
            if (taOperation.getText().endsWith("(")) {
                bracketStack.pop();
                taOperation.deleteText(taOperation.getText().length() - 1, taOperation.getText().length());
                taText = taOperation.getText();
                continue;
            }
            bracketStack.pop();
            taOperation.appendText(")");
            taText = taOperation.getText();
        }
        return true;
    }
}