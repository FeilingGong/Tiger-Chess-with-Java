package Gobang.gfl.one;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
/**
 * 按钮监听器
 */
public class ButtonListener implements ActionListener
{
	public GoBangFrame gf; 
	public ButtonListener(GoBangFrame gf)
	{
		this.gf=gf;//棋盘，即左半部分
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("开始游戏"))	//如果是开始新游戏的按钮，再为左半部分设置监听方法
		{
			gf.t.resume();
			gf.tx.setText("游戏开始，猎犬先行");

			//System.out.println("开始！");
			BoardListener boardListener=new BoardListener();//BoardListener实现了MouseListener鼠标监听器
			boardListener.setGraphics(gf);//获取画笔对象
			gf.addMouseListener((MouseListener) boardListener);//为棋盘添加了鼠标监听器
		}
		if(e.getActionCommand().equals("开始新游戏"))
		{
			//初始化所有棋子落点
			for(int i=0;i<7;i++)
			{
				for(int j=0;j<5;j++)
				{
					gf.visit[i][j]=0;
				}
			}
			gf.tiger_time_left=300; //游戏时间
			gf.dog_time_left=10;
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
			gf.repaint();
			gf.tx.setText("开始新游戏");
		}
		else if(e.getActionCommand().equals("悔棋"))
		{
			if(gf.ChessPositonList.size()>=1) {
				//把棋子数组相应的位置置为0；
				ChessPosition l=new ChessPosition();
				
				//获取最后一个棋子的对象信息
				l=gf.ChessPositonList.remove(gf.ChessPositonList.size()-1);
				

				gf.visit[l.Listx1][l.Listy1]=gf.visit[l.Listx2][l.Listy2];
				gf.visit[l.Listx2][l.Listy2]=0;
				
				if(l.chizix1!=-1)
				{
					gf.visit[l.chizix1][l.chiziy1]=1;
					gf.number_of_dog++;//猎犬数量加
				}
				if(l.chizix2!=-1)
				{
					gf.visit[l.chizix2][l.chiziy2]=1;
					gf.number_of_dog++;
				}
				if(l.chizix3!=-1)
				{
					gf.visit[l.chizix3][l.chiziy3]=1;
					gf.number_of_dog++;
				}
				if(l.chizix4!=-1)
				{
					gf.visit[l.chizix4][l.chiziy4]=1;
					gf.number_of_dog++;
				}
				if(l.chizix5!=-1)
				{
					gf.visit[l.chizix5][l.chiziy5]=1;
					gf.number_of_dog++;
				}
				if(l.chizix6!=-1)
				{
					gf.visit[l.chizix6][l.chiziy6]=1;
					gf.number_of_dog++;
				}
				if(l.chizix7!=-1)
				{
					gf.visit[l.chizix7][l.chiziy7]=1;
					gf.number_of_dog++;
				}
				if(l.chizix8!=-1)
				{
					gf.visit[l.chizix8][l.chiziy8]=1;
					gf.number_of_dog++;
				}
				
				
				
				//把玩家还原为上一步的玩家
				if(gf.turn==1) gf.turn++;
				else gf.turn--;
				
				//直接调用gf的重绘方法，重绘方法的画笔应该是在棋盘页面还没生成的时候就要获取
				//调用repaint会自动调用paint方法，而且不用给参数
				gf.repaint();
				//gf.paint(gf.getGraphics());
 
			}
			else {
				gf.tx.setText("不能悔棋！");
			}

		}
		else if(e.getActionCommand().equals("认输")) 
		{
			if(gf.turn==1) 
				{
					JOptionPane.showMessageDialog(null,"老虎赢！", "游戏结束",JOptionPane.PLAIN_MESSAGE);
				}
			else 
				{
					JOptionPane.showMessageDialog(null,"猎犬赢！", "游戏结束",JOptionPane.PLAIN_MESSAGE);
				}
		}
		
	}
	
}
