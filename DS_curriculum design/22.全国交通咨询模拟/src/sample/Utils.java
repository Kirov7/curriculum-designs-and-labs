package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2021-12-31 3:39
 */

//工具类
public class Utils {

    static String defaultURL = "C:\\Users\\25222\\Desktop\\列车文件\\TrainList.txt";

    public static boolean setURL(String newURL) {
        defaultURL = newURL;
        return new File(newURL).exists();
    }

    //获取全排列的所有可能
    public static ArrayList<String> getPermulation(int[] arr) {
        ArrayList<String> al = new ArrayList<>();
        do {
            String str = null;
            for (int i = 0; i < arr.length; i++) {
                if (i == 0)
                    str = arr[i] + ",";
                else
                    str += arr[i] + ",";
            }
            al.add(str);
        } while (new Permulation().nextPermutation(arr));
        return al;
    }


    //获取对应的BufferedReader
    public static BufferedReader getBr() throws FileNotFoundException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(defaultURL));
        return br;
    }

    //添加指定信息到指定文件中
    public static boolean addInfo(String info) throws IOException {
        boolean flag = false;
        String[] date = info.split("-->");
        if (date.length != 6)
            return false;        //输入不规范
        else {
            BufferedWriter bw = null;

            bw = new BufferedWriter(new FileWriter(defaultURL, true));
            bw.append(info + "\r\n");
            bw.flush();
            flag = true;
            bw.close();

        }

        return flag;
    }

    //删除文件中的信息,起始站名,终点站名
    public static boolean deleteInfo(String start, String end) throws IOException {
        boolean flag = false;
        BufferedReader br = getBr();
        StringBuilder sb = new StringBuilder();    //将读取进来的数据存放在这里面
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.contains("TEMP"))
                continue;
            String[] info = new String[6];
            info = line.split("-->");
            if (!(info[1].equals(start) && info[2].equals(end))) {  //不存在
                sb.append(line + "\r\n");
                flag = true;
            }else
                flag = true;
        }
        FileWriter fw = null;

        fw = new FileWriter(defaultURL);

        fw.write("");    //清空？
        fw.append(sb.toString());
        fw.close();
        br.close();

        return flag;
    }

    public static boolean deleteCity(String city) throws IOException {
        boolean flag = false;
        BufferedReader br = getBr();
        StringBuilder sb = new StringBuilder();    //将读取进来的数据存放在这里面
        String line = null;
        while ((line = br.readLine()) != null) {
            if (line.contains("TEMP"))
                continue;
            String[] info = new String[6];
            info = line.split("-->");
            if (!(info[1].equals(city))) {   //不存在
                sb.append(line + "\r\n");
                flag = false;
            }else
                flag = false;
        }
        FileWriter fw = null;

        fw = new FileWriter(defaultURL);

        fw.write("");    //清空
        fw.append(sb.toString());
        fw.close();
        br.close();

        return flag;
    }

    //将分钟（整形）转化为x小时x分钟的字符串形式
    public static String transformTime(int costTime) {
        int hour, minute;
        hour = costTime / 60;
        minute = costTime % 60;
        if (hour == 0)
            return minute + "分钟";
        if (minute == 0)
            return hour + "小时";
        return hour + "小时" + minute + "分钟";
    }

    //获取路径的具体信息，从文件中读取挑选并存入StringBuilder中，并返回StringBuilder
    public static StringBuilder getPathInfo(String path, String bestChoice,
                                            int costInfo) throws IOException {
        StringBuilder sb = new StringBuilder();
        String[] station = path.split("-->");
        BufferedReader br = null;
        String line = null;

        for (int i = 0; i < station.length - 1; i++) {
            br = new BufferedReader(new FileReader(defaultURL));

            while ((line = br.readLine()) != null) {
                if (line.contains("TEMP"))
                    continue;
                String[] info = line.split("-->");
                if (info[1].equals(station[i]) && info[2].equals(station[i + 1])) {

                    sb.append(line);
                    sb.append("\r\n");
                }

            }

        }
        br.close();
//		System.out.println("costInfo:"+costInfo);
        if (bestChoice.equals("最快到达"))
            sb.append(bestChoice + "的数据总计为：" + transformTime(costInfo));
        else
            sb.append(bestChoice + "的数据总计为:" + costInfo + "元");

        return sb;
    }

    //从指定文件中读取数据，生成一个MGraph对象并返回
    public static MGraph readInfo() throws NumberFormatException, IOException {
        ArrayList<Station> al = new ArrayList<>();
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(defaultURL));
        //读取文件中的火车列表数据
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("TEMP"))
                continue;
            String[] info = line.split("-->");
            int price = Integer.parseInt(info[5]);
            al.add(new Station(info[0], info[1], info[2], info[3], info[4], price));

        }
        br.close();        //关闭流
        MGraph mg = new MGraph(al);
