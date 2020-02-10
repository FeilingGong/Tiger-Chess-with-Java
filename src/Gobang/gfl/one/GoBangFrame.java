package Gobang.gfl.one;

import java.awt.BorderLayout; //边界式布局
import java.awt.FlowLayout;//流式布局
import java.awt.Font;//字体
import java.awt.Color;//颜色
import java.awt.Dimension;

import java.awt.Graphics; //画图

import java.awt.Image;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat; //日期格式
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame; //顶级容器类
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; //面板容器类
import javax.swing.JTextField;
import javax.swing.Timer; //时间组件



/*相当于JFrame是一个大餐桌，JPanel是盘子，如果我们要对餐桌JFrame上的
 * 东西进行管理分类等，我们就需要使用这些JPanel盘子
 */


public class GoBangFrame extends JPanel implements GoBangInterface,Runnable
{
    Thread t = new Thread(this);//线程

	public Graphics g;//定义一支画笔
	public int[][] visit=new int[7][5];////二维数组储存棋盘的落子情况，空为0，狗为1，虎为2，选中狗为3，选中虎为4
	public String[] butname= {"开始游戏","开始新游戏","悔棋","认输"};
	public JButton[] button=new JButton[butname.length];
	public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();//保存每一步的落子情况
	public int turn=1;//1为狗 2为虎
	public int pitchup = 0 ;//0为未选中，1为选中
	
	public Timer time;
	public JLabel timelabel;//日期相关
	
	
	public int tempi,tempj ;//用于存放上一个坐标
	
	public int number_of_dog=16; //狗的数量
	int tiger_x=4;//老虎的坐标
	int tiger_y=2;

	public int end=0;//游戏结束标志
	
	public int tiger_time_left=300; //游戏时间
	public int dog_time_left=300;
	
	public String tigermessage="无限制";
	public String dogmessage="无限制";
	
	JTextField tx=new JTextField();//提示框
	
	Image tiger = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Images/老虎.png"));
	Image dog = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Images/狗子.png"));
	//Image tiger=Toolkit.getDefaultToolkit().getImage("images/老虎.png");
	
	
	//Image dog=Toolkit.getDefaultToolkit().getImage("images/狗子.png");
	
	
	public GoBangFrame() {

        this.repaint();
        // 添加鼠标监视器
        //addMouseListener((MouseListener) this);
        // addMouseMotionListener((MouseMotionListener) this);
        // this.requestFocus();
        t.start();
        t.suspend();// 线程挂起


    }
	
	public static void main(String[] args)//----------------------------------------------主方法入口
	{
		
		GoBangFrame gf=new GoBangFrame();
		gf.initUI();
		gf.BoundListener();
	}
	
	
	public void initUI()//初始化一个界面,并设置标题大小等属性
	{
		
		JFrame jf=new JFrame();
		jf.setTitle("捕虎棋游戏");
		jf.setSize(1000, 900);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.setLayout(new BorderLayout());//设置顶级容器JFrame为边界式布局
		jf.setVisible(true);//设置窗体的显示状态而非隐藏
		
		Dimension dim1=new Dimension(150,0);//右半部分大小
		Dimension dim2=new Dimension(550,0);//左半部分大小
		Dimension dim3=new Dimension(140,40); //设置按钮组件大小
		
		//实现左边的界面，把GoBangframe的对象添加到框架布局的中间部分
		this.setPreferredSize(dim2);//设置下棋界面的大小
		this.setBackground(Color.orange);//设置下棋界面的颜色
		jf.add(this, BorderLayout.CENTER);//添加到框架布局的中间部分
		

		//实现右边的JPanel容器界面
		JPanel jp=new JPanel();
		jp.setPreferredSize(dim1);
		jp.setBackground(Color.LIGHT_GRAY);
		jp.setLayout(new FlowLayout());
		
		//接下来我们需要把按钮等组件依次加到那个JPanel上面
		//设置按钮数组
		
		
		for(int i=0;i<butname.length;i++)
		{
			button[i]=new JButton(butname[i]);
			button[i].setPreferredSize(dim3);
			jp.add(button[i]);
		}
		
		
		
		tx.setPreferredSize(dim3);
		jp.add(tx);
		
		jp.add(this.getTimelabel());
		
		jf.add(jp, BorderLayout.EAST);
		
		
		//jf.setResizable(false); //不可改变大小
		
		//初始化所有棋子落点
		visit[2][0]=1;
		visit[2][1]=1;
		visit[2][2]=1;
		visit[2][3]=1;
		visit[2][4]=1;
		visit[6][0]=1;
		visit[6][1]=1;
		visit[6][2]=1;
		visit[6][3]=1;
		visit[6][4]=1;
		visit[3][0]=1;
		visit[4][0]=1;
		visit[5][0]=1;
		visit[3][4]=1;
		visit[4][4]=1;
		visit[5][4]=1;
		visit[4][2]=2;
	}
	
	public void BoundListener()//绑定按钮监听器
	{
		ButtonListener ButListener=new ButtonListener(this);//ButtonListener实现了ActionListener，成为一个监听器
		for(int i=0;i<butname.length;i++)
		{
			button[i].addActionListener(ButListener);//为每个按钮添加监听器，监听器中包含了事件处理方法
		}
	}

