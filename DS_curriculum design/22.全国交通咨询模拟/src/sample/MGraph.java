package sample;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2021-12-31 3:32
 */

public class MGraph {
    public ArrayList<String> stationName;    //用于存储顶点（火车站名）
    public ArrayList<Station> al;
    public int n;                    //顶点数
    public int[][] priceArcs;        //用于存储边的权值（价格）
    public int[][] timeArcs;        //用于存储边的权值（分钟）
    public String[][] path;            //最省钱的路径
    public String[][] timePath;        //最快捷的路径
    public boolean[][][] PathMatrix;    //价格路径矩阵
    public boolean[][][] TimePathMatrix;    //时间路径矩阵
    public int[][] DistancMatrix;    //用于存储从某点到某点所用的最小费用
    public int[][] timeMatrix;        //用于存储从某点到某点的所用的最少时间

    public MGraph(ArrayList<Station> al)    //构造器
    {
        this.al = al;
        stationName = new ArrayList<>();
        ListIterator<Station> li = al.listIterator();
        while (li.hasNext()) {
            Station ts = li.next();
            if (!stationName.contains(ts.from))
                stationName.add(ts.from);
            if (!stationName.contains(ts.to))
                stationName.add(ts.to);
        }
        //初始化顶点数
        n = stationName.size();
        //初始化个数组
        priceArcs = new int[n][n];
        path = new String[n][n];
        timePath = new String[n][n];
        PathMatrix = new boolean[n][n][n];
        TimePathMatrix = new boolean[n][n][n];
        DistancMatrix = new int[n][n];
        timeArcs = new int[n][n];
        timeMatrix = new int[n][n];
        //为指定数组赋初始值
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                timeArcs[i][j] = 65535;
                priceArcs[i][j] = 65535;
            }
        }

        li = al.listIterator();
        while (li.hasNext()) {
            Station ts = li.next();
            priceArcs[stationName.indexOf(ts.from)][stationName.indexOf(ts.to)] = ts.price;
            timeArcs[stationName.indexOf(ts.from)][stationName.indexOf(ts.to)] = ts.costTime;
        }

    }
}
