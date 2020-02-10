package Gobang.gfl.one;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;//提示框组件


public class BoardListener implements GoBangInterface,MouseListener
{
	
	public GoBangFrame gf;
	
	public int currentx; //表示当前棋子落点
	public int currenty;
	
	int ro;//棋子在数组中的横坐标
	int colu;//棋子在数组中的纵坐标

	public void setGraphics(GoBangFrame gf) //构造函数
	{
		
		this.gf=gf;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
		int x=e.getX(); //像素坐标
		int y=e.getY();
		
		int currentx=(x/boardsize)*boardsize+60;//像素坐标取整操作
		int currenty=(y/boardsize)*boardsize+60;
		
		ro=(currenty-60)/boardsize; //棋子在数组中的横坐标
		colu=(currentx-60)/boardsize;//棋子在数组中的纵坐标
		

		
		//System.out.println(ro+"    "+colu+"     "+gf.pitchup+"     "+gf.turn);


		//System.out.println(x+" "+y+" "+x/boardsize+"  "+y/boardsize+"  "+currentx+"  "+currenty);
		
		gf.tx.setText("  ");

		Graphics g=gf.getGraphics();
		
		if(gf.visit[ro][colu]!=0) 
		{
			pick(ro,colu,g);//选子
		}
		else if(gf.visit[ro][colu]==0)
		{
			if(canmove(gf.tempi,gf.tempj,ro,colu)==1)
			{

				set(ro,colu,g);//落子
				
				if(gf.turn==1) //狗落完子，记录虎的坐标用于判断输赢
				{
					gf.tiger_x=ro;
					gf.tiger_y=colu;
					
					System.out.println(ro+"    "+colu+"     "+gf.pitchup+"     "+gf.turn);
				}
			}
		}
		if(judge()==1)
		{
			gf.end=1;
			JOptionPane.showMessageDialog(null,"猎犬获胜！", "游戏结束",JOptionPane.PLAIN_MESSAGE);
			//初始化所有棋子落点
			
		}
		else if(judge()==2)
		{
			gf.end=1;
			JOptionPane.showMessageDialog(null,"老虎获胜！", "游戏结束",JOptionPane.PLAIN_MESSAGE);
		}
		if(gf.end==1)
		{
			//初始化所有棋子落点
			gf.tiger_time_left=300; //游戏时间
			gf.dog_time_left=10;
			for(int i=0;i<7;i++)
			{
				for(int j=0;j<5;j++)
				{
					gf.visit[i][j]=0;
				}
			}
			
			gf.visit[2][0]=1;
			gf.visit[2][1]=1;
			gf.visit[2][2]=1;
			gf.visit[2][3]=1;
			gf.visit[2][4]=1;
			gf.visit[6][0]=1;
			gf.visit[6][1]=1;
			gf.visit[6][2]=1;
			gf.visit[6][3]=1;
			gf.visit[6][4]=1;
			gf.visit[3][0]=1;
			gf.visit[4][0]=1;
			gf.visit[5][0]=1;
			gf.visit[3][4]=1;
			gf.visit[4][4]=1;
			gf.visit[5][4]=1;
			gf.visit[4][2]=2;
			
			gf.ChessPositonList.clear();
			gf.turn=1;//1为狗 2为虎
			gf.pitchup = 0 ;//0为未选中，1为选中
			gf.number_of_dog=16; //狗的数量
			gf.tiger_x=4;//老虎的坐标
			gf.tiger_y=2;
			gf.end=0;
			gf.ChessPositonList.clear();
			
			gf.repaint();
			gf.tx.setText("开始新游戏");
		}
	}
	public void pick(int x,int y,Graphics g)//选中棋子函数，猎犬棋子之间可以切换，老虎只有一个
	{
		int countx=boardsize*x+60;
		int county=boardsize*y+60;
		if(gf.turn==2)//turn=1老虎方选棋
		{
			if(gf.visit[x][y]==1) //鼠标指定位置是猎犬棋
			{
				gf.tx.setText("请选中虎棋！");
			}
			else
			{
				//先设置颜色
				g.setColor(Color.red);
				
				g.drawRect(county-chesssize/2, countx-chesssize/2, chesssize, chesssize);//画线框
				
				//设置当前位置已经有棋子了,棋子为老虎

				gf.pitchup=1;
				gf.tx.setText("选中老虎！");
				gf.tempi=x;
				gf.tempj=y;
			}
		}
		
		
		else  //猎犬方选棋
		{
			
			if(gf.visit[x][y]==2) //当前不应该是虎棋选子落子
			{
				gf.tx.setText("请选中猎犬！");
			}
			else if(gf.pitchup==1) 
			{
				//gf.repaint();
				/*g.setColor(Color.white);
				g.drawRect(gf.tempi*boardsize+60-chesssize/2,gf.tempj*boardsize+60-chesssize/2,chesssize,chesssize);
				*/
				//gf.tx.setText("哈哈哈哈");
				g.setColor(Color.red);
				g.drawRect(county-chesssize/2,countx-chesssize/2, chesssize, chesssize);//画线框
				gf.pitchup=1;//选中棋子
				gf.tx.setText("选中猎犬！");
				gf.tempi=x;
				gf.tempj=y;
				
			}
			else
			{
				g.setColor(Color.red);
				
				g.drawRect(county-chesssize/2,countx-chesssize/2, chesssize, chesssize);//画线框
				gf.pitchup=1;//选中棋子
				gf.tx.setText("选中猎犬！");

				gf.tempi=x;
				gf.tempj=y;
			}
		}
	}
	public void set(int x,int y,Graphics g)//放置棋子函数
	{
		if(gf.pitchup==1)
		{
			if(gf.turn==1) //猎犬走
			{
				

				
				gf.ChessPositonList.add(new ChessPosition(gf.tempi,gf.tempj,x,y,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1));//tempi,tempj为原来的位置
				gf.visit[gf.tempi][gf.tempj]=0;
				gf.visit[x][y]=1;
				gf.turn++;
				gf.pitchup=0;
				gf.tx.setText("轮到老虎落子");

			}
			else //老虎走
			{

				int chizix1=-1,chiziy1=-1,chizix2=-1,chiziy2=-1,chizix3=-1,chiziy3=-1,chizix4=-1,chiziy4=-1,
					chizix5=-1,chiziy5=-1,chizix6=-1,chiziy6=-1,chizix7=-1,chiziy7=-1,chizix8=-1,chiziy8=-1;
				gf.visit[gf.tempi][gf.tempj]=0;
				gf.visit[x][y]=2;
				gf.turn--;
				gf.pitchup=0;
				
				int eat=0;
				//吃子判断
				  if(gf.eatshu(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro+1][colu]=0;
					  gf.visit[ro-1][colu]=0;
					  gf.tx.setText("老虎吃掉犬！");
					  gf.number_of_dog-=2;
					  chizix1=ro-1;chiziy1=colu;
					  
					  chizix2=ro+1;chiziy2=colu;
				  }
				  if(gf.eatheng(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro][colu+1]=0;
					  gf.visit[ro][colu-1]=0;
					  gf.tx.setText("老虎吃掉犬！");
					  gf.number_of_dog-=2;
					  chizix3=ro;chiziy3=colu+1;
					  chizix4=ro;chiziy4=colu-1;
				  }
				  if(gf.eatxiezuoxia(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro+1][colu-1]=0;
					  gf.visit[ro-1][colu+1]=0;
					  gf.tx.setText("老虎吃掉犬！");
					  gf.number_of_dog-=2;
					  chizix5=ro+1;chiziy5=colu-1;
					  chizix6=ro-1;chiziy6=colu+1;
				  }
				  if(gf.eatxieyouxia(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro+1][colu+1]=0;
					  gf.visit[ro-1][colu-1]=0;
					  gf.tx.setText("老虎吃掉犬！");
					  gf.number_of_dog-=2;
					  chizix7=ro+1;chiziy7=colu+1;
					  chizix8=ro-1;chiziy8=colu-1;
				  }
				  gf.ChessPositonList.add(new ChessPosition(gf.tempi,gf.tempj,x,y,chizix1,chiziy1,chizix2,chiziy2,chizix3,chiziy3,chizix4,chiziy4,chizix5,chiziy5,chizix6,chiziy6,chizix7,chiziy7,chizix8,chiziy8));//tempi,tempj为原来的位置
				  if(eat==0)
				  {
						gf.tx.setText("轮到猎犬落子");
				  }
				  System.out.println("猎犬的数量："+gf.number_of_dog);
			}
			gf.repaint();
			gf.repaint();
			//落子提示
		}
	}


