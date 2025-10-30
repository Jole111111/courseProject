#include <stdio.h>
#include <stdlib.h>
void readin(int n,int *a,int *b,int*l);
//将序列读入数组的函数
int dfs(int *root,int *a,int x1,int x2,int *b,int y1,int y2,int *c,int z1,int z2,int *d,int *e,int *f,int depth);
//沿树的深度遍历整棵树，将树补完的函数
void dfs_print(int root,int mode);
//再次遍历整棵树并按顺序、前序、后序将节点打印的函数
int maxdepth,cnt,miss,flag,r[30][100],v[100],initial,len;
char c[5];
/*maxdepth:记录树的深度;
cnt:记录从未出现过的数的个数;
miss:记录缺失的数(仅限一个);
flag:记录遍历时的状态,该数是否是序列第一个;
r[30][100]:按层级与从左到右的顺序记录节点;
v[100]:记录节点的值;
initial:根节点;
c[5]:暂时记录读取的字符串;
len=0:*/
struct Tree{
    int left;
    int right;
};
//树的结构体
struct Tree *tree;
//树的结构体指针 
int main(){
    int n,i;
    initial=0;
    flag=0;
    cnt=0;
    miss=0;
    len=0;
    //先将全局变量初始化
    scanf("%d",&n);
    //读取N,传给n
    int in[n+1],pre[n+1],post[n+1],all[n+1],inp[n+1],prep[n+1],postp[n+1];
    /*创建7个数组,分别申请n+1个int空间,
    前3个分别存放顺序、前序和后序遍历节点,
    all存放1到N出现的次数,
    后三个存放顺序、前序和后序中1到N出现的位置*/
    for(i=0;i<n+1;i++){
        in[i]=0;
        pre[i]=0;
        post[i]=0;
        all[i]=0;
        inp[i]=0;
        prep[i]=0;
        postp[i]=0;
    }
    //将7个数组全部初始化
    readin(n,in,inp,all);
    readin(n,pre,prep,all);
    readin(n,post,postp,all);
    //读入顺序、前序和后序遍历的部分结果
    tree=(struct Tree *)malloc((n+1) * sizeof(struct Tree));
    //有n个节点,由于为了1到N的一一对应防止出错,申请n+1个空间
    for(int judge=1;judge<=n;judge++){
        if(all[judge]==0){
            cnt++;
            miss=judge;
        }
        //判断1到N中缺了谁且缺的是否超过一个
    }
    if(cnt>1||dfs(&initial,in,1,n,pre,1,n,post,1,n,inp,prep,postp,0)==0){
        printf("Impossible\n");
        return 0;
    }
    //当缺失数超过一个或者dfs的返回值为0,说明树不可能被唯一构建,程序结束
    else{
        if(miss) for(i=1;i<=n;i++){
            if(v[i]==0) v[i]=miss;
        }
        //将v填满缺失的数,在遍历打印时填补空缺
        for(int k=0;k<3;k++){
            flag=0;
            //每次打印完后将flag置0便于下次区分第一个数
            dfs_print(initial,k);
            printf("\n");
        }
        //依顺序不同,分别打印顺序、前序和后序的遍历结果
        for(int k=0;k<=maxdepth;k++){
            for(int num=0;num<100;num++){
                if(k==0&&num==0&&r[k][num]!=0) printf("%d",r[k][num]);
                else if(r[k][num]!=0) printf(" %d",r[k][num]);
            }
        }
        //按照深度依次增加、从左到右记录的节点打印出来
    }
    return 0;
    //程序结束
}
void readin(int n,int *a,int *b,int*l){
    //n为N,a传记录结果的数组,b传记录数的位置的数组,l传数组
    for(int i=1;i<=n;i++){
        scanf("%s",c);
        if(c[0]=='-'){
            a[i]=0;
            continue;
        }
        //c记录读进的字符串,若第一个字符是"-"就传入0并继续
        int res=0;
        //读的不是"-",就开始记录
        for (int j=0;c[j];j++){
            res=res*10+c[j]-'0';   
        }
        //将字符串转换成整型
        a[i]=res;
        //记录该数
        b[res]=i;
        //记录该数的位置
        l[res]++;
        //该数出现的次数+1
    }
}
int dfs(int *root,int *a,int x1,int x2,int *b,int y1,int y2,int *c,int z1,int z2,int *d,int *e,int *f,int depth){
    //*root传根地址,a、b、c分别传顺序、前序和后序结果,x1、x2,y1、y2,z1、z2分别记录其起止位置,d、e、f传记录位置的数组,depth传深度
    if(maxdepth<depth) maxdepth=depth;
    //更新最深处深度
    if(x1>x2){
        *root=0;
        return 1;
    }
    //x1超过x2时,说明该枝已经遍历结束,此处根记录为0,返回1表示成功
    for(int i=x1;i<=x2;i++){
        if((a[i]!=b[y1]&&a[i]&&b[y1])||(a[i]!=c[z2]&&a[i]&&c[z2])||(b[y1]!=c[z2]&&b[y1]&&c[z2])) continue;
        //如果在此处continue,说明未找到正确的根的位置或所给数据错误
        v[i]=a[i]>(b[y1]>c[z2]?b[y1]:c[z2])?a[i]:(b[y1]>c[z2]?b[y1]:c[z2]);
        //记录节点,找到其中最大值以避免取0,当然,在有数缺失的情况下,取0不可避免
        int temp_in = d[v[i]];
        int temp_pre = e[v[i]];
        int temp_post = f[v[i]];
        //找到这个数在三个数组中的位置
        if((temp_in!=i&&temp_in)||(temp_pre!=y1&&temp_pre)||(temp_post!=z2&&temp_post)) continue;
        //如果这三个位置只要有一个不在本该在的位置上均continue
        *root=i;
        //根的位置转变成i
        if(miss!=0&&v[i]==0) r[depth][len++]=miss;
        r[depth][len++]=v[i];
        //按照深度依次增加、从左到右记录的节点
        if(dfs(&((tree[*root]).left),a,x1,i-1,b,y1+1,y1+i-x1,c,z1,z1+i-x1-1,d,e,f,depth+1)==0) continue;
        if(dfs(&((tree[*root]).right),a,i+1,x2,b,y1+1+i-x1,y2,c,z1+i-x1,z2-1,d,e,f,depth+1)==0) continue;
        //向左向右递归遍历子树
        return 1;
        //全部成功的话返回1
    }
    return 0;
    //说明遍历不成功,返回0
}
//比较大小,返回较大值
void dfs_print(int root,int mode){
    //传入根,根据条件不同进行不同的遍历顺序
    if(root==0) return;
    //root为0时结束递归
    if(mode==1){
        if(flag==0){
            printf("%d",v[root]);
            flag=1;
        }
        else printf(" %d",v[root]);
    }
    dfs_print((tree[root]).left,mode);
    if(mode==0){
        if(flag==0){
            printf("%d",v[root]);
            flag=1;
        }
        else printf(" %d",v[root]);
    }
    dfs_print((tree[root]).right,mode);
    if(mode==2){
        if(flag==0){
            printf("%d",v[root]);
            flag = 1;
        }
        else printf(" %d",v[root]);
    }
    /*mode为0时先向左递归,然后打印根,再向右递归,打印出的是顺序遍历的结果
    mode为1时先打印根,然后向左递归,再向右递归,打印出的是前序遍历的结果
    mode为2时先向左递归,然后向右递归,再打印根,打印出的是后序遍历的结果*/
}