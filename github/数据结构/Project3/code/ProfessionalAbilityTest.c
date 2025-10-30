#include<stdio.h>
#include<stdlib.h>
#define SIZE 1000
//定义SIZE（N的最大值）为1000
#define INFINITY 1000000
//将分数的无穷定义为1000000
typedef struct{
    int arr[SIZE];
    int front;
    int rear;
    int counter;
}Queue;
//队列结构的定义
typedef struct{
    int flag;
    int s;
    int d;
}Node;
//邻接表结构的定义
//flag表示节点是否邻接
//s表示邻接节点的分数
//d表示邻接节点的代金券额度
typedef struct{
    int known;
    int s;
    int d;
    int path;
}Table;
//各测试点结构的定义
//known表示该测试点是否被读取过
//s表示测试点离源点的总分数
//d表示测试点离源点的总代金券额度
//path表示测试点最优路径的上一个测试点
int n,m;
//分别表示测试总数、给出的关系个数
int *in;
//in[]用来计数各测试点的入度
Table T[SIZE+1];
//声明变量T[]，类型为Table，容量为SIZE+1
Node G[SIZE+1][SIZE+1];
//声明变量G[][]，类型为Node，用来存储邻接图
/*T[SIZE]、G[SIZE][SIZE]这两个点用来存放一个虚拟的源点
连接向所有入度为零的测试点，使源点唯一以便进行Dijkstra算法*/
int Topsort();
//进行拓扑排序以判定图是否有环的函数
void Dijkstra();
//进行Dijkstra算法规划出最优路径的函数
void Trace(int t);
//进行path回溯，打印路径的函数
int IsEmpty(Queue *q);
int DeQueue(Queue *q);
void EnQueue(Queue *q, int val);
void Init(Queue *q);
//以上几个是队列操作函数
int main(){
    int t1,t2,s,d;
    //声明变量，分别暂时储存一行给出的关系
    scanf("%d %d",&n,&m);
    //先读入两个数储存为n和m
    in=(int *)malloc(n*sizeof(int));
    //为in[]申请n的空间储存这n个测试点的入度
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            G[i][j].d=-1;
            G[i][j].s=INFINITY;
            G[i][j].flag=0;
        }
        in[i]=0;
    }
    //初始化G和in中所需要用到的点
    for(int i=0;i<SIZE;i++){
        G[SIZE][i].flag=0;
        G[SIZE][i].d=-1;
        G[SIZE][i].s=INFINITY;
        T[i].known=0;
        T[i].s=INFINITY;
        T[i].d=-1;
        T[i].path=-1;
    }
    //初始化虚拟源点和其他测试点的图，初始化测试点的信息
    for(int i=0;i<m;i++){
        scanf("%d %d %d %d",&t1,&t2,&s,&d);
        G[t1][t2].flag=1;
        G[t1][t2].s=s;
        G[t1][t2].d=d;
        in[t2]++;
    }
    //读入题中给出的edge信息，每读到一个t2，t2的入度加一
    for(int i=0;i<n;i++){
        if(in[i]==0){
            G[SIZE][i].flag=1;
            G[SIZE][i].d=0;
            G[SIZE][i].s=0;
            in[i]=1;
        }
    }
    /*读完edge关系后，将虚拟源点SIZE连接向所有入度为零
    的测试点，图上flag置1，d和s置0，将测试点的入度置1*/
    int c=Topsort();
    //进行拓扑排序判断图中是否有环，无的话返回1，否则返回0
    int k;
    //k表示将要寻找路径的测试点个数
    scanf("%d",&k);
    //读入k
    int a[k];
    for(int i=0;i<k;i++){
        scanf("%d",&a[i]);  
    }
    //用a[k]来储存读入的待测测试点
    if(c){
        //图中无环的情况
        Dijkstra();
        //进行Dijkstra算法规划出最优路径   
        printf("Okay.\n");
        //按题意输出
        for(int i=0;i<k;i++){
            Trace(a[i]);
        }
        //对每个测试点回溯并打印路径
    }
    else{
        //图中有环的情况
        printf("Impossible.\n");
        //按题意输出
        for(int i=0;i<k;i++){
            int flag=1;
            for(int j=0;j<n;j++){
                if(G[j][a[i]].flag==1){
                    printf("Error.\n");
                    flag=0;
                    break;
                }
            }
            if(flag==1) printf("You may take test %d directly.\n",a[i]);
        }
        /*对于每个测试点，如果其入度为零则打印
        "You may take test %d directly.\n",a[i]，
        否则打印"Error."*/
    }
    return 0;
    //主函数结束
}
int Topsort(){
    Queue *q;
    Init(q);
    int counter=0;
    EnQueue(q,SIZE);
    //先让虚拟源点入队
    int v;
    while(!IsEmpty(q)){
        v=DeQueue(q);
        counter++;
        //计数，不再看到这个节点
        for(int i=0;i<n;i++){
            if(G[v][i].flag==1){
                if(--in[i]==0) EnQueue(q,i);
                //每遇到联通路径，i的入度就减一
                //若入度减一后为0，入队
            }
        }
    }
    if(counter!=n+1) return 0;
    else return 1;
    //若最终的统计结果为n+1，即为虚拟源点加测试点的总数，则图无环，返回1，否则返回0
}
void Dijkstra(){
    T[SIZE].s=0;
    T[SIZE].d=0;
    T[SIZE].known=0;
    //初始化T[SIZE]以便循环的进行
    int min,max,k;
    //min表示当前所遇见的最低所需分数
    //max表示当前所需分数相同时可获得的最高代金券
    //k储存所寻找到当前节点的最优路径上的下一节点
    for(int i=0;i<=n;i++){
        //总共有n+1个节点，故循环n+1次
        min=INFINITY;
        max=-1;
        //先将min和max初始化为最差情况
        for(int j=SIZE;j<n||j==SIZE;j++){
            //从虚拟源点开始寻找下一最优路径
            if(T[j].known==0&&T[j].s<min){
                min=T[j].s;
                max=T[j].d;
                k=j;
            }
            //如果j这个节点没有被读取过，且其所需分数比当前的min还要小，就选它
            //此时需要同时改变min、max的值以和路径匹配，用k记录这个节点
            else if(T[j].known==0&&T[j].s==min&&T[j].d>max){
                max=T[j].d;
                k=j;
            }
            //如果j这个节点没有被读取过，且其所需分数和当前的min一样，但可得代金券更高，就选它
            //此时仅需改变max的值以和路径匹配，用k记录这个节点
            if(j==SIZE) j=-1;
            //第一次过后就从0开始循环
            if(j==SIZE-1) break;
            //特殊情况，此时n==SIZE。当读到SIZE-1的时候说明已经遍历了所有节点，为了防止循环采用break
        }
        T[k].known=1;
        //被找到的最优路径上的下一节点标记为已读
        for(int j=0;j<n;j++){
            //刷新所有未读测试点的最优路径
            if(G[k][j].flag==1&&T[j].known==0&&T[j].s>T[k].s+G[k][j].s){
                T[j].s=T[k].s+G[k][j].s;
                T[j].d=T[k].d+G[k][j].d;
                T[j].path=k;
            }
            /*如果k和j存在edge，且j没有被读取过，且j当前所需总分高于k所需总分加k到j所需分数，则使j当前所需
            总分变为k所需总分加k到j所需分数，j所得代金券总额变为k所得总额加k到j所得代金券额度，标记j的前置路径为k*/
            else if(G[k][j].flag==1&&T[j].known==0&&T[j].s==T[k].s+G[k][j].s&&T[j].d<T[k].d+G[k][j].d){
                T[j].d=T[k].d+G[k][j].d;
                T[j].path=k;
            }
            /*如果k和j存在edge，且j没有被读取过，且j当前所需总分等于k所需总分加k到j所需分数，但j所得代金券总额
            小于k所得总额加k到j所得代金券额度，则使j所得代金券总额变为k所得总额加k到j所得代金券额度，标记j的前置路径为k*/
        }
    }
    return;
    //函数结束
}
void Trace(int t){
    int temp=t;
    int i=1;
    int p[n];
    //用来储存路径
    p[0]=t;
    while(t!=SIZE){
        p[i]=T[t].path;
        t=T[t].path;
        i++;
    }
    //在没遇到虚拟源点前，沿path一路向上搜索并储存
    if(i==2){
        //仅循环一次就遇到虚拟源点，说明该测试点原入度为0，按题意打印
        printf("You may take test %d directly.\n",temp);
        return;
    }
    else{
        //否则按题目条件逆序打印数组即可
        for(i=i-2;i>0;i--){
            printf("%d->",p[i]);
        }
        printf("%d\n",p[0]);
        return;
    }
}
//以下为队列操作函数，不赘述
void Init(Queue *q){
    q->front=0;
    q->rear=-1;
    q->counter=0;
    return;
} 
void EnQueue(Queue *q, int val){
    q->rear=(q->rear+1)%SIZE;
    q->arr[q->rear]=val;
    q->counter++;
}
int IsEmpty(Queue *q){
    return (q->counter<=0);
}
int DeQueue(Queue *q){
    int c=q->arr[q->front];
    q->front=(q->front+1)%SIZE;
    q->counter--;
    return c;
}