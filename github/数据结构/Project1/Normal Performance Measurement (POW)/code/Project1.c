#include <stdio.h>
#include <time.h>

double Algorithm1(double x,int n);						    
//算法一的函数
double Algorithm2(double x,int n);                    		
//算法二的递归形式函数 
double Algorithm3(double x,int n);         				    
//算法二的迭代形式函数  

int main()
{   
	clock_t start,stop;
	double x=1.0001,ans1,ans2,ans3,t;                 			    
	//ans1,ans2,ans_3分别为三个函数计算出来的结果
	int k,n;                                                  
	//设定k为重复运行的次数,n为指数 
	printf ("输入循环次数k和指数N\n");                              
	scanf("%d%d",&k,&n);                                   
	//输入底数 x 和指数 n  
    
	start=clock();                                          
	//开始计时    
	for(int i=0;i<k;i++){                                   
		//重复运行函数k次
		ans1= Algorithm1(x,n);								
		//运行算法一
	}                   
	stop=clock();                                            
	//结束计时 
    t=((double)(stop-start))/CLK_TCK;              			
	//重复k次所花费的的总时间
	printf("算法一:\n计算结果为%lf\n用时%lfs\n",ans1,t);                 
	//输出结果和总运行时间 

	start=clock();                                          
	//开始计时 
	for(int i=0;i<k;i++){                                   
		//重复运行函数k次
		ans2=Algorithm2(x,n);								
		//运行算法二递归形式
	}           
	stop=clock();                                           
	//停止计时 
    t=((double)(stop-start))/CLK_TCK;
	printf("递归算法二:\n计算结果为%lf\n用时%lfs\n",ans2,t);                 		     
	//输出结果和总运行时间  
	
	start=clock();                                          
	//开始计时 
	for(int i=0;i<k;i++){                                   
		//重复运行函数k次
		ans3=Algorithm3(x,n);								
		//运行算法二迭代形式
	}
	stop=clock();                                           
	//停止计时 
    t=((double)(stop-start))/CLK_TCK;
	printf("迭代算法二:\n计算结果为%lf\n用时%lfs\n",ans3,t);                       			
	//输出总运行时间 
	return 0;
}

double Algorithm1(double x,int n){
    double m=1;
	for (int i=0;i<n;i++){                                 
		//循环n次相乘得出乘积
		m=m*x;
	}			 
    return m;
} 

double Algorithm2(double x,int n){
    if (n==0) return 1;
    if (n==1) return x;
	if (n % 2 == 0)                                        
	//如果n是偶数，则返回Algorithm2(x*x,n/2);
		return Algorithm2(x*x,n/2);
	else                                                   
	//如果n是奇数，则返回Algorithm2(x*x,n/2)*x;
		return Algorithm2(x*x,n/2)*x;
}

double Algorithm3(double x,int n){
	int p[20],m;											
	//先定义数组p存放每次n除2的余数
	double y=1;												
	//初始化返回值
	for(m=0;m<20;m++){										
		//初始化数组p
		p[m]=0;
    }
	m=0;
	while(n>0){												
		//每次将n除二取余将n分解
		p[m]=n%2;
		m++;
		n=n/2;
	}
	while(--m>=0){											
		//按照n的余数进行相应迭代操作
		if(p[m]==1) y=y*y*x;
		else if(p[m]==0) y=y*y;
	}
	return y;
}               
