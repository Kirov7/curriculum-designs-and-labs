package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {


    //标记备忘录
    static boolean[] flag;

    //最大容量
    static int SUM;

    //物体个数
    static int N;

    //物体重量数组
    static int[] vols;

    //解决办法
    static ArrayList answerList = new ArrayList();

    @FXML
    public TextField taInput;

    @FXML
    private TextArea tfResult;

    @FXML
    public Spinner<Integer> spinnerNum;

    @FXML
    private RadioButton rbtnhuisu;

    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton rbtDP;

    @FXML
    private Button btnRe;

    @FXML
    private Button btnGo;

    @FXML
    void btnGo(ActionEvent event) {
        String rbtu = group.getSelectedToggle().toString();
        switch (rbtu.charAt(rbtu.length() - 2)) {
            case '归': {
                recursionSolution();
                break;
            }
            case '法': {
                otherSolution();
                break;
            }
        }

    }

    @FXML
    void btnReEvent(ActionEvent event) {
        vols = null;
        flag = null;//标记
        N = 0;
        tfResult.setText("");
    }

    @FXML
    void rbtnDPevent(ActionEvent event) {

    }

    @FXML
    void rbtnhuisuEvent(ActionEvent event) {

    }

    @FXML
    void taInputEvent(ActionEvent event) {

    }

    //输出函数
    void output() {
        for (int i = 0; i < N; i++) {
            if (flag[i])
                tfResult.appendText(vols[i] + " ");
        }
        tfResult.appendText("\n");

    }

    void dfs(int V, int index) {
        if (V == 0) {
            output();//背包装满输出
            return;
        }
        if (V < 0 || index >= N) {
            return;//不符合条件,返回
        }
        dfs(V, index + 1);//向后递归
        //可以装进去就装
        if (V >= vols[index]) {
            flag[index] = true;//装进去为true
            dfs(V - vols[index], index + 1);
            //返回之后就拿出
            flag[index] = false;//取出来为false
        }
    }

    void recursionSolution() {
        String taText = taInput.getText();
        String tfText = tfResult.getText();

        String[] objs = taText.split(" ");
        int objsNum = objs.length;
        int[] objsInt = new int[objsNum];

        for (int i = 0; i < objs.length; i++) {
            objsInt[i] = Integer.parseInt(objs[i]);
        }
        //最大容量
        SUM = spinnerNum.getValue();
        //物体个数
        N = objsNum;
        //物体体积
        vols = objsInt;
        flag = new boolean[objsNum];
        Arrays.fill(flag, false);//标记置为false
        dfs(SUM, 0);//背包问题

    }


    void otherSolution() {
        String taText = taInput.getText();
        String tfText = tfResult.getText();

        String[] objs = taText.split(" ");
        N = objs.length;
        vols = new int[N];
        for (int i = 0; i < objs.length; i++) {
            vols[i] = Integer.parseInt(objs[i]);
        }
        SUM = spinnerNum.getValue();

        findAnswer();
        logAnswer();
    }


    /**
     * 找出件数为1的满足条件的解决办法
     */
    private static void findAnswer() {
        for (int i = 0; i < N; i++) {
            find(i, new int[]{i}, 1, vols[i]);
        }
    }

    /**
     * 从第start件开始搜索，找出件数为count的满足条件的解决办法放入到answerList集合中
     * 处理详细
     * 0、如果nums序号集合对应的物体总重量大于背包重量直接返回。（因为如果nums序号对应的物品总重量大于背包重量的话，再加其他物品的重量了肯定也大于背包重量）
     * 1、判断nums序号集合对应的物体总重量是否等于背包重量，等于的话将序号集合加入到解决办法集合中，否则执行第2步
     * 2、从i = start + 1开始搜索，看i是否在nums序号集合中，如果不在的话，执行下一步操作，否则继续下一次循环。
     * 3、从第i间开始搜索，找出件数为count+1的满足条件的解决办法。
     *
     * @param start   从vols的第start件开始搜索
     * @param nums    序号集合
     * @param count   件数
     * @param tempSum nums序号对应的物体的总重量
     */
    private static void find(int start, int[] nums, int count, int tempSum) {

        if (SUM < tempSum) return;
        if (SUM == tempSum) {
            // 在al中返回true否则返回false
            if (!isInAl(nums)) {
                answerList.add(nums);
            }
        }
        for (int i = start + 1; i < N; i++) {
            // i是否在nums中,在Nums中返回true，否则返回false
            boolean flag = isInNums(nums, i);
            if (!flag) {
                int[] numsAddOne = Arrays.copyOf(nums, count + 1);

                numsAddOne[count] = i;
                find(i, numsAddOne, count + 1, tempSum + vols[i]);
            }
        }
    }

    /**
     * nums序号集合中是否有a
     *
     * @param nums 序号集合
     * @param a    序号
     * @return 若nums序号集合中含有a返回true，否则返回false
     */
    private static boolean isInNums(int[] nums, int a) {
        for (int num : nums) {
            if (a == num) return true;
        }
        return false;
    }

    /**
     * nums序号集合是否在解决办法集合中
     * @param nums 序号集合
     * @return nums集合已经存在了返回true，否则返回false
     */
    private static boolean isInAl(int[] nums) {
        int[] tempNums = nums.clone();
        for (Object o : answerList) {
            int[] tempData = (int[]) o;
            Arrays.sort(tempData);
            Arrays.sort(tempNums);
            if (Arrays.equals(tempData, tempNums)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 输出解决办法
     */
    private void logAnswer() {
        for (Object o : answerList) {
            int[] tempData = (int[]) o;
            for (int tempDatum : tempData) {
                tfResult.appendText(vols[tempDatum] + " ");
            }
            tfResult.appendText("\n");
        }
    }
}



