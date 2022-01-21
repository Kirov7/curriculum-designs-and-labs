package sample;



//弗洛伊德算法
public class Floyd {
	public MGraph mg;

	public Floyd(MGraph mg){
		this.mg=mg;
		ShortestPath_FLOYD(mg);	//创建对象时即调用弗洛伊德算法
		Utils.FinishPath(mg);	//完善路径信息
	}

	public void ShortestPath_FLOYD(MGraph g){
		for(int v=0;v<g.n;v++)
			for(int w=0;w<g.n;w++)
			{
				g.DistancMatrix[v][w]=g.priceArcs[v][w];
				g.timeMatrix[v][w]=g.timeArcs[v][w];
				if(g.DistancMatrix[v][w]<65535)
				{
					g.PathMatrix[v][w][v]=true;
					g.PathMatrix[v][w][w]=true;
				}
				if(g.timeMatrix[v][w]<65535)
				{
					g.TimePathMatrix[v][w][v]=true;
					g.TimePathMatrix[v][w][w]=true;

				}
			}

		//最快捷
		for(int u=0;u<g.n;u++)
			for(int v=0;v<g.n;v++)
				for(int w=0;w<g.n;w++)
					//从v经u到w的一条路径更短
					if(g.timeMatrix[v][u]+g.timeMatrix[u][w]+Utils.addWaitTime(g, v, u, w)<g.timeMatrix[v][w])
					{
						g.timeMatrix[v][w]=g.timeMatrix[v][u]+g.timeMatrix[u][w]+Utils.addWaitTime(g, v, u, w);

						for(int i=0;i<g.n;i++)
							g.TimePathMatrix[v][w][i]=g.TimePathMatrix[v][u][i]||g.TimePathMatrix[u][w][i];
					}

//		打印一开始DistancMatrix或timeMatrix的值
//		for(int i=0;i<g.n;i++){
//			for(int j=0;j<g.n;j++)
//				System.out.print(g.timeMatrix[i][j]+"\t");
//			System.out.println();
//		}

		//最省钱
		for(int u=0;u<g.n;u++)
			for(int v=0;v<g.n;v++)
				for(int w=0;w<g.n;w++)
					//从v经u到w的一条路径更短
					if(g.DistancMatrix[v][u]+g.DistancMatrix[u][w]<g.DistancMatrix[v][w])
					{
						g.DistancMatrix[v][w]=g.DistancMatrix[v][u]+g.DistancMatrix[u][w];

						for(int i=0;i<g.n;i++)
							g.PathMatrix[v][w][i]=g.PathMatrix[v][u][i]||g.PathMatrix[u][w][i];
					}

		for(int i=0;i<g.n;i++){
			//将对角线的值全部设为65535
			g.DistancMatrix[i][i]=65535;
			g.timeMatrix[i][i]=65535;
		}

	}

}