	public void paint(Graphics g) //画出棋盘
	{
		super.paint(g);
		
		g.setColor(Color.red);
        //画出直线
		for(int i=0;i<row-2;i++) 
		{
			g.drawLine(x, y+boardsize*i+2*boardsize, x+boardsize*(column-1), y+boardsize*i+2*boardsize);//横线
		}
		
		for(int j=0;j<column;j++) 
		 
		{
			g.drawLine(x+boardsize*j, y+2*boardsize, x+boardsize*j, y+boardsize*(row-1));//竖线
		}
		
		//画出斜线
        //菱形的完善
           g.drawLine(x+boardsize,y+boardsize, x+boardsize*2, y+boardsize*2);
           g.drawLine(x+boardsize,y+boardsize, x+2*boardsize, y);
           g.drawLine(x+3*boardsize,y+boardsize, x+2*boardsize, y);
           g.drawLine(x+3*boardsize,y+boardsize, x+boardsize*2, y+boardsize*2);
           g.drawLine(x+2*boardsize, y,x+boardsize*2, y+boardsize*2);
           g.drawLine(x+boardsize,y+boardsize,x+3*boardsize,y+boardsize);
        //三条右斜线
           g.drawLine(x,y+2*boardsize,x+4*boardsize,y+6*boardsize);
           g.drawLine(x+2*boardsize,y+2*boardsize,x+4*boardsize,y+4*boardsize); 
           g.drawLine(x,y+4*boardsize,x+2*boardsize,y+6*boardsize);
        //三条左斜线    
           g.drawLine(x,y+6*boardsize,x+4*boardsize,y+2*boardsize);
           g.drawLine(x,y+4*boardsize,x+2*boardsize,y+2*boardsize);
           g.drawLine(x+2*boardsize,y+6*boardsize,x+4*boardsize,y+4*boardsize);
		
		
           //画出圆圈
           g.setColor(Color.RED);//先画红圆圈
           g.fillArc(x+2*boardsize-20,y-20,40,40,0,360);
           g.setColor(Color.white); //再画白圆圈，覆盖部分红圆圈
           g.fillArc(x+2*boardsize-17,y-17,34,34,0,360);
            
           g.setColor(Color.RED);//先画红圆圈
           g.fillArc(x+2*boardsize-50,y+4*boardsize-50,100,100,0,360);
           g.setColor(Color.white); //再画白圆圈，覆盖部分红圆圈
           g.fillArc(x+2*boardsize-47,y+4*boardsize-47,94,94,0,360);
            
           g.setColor(Color.RED);
           for(int k=0;k<row-3;k++)//往下画圈圈，左右两个地画
           {
                   g.setColor(Color.RED);
                   g.fillArc(x-20, y-20+boardsize*k+2*boardsize, 40,40,0,360);
                   g.setColor(Color.white); 
                   g.fillArc(x-17, y-17+boardsize*k+2*boardsize, 34,34,0,360);
                   g.setColor(Color.RED);
                   g.fillArc(x+boardsize*4-20, y-20+boardsize*k+2*boardsize, 40,40,0,360);
                   g.setColor(Color.white); 
                   g.fillArc(x+boardsize*4-17, y-17+boardsize*k+2*boardsize, 34,34,0,360);
           }
           g.setColor(Color.RED);
           for(int t=0;t<column;t++)//往右画圈圈，上下两个地画
           {
                   g.setColor(Color.RED);
                   g.fillArc(x+t*boardsize-20,y+2*boardsize-20, 40,40,0,360);
                   g.setColor(Color.white); 
                   g.fillArc(x+t*boardsize-17,y+2*boardsize-17, 34,34,0,360);
                   g.setColor(Color.RED);
                   g.fillArc(x+t*boardsize-20, y+boardsize*6-20, 40,40,0,360);
                   g.setColor(Color.white); 
                   g.fillArc(x+t*boardsize-17, y+boardsize*6-17, 34,34,0,360);
           }

           
		
           //字符
           	g.setColor(Color.RED);
		    g.drawString("捕虎棋",x-10,y-5);
		    g.drawString("陷阱",x+2*boardsize-10,y+5);
		    g.drawString("游戏规则：1、本棋供两人玩，四周摆猎犬，中间摆虎。",x+4*boardsize,y+20);
		    g.drawString("                   2、由猎犬先走，双方每次只走一步。猎犬不能",x+4*boardsize,y+40);
		    g.drawString("                   吃虎，只能困逼虎至陷阱致死或者当猎犬仅剩四",x+4*boardsize,y+60);
		    g.drawString("                   只时把虎围至任何角落无法走动致死。猎犬可在",x+4*boardsize,y+100);
		    g.drawString("                   陷阱走动。",x+4*boardsize,y+80);
		    g.drawString("                   3、当两只猎犬在一条线上，中间空位时，老虎",x+4*boardsize,y+120);
		    g.drawString("                   走入中间，可以吃掉两边一对猎犬（但如果猎犬 ",x+4*boardsize,y+140);
		    g.drawString("                   走动形成这种局面时，虎不能吃掉两边猎犬),老" ,  x+4*boardsize,y+160);     
		    g.drawString("                   虎吃到只剩两只猎犬时算胜。",x+4*boardsize,y+180);
		            

		g.setColor(Color.black);
		//重绘出棋子
		for(int i=0;i<row;i++) 
		{
			for(int j=0;j<column;j++) 
			{
				if(visit[i][j]==1) 
				{
					int countx=boardsize*i+60;
					int county=boardsize*j+60;
					/*
					g.setColor(Color.black);
					g.fillOval(county-chesssize/2, countx-chesssize/2, chesssize, chesssize);
					g.setColor(Color.white);
					g.setFont(new Font("宋体",Font.BOLD,20));    //改变字体大小
					g.drawString("狼", county, countx);
					*/
					g.drawImage(dog,county-chesssize/2-5, countx-chesssize/2-5,100,100, null);
				}
				else if(visit[i][j]==2) 
				{
					int countx=boardsize*i+60;
					int county=boardsize*j+60;
					/*
					g.setColor(Color.orange);
					g.fillOval(county-chesssize/2, countx-chesssize/2, chesssize, chesssize);
					g.setColor(Color.white);
					g.setFont(new Font("宋体",Font.BOLD,20));    //改变字体大小
					g.drawString("虎", county, countx);
					*/
					g.drawImage(tiger,county-chesssize/2-5, countx-chesssize/2-5,100,100, null);
				}
			}
		}
        g.setColor(Color.white);
        g.setFont(new Font("宋体", Font.BOLD, 19));
        g.drawString("老虎剩余时间：" + this.tiger_time_left+"秒", 550, 380);
        g.drawString("猎犬剩余时间：" + this.dog_time_left+"秒",550, 400);
		
	}
	@Override
	public void run() 
	{
		
        // 判断是否有时间限制
            while (true) 
            {
                if (this.turn==1)//狗下棋 
                {
	                this.dog_time_left--;
	                if (this.dog_time_left == 0) 
	                {
	                    JOptionPane.showMessageDialog(this, "猎犬超时，游戏结束!");
	                }
                } 
	            else 
	            {
	                this.tiger_time_left--;
	                if (this.tiger_time_left == 0) 
	                {
	                    JOptionPane.showMessageDialog(this, "老虎超时，游戏结束!");
	                    
	                }
	            }
            
            
            /*tigermessage = this.tiger_time_left / 3600 + ":" + (this.tiger_time_left / 60 - this.tiger_time_left / 3600 * 60) + ":"
                    + (this.tiger_time_left - this.tiger_time_left / 60 * 60);
            dogmessage = dog_time_left / 3600 + ":" + (dog_time_left / 60 - dog_time_left / 3600 * 60) + ":"
                    + (dog_time_left - dog_time_left / 60 * 60);*/

            this.repaint();
            try 
            {
                Thread.sleep(1000);
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
            }

            
        }
	public int eatshu(int i,int j)
	{
		//竖列
		int ans=0;
		if(i>2&&visit[i-1][j]==1&&i<6&&visit[i+1][j]==1)
		{
			ans=1;
		}
		else if(i==2&&j==2&&visit[1][2]==1&&visit[3][2]==1)
		{
			ans=1;
		}
		else if(i==1&&j==2&&visit[2][2]==1&&visit[0][2]==1)
		{
			ans=1;
		}
		return ans;
	}
	public int eatheng(int i,int j )
	{
		int ans = 0;
		if(i>1&&j<5&&visit[i][j-1]==1&&visit[i][j+1]==1)
		{
			ans=1;
		}
		if(i==1&&j==2&&visit[1][1]==1&&visit[1][3]==1)
		{
			ans=1;
		}
		
		
		return ans;
	}
	public int eatxieyouxia(int i,int j )//向右下斜
	{
		int ans = 0;
		if(i<6&&i>2&&j<4&&j>0&&visit[i-1][j-1]==1&&visit[i+1][j+1]==1)
		{
			ans=1;
		}
		if(i==2&&j==2&&visit[1][1]==1&&visit[3][3]==1)
		{
			ans=1;
		}
		return ans;
	}
	public int eatxiezuoxia(int i,int j )//向左下斜
	{
		int ans = 0;
		if(i<6&&i>2&&j>0&&j<4&&visit[i-1][j+1]==1&&visit[i+1][j-1]==1)
		{
			ans=1;
		}
		if(i==2&&j==2&&visit[3][1]==1&&visit[1][3]==1)
		{
			ans=1;
		}
		return ans;
	}

public JLabel getTimelabel() 
{
	if (this.timelabel == null) 
	{
		this.timelabel = new JLabel("");
		this.timelabel.setBounds(5, 65, 200, 20);
		this.timelabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
		timelabel.setForeground(new Color(252, 252, 248));
		
		ActionListener A=new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
			timelabel.setText(new SimpleDateFormat("EEEE hh:mm:ss").format(new Date()));
			}
		};
		this.time = new Timer(1000,A);
		time.start();
	}
	return timelabel;
	}
}


