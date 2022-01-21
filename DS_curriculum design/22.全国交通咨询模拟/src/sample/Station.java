package sample;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2021-12-31 3:39
 */


//有向边
public class Station {
    String num;        //车次
    String from;    //始发站
    String to;        //终点站
    String startTime;    //出发时间
    String arriveTime;    //到站时间
    int price;            //票价
    int costTime;        //走完这条边所需的时间（分钟）

    //构造器
    public Station(String num, String from, String to, String startTime,
                   String arriveTime, int price) {
        super();
        this.num = num;
        this.from = from;
        this.to = to;
        this.startTime = startTime;
        this.arriveTime = arriveTime;
        this.price = price;
        this.costTime = Utils.getCostTime(startTime, arriveTime);

    }

    public String toString() {
        return num + "	" + from + "	" + to + "	" + startTime + "	" + arriveTime + "	" + price;
    }

}