	public int canmove(int i1,int j1,int i2,int j2)//判断这一步能不能走，如果能走就返回1，不能走就返回0
	{
		
		int  ans=0;
		if (i1==6&&j1==0)
		{
			if((i2==6&&j2==1)||(i2==5&&j2==0)||(i2==5&&j2==1))
			{
				ans =1;
				return ans;
			}
		}
		if (i1==6&&j1==1)
		{
			if((i2==6&&j2==0)||(i2==6&&j2==2)||(i2==5&&j2==1))
			{
				ans =1;
				return ans;
			}
		}
		if (i1==6&&j1==2)
		{
			if((i2==6&&j2==1)||(i2==6&&j2==3)||(i2==5&&j2==1)||(i2==5&&j2==2)||(i2==5&&j2==3))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==6&&j1==3)
		{
			if((i2==6&&j2==2)||(i2==6&&j2==4)||(i2==5&&j2==3))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==6&&j1==4)
		{
			if((i2==6&&j2==3)||(i2==5&&j2==3)||(i2==5&&j2==4))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==5&&j1==0)
		{
			if((i2==6&&j2==0||(i2==4&&j2==0)||(i2==5&&j2==1)))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==5&&j1==1)
		{
			if((i2==4&&j2==0)||(i2==4&&j2==1)||(i2==4&&j2==2)||(i2==5&&j2==0)||(i2==5&&j2==2)||(i2==6&&j2==0)||(i2==6&&j2==1)||(i2==6&&j2==2))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==5&&j1==2)
		{
			if((i2==4&&j2==2)||(i2==5&&j2==1)||(i2==5&&j2==3)||(i2==6&&j2==2))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==5&&j1==3)
		{
			if((i2==4&&j2==2)||(i2==4&&j2==3)||(i2==4&&j2==4)||(i2==5&&j2==2)||(i2==5&&j2==4)||(i2==6&&j2==2)||(i2==6&&j2==3)||(i2==6&&j2==4))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==5&&j1==4)
		{
			if((i2==4&&j2==4)||(i2==5&&j2==3)||(i2==6&&j2==4))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==4&&j1==0)
		{
			if((i2==5&&j2==0)||(i2==3&&j2==0)||(i2==4&&j2==1)||(i2==3&&j2==1)||(i2==5&&j2==1))
					{
				ans =1;
				return ans;
					}
		}
		if(i1==4&&j1==1)
		{
			if((i2==4&&j2==2)||(i2==4&&j2==0)||(i2==5&&j2==1)||(i2==3&&j2==1))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==4&&j1==2)
		{
			if((i2==3&&j2==1)||(i2==3&&j2==2)||(i2==3&&j2==3)||(i2==4&&j2==1)||(i2==4&&j2==3)||(i2==5&&j2==1)||(i2==5&&j2==2)||(i2==5&&j2==3))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==4&&j1==3)
		{
			if((i2==4&&j2==2)||(i2==4&&j2==4)||(i2==5&&j2==3)||(i2==3&&j2==3))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==4&&j1==4)
		{
			if((i2==4&&j2==3)||(i2==3&&j2==4)||(i2==5&&j2==4)||(i2==3&&j2==3)||(i2==5&&j2==3))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==3&&j1==0)
		{
			if((i2==3&&j2==1)||(i2==2&&j2==0)||(i2==4&&j2==0))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==3&&j1==1)
		{
			if((i2==2&&j2==0)||(i2==2&&j2==1)||(i2==2&&j2==2)||(i2==3&&j2==0)||(i2==3&&j2==2)||(i2==4&&j2==0)||(i2==4&&j2==1)||(i2==4&&j2==2))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==3&&j1==2)
		{
			if((i2==3&&j2==1)||(i2==3&&j2==3)||(i2==4&&j2==2)||(i2==2&&j2==2))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==3&&j1==3)
		{
			if((i2==2&&j2==2)||(i2==2&&j2==3)||(i2==2&&j2==4)||(i2==3&&j2==2)||(i2==3&&j2==4)||(i2==4&&j2==2)||(i2==4&&j2==3)||(i2==4&&j2==4))
			{
				ans =1;
				return ans;
			}
		}

		if(i1==3&&j1==4)
		{
			if((i2==3&&j2==3)||(i2==4&&j2==4)||(i2==2&&j2==4))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==2&&j1==0)
		{
			if((i2==3&&j2==0)||(i2==2&&j2==1)||(i2==3&&j2==1))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==2&&j1==1)
		{
			if((i2==2&&j2==0)||(i2==2&&j2==2)||(i2==3&&j2==1))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==2&&j1==2)
		{
			if((i2==1&&j2==1)||(i2==1&&j2==2)||(i2==1&&j2==3)||(i2==2&&j2==1)||(i2==2&&j2==3)||(i2==3&&j2==1)||(i2==3&&j2==2)||(i2==3&&j2==3))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==2&&j1==3)
		{
			if((i2==2&&j2==2)||(i2==2&&j2==4)||(i2==3&&j2==3))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==2&&j1==4)
		{
			if((i2==2&&j2==3)||(i2==3&&j2==3)||(i2==3&&j2==4))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==1&&j1==1)
		{
			if((i2==1&&j2==2)||(i2==0&&j2==2)||(i2==2&&j2==2))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==1&&j1==2)
		{
			if((i2==1&&j2==1)||(i2==1&&j2==3)||(i2==2&&j2==2)||(i2==0&&j2==2))
			{
				ans =1;
				return ans;
			}
		}
		if(i1==1&&j1==3)
		{
			if((i2==1&&j2==2)||(i2==0&&j2==2)||(i2==2&&j2==2))
			{
			     ans =1;
				 return ans;
			}
		}
			if(i1==0&&j1==2)
			{
			if((i2==1&&j2==1)||(i2==1&&j2==2)||(i2==1&&j2==3))
			{
					ans =1;
					return ans;
			}
		}
		return ans;
	}
	public int judge()//进行输赢判断,0表示未结束，1表示狗胜，2表示虎胜
	{
		if(gf.turn==2)
		{
			int x=gf.tiger_x;
			int y=gf.tiger_y;
			if((x==1&&y==1)||(x==1&&y==2)&&(x==1&&y==3))
			{
				return 1;
			}
			
			int[][] dir= new int[8][2];
			dir[0][0]=0;dir[0][1]=1;
			dir[1][0]=1;dir[1][1]=1;
			dir[2][0]=1;dir[2][1]=0;
			dir[3][0]=1;dir[3][1]=-1;

			dir[4][0]=0;dir[4][1]=-1;
			dir[5][0]=-1;dir[5][1]=-1;
			dir[6][0]=-1;dir[6][1]=0;
			dir[7][0]=-1;dir[7][1]=1;
			int alive=0;			
			for(int i=0;i<8;i++)
			{
				int tempx=x+dir[i][0];
				int tempy=y+dir[i][1];
				
				if(tempx>=7 || tempy<0 || tempy>=5)
				{
					continue;
				}

				if(tempx==1 && (tempy==1 || tempy==2 || tempy==3))
				{
					if(gf.visit[tempx][tempy]==0)
					{
						if((x==2 && y==0)||(x==2 && y==4))
						{
							break;//老虎落入陷阱
						}
						System.out.println("\n老虎的走子路径判断：");
						System.out.println("当前路径"+x+" "+y);
						System.out.println(dir[i][0]+" "+dir[i][1]);
						System.out.println("可走路径"+tempx+" "+tempy);
						alive=1;
						System.out.println("老虎是否还活着，0死掉，1活着    "+alive);
						break;
					}
					
				}
				if(tempx==0 && tempy==2)
				{
					if(gf.visit[tempx][tempy]==0)
					{
						System.out.println("\n老虎的走子路径判断：");
						System.out.println("当前路径"+x+" "+y);
						System.out.println(dir[i][0]+" "+dir[i][1]);
						System.out.println("可走路径"+tempx+" "+tempy);
						alive=1;
						System.out.println("老虎是否还活着，0死掉，1活着    "+alive);
						break;
					}
				}
				if(tempx>=2 && tempx<=6 && tempy>=0 && tempy<=4)
				{
					if(gf.visit[tempx][tempy]==0)
					{
						System.out.println("\n老虎的走子路径判断：");
						System.out.println("当前路径"+x+" "+y);
						System.out.println(dir[i][0]+" "+dir[i][1]);
						System.out.println("可走路径"+tempx+" "+tempy);
						alive=1;
						System.out.println("老虎是否还活着，0死掉，1活着    "+alive);
						break;
					}
				}
			}
			if(alive==0)
			{
				return 1;//猎犬获胜
			}
			
			
		}

		if(gf.number_of_dog<=2)
		{
			return 2;//老虎获胜
		}
		return 0;
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