//		System.out.println("stationName长度："+mg.stationName.size());
        Floyd tf = new Floyd(mg);
        return mg;
    }


    //完成路径
    public static void FinishPath(MGraph mg) {
        //最省钱
        for (int i = 0; i < mg.n; i++)
            for (int j = 0; j < mg.n; j++) {
                if (mg.DistancMatrix[i][j] != 65535)    //i可以到达j
                {
                    String from = mg.stationName.get(i);
                    String to = mg.stationName.get(j);
                    mg.path[i][j] = from;
                    if (mg.DistancMatrix[i][j] == mg.priceArcs[i][j])    //直达，不必经过其他顶点
                    {
                        mg.path[i][j] += "-->" + to;
                    } else {
                        int[] changNode = new int[10];        //考虑最多中间有两个节点
                        int index = 0;
                        for (int u = 0; u < mg.n; u++) {
                            if (mg.PathMatrix[i][j][u] == true && u != i && u != j) {
                                changNode[index++] = u;
                            }
                        }
                        if (index - 1 == 0)    //只有一个顶点
                        {
                            String change = mg.stationName.get(changNode[0]);
                            mg.path[i][j] += "-->" + change + "-->" + to;
                        } else {        //大于两个顶点
                            ArrayList<String> al = getPermulation(changNode);
                            Iterator it = al.iterator();
                            int[] right = new int[index];    //正确的顺序
                            while (it.hasNext()) {
                                String str = (String) it.next();
                                String[] array = str.split(",");
                                int[] arr = new int[index];
                                for (int k = 0; k < index; k++)
                                    arr[k] = Integer.parseInt(array[k]);
                                int cost = 0;
                                //算出头尾
                                cost = mg.priceArcs[i][arr[0]] + mg.priceArcs[arr[index - 1]][j];
                                for (int k = 0; k < index - 1; k++) {
                                    if (mg.priceArcs[arr[k]][arr[k + 1]] == 65535)    //不经过
                                        break;
                                    else
                                        cost += mg.priceArcs[arr[k]][arr[k + 1]];
                                }
                                if (cost == mg.DistancMatrix[i][j])    //正确序列
                                {
                                    for (int z = 0; z < index; z++) {
                                        right[z] = arr[z];
                                    }
                                    break;    //跳出while
                                }
                            }
                            for (int k = 0; k < index; k++) {
                                mg.path[i][j] += "-->" + mg.stationName.get(right[k]);
                            }
                            mg.path[i][j] += "-->" + to;

                        }


                    }

                }
            }

        //最快捷
        for (int i = 0; i < mg.n; i++)
            for (int j = 0; j < mg.n; j++) {
                if (mg.timeMatrix[i][j] != 65535)    //i可以到达j
                {
                    String from = mg.stationName.get(i);
                    String to = mg.stationName.get(j);
                    mg.timePath[i][j] = from;
                    if (mg.timeMatrix[i][j] == mg.timeArcs[i][j])    //直达，不必经过其他顶点
                    {
                        mg.timePath[i][j] += "-->" + to;
                    } else {
                        int[] changNode = new int[10];        //考虑最多中间有两个节点
                        int index = 0;
                        for (int u = 0; u < mg.n; u++) {
                            if (mg.TimePathMatrix[i][j][u] == true && u != i && u != j) {
                                changNode[index++] = u;
                            }
                        }
                        if (index - 1 == 0)    //只有一个顶点
                        {
                            String change = mg.stationName.get(changNode[0]);
                            mg.timePath[i][j] += "-->" + change + "-->" + to;
                        } else {
                            ArrayList<String> al = getPermulation(changNode);
                            Iterator it = al.iterator();
                            int[] right = new int[index];    //正确的顺序
                            while (it.hasNext()) {
                                String str = (String) it.next();
                                String[] array = str.split(",");
                                int[] arr = new int[index];
                                for (int k = 0; k < index; k++)
                                    arr[k] = Integer.parseInt(array[k]);
                                if (mg.timeArcs[i][arr[0]] != 65535 && mg.timeArcs[arr[index - 1]][j] != 65535) {    //头尾都可以到达
                                    int cost = 0;
                                    //添加头尾及相应的等待时间
                                    cost += mg.timeArcs[i][arr[0]] + Utils.addWaitTime(mg, i, arr[0], arr[1]);
                                    cost += mg.timeArcs[arr[index - 1]][j];
                                    for (int k = 0; k < index - 1; k++) {
                                        if (mg.timeArcs[arr[k]][arr[k + 1]] == 65535)    //不经过
                                            break;
                                        else {
                                            if (k != index - 2)
                                                cost += mg.timeArcs[arr[k]][arr[k + 1]];
                                            else
                                                cost += mg.timeArcs[arr[k]][arr[k + 1]];

                                        }
                                    }
                                    if (cost == mg.timeMatrix[i][j])    //正确序列
                                    {
                                        for (int z = 0; z < index; z++) {
                                            right[z] = arr[z];
                                        }
                                        break;    //跳出while
                                    }
                                }
                            }

                            for (int k = 0; k < index; k++) {
                                mg.timePath[i][j] += "-->" + mg.stationName.get(right[k]);
                            }
                            mg.timePath[i][j] += "-->" + to;

                        }


                    }

                }
            }
    }

    //添加中转站的等待时间
    public static int addWaitTime(MGraph mg, int v, int u, int w)    //添加v-->u u-->w之间的等待时间到timeMatrix[v][w]
    {
        int waitTime = 0;
        String time1 = null, time2 = null;        //u站的到达时间和出发时间
        String shifa, zhongzhuan, zhongdian;    //始发站，中转站，终点站的名称
        shifa = mg.stationName.get(v);
        zhongzhuan = mg.stationName.get(u);
        zhongdian = mg.stationName.get(w);
        for (Station ts : mg.al) {
            if (ts.from.equals(shifa) && ts.to.equals(zhongzhuan))
                time1 = ts.arriveTime;
            if (ts.from.equals(zhongzhuan) && ts.to.equals(zhongdian))
                time2 = ts.startTime;
        }
        if (time1 != null && time2 != null)
            waitTime = Utils.getCostTime(time1, time2);
//		System.out.println(shifa+"-->"+zhongzhuan+"-->"+zhongdian+"waitTime:"+waitTime);
        return waitTime;
    }

    //传入两个字符类型的时间，计算出两个时间的差值，date2-date1，返回的数值以分钟为单位
    public static int getCostTime(String date1, String date2) {
        int costHour = 0, costMinute = 0;
        String time1[] = date1.split(":");
        String time2[] = date2.split(":");

        int hour1 = Integer.parseInt(time1[0]);
        int hour2 = Integer.parseInt(time2[0]);

        int minute1 = Integer.parseInt(time1[1]);
        int minute2 = Integer.parseInt(time2[1]);

        if (hour2 > hour1) {
            costHour = hour2 - hour1;
            if (minute2 >= minute1) {
                costMinute = minute2 - minute1;
            } else if (minute2 < minute1) {
                costMinute = 60 - minute1 + minute2;
                costHour--;
            } else {

            }
        } else {
            costHour = 24 - hour1 + hour2;
            if (minute2 > minute1) {
                costMinute = minute2 - minute1;
            } else if (minute2 < minute1) {
                costMinute = 60 - minute1 + minute2;
                costHour--;
            } else {

            }
        }
        return costHour * 60 + costMinute;
    }
}
