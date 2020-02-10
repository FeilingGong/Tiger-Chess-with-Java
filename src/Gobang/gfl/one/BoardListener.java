package Gobang.gfl.one;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;//��ʾ�����


public class BoardListener implements GoBangInterface,MouseListener
{
	
	public GoBangFrame gf;
	
	public int currentx; //��ʾ��ǰ�������
	public int currenty;
	
	int ro;//�����������еĺ�����
	int colu;//�����������е�������

	public void setGraphics(GoBangFrame gf) //���캯��
	{
		
		this.gf=gf;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
		int x=e.getX(); //��������
		int y=e.getY();
		
		int currentx=(x/boardsize)*boardsize+60;//��������ȡ������
		int currenty=(y/boardsize)*boardsize+60;
		
		ro=(currenty-60)/boardsize; //�����������еĺ�����
		colu=(currentx-60)/boardsize;//�����������е�������
		

		
		//System.out.println(ro+"    "+colu+"     "+gf.pitchup+"     "+gf.turn);


		//System.out.println(x+" "+y+" "+x/boardsize+"  "+y/boardsize+"  "+currentx+"  "+currenty);
		
		gf.tx.setText("  ");

		Graphics g=gf.getGraphics();
		
		if(gf.visit[ro][colu]!=0) 
		{
			pick(ro,colu,g);//ѡ��
		}
		else if(gf.visit[ro][colu]==0)
		{
			if(canmove(gf.tempi,gf.tempj,ro,colu)==1)
			{

				set(ro,colu,g);//����
				
				if(gf.turn==1) //�������ӣ���¼�������������ж���Ӯ
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
			JOptionPane.showMessageDialog(null,"��Ȯ��ʤ��", "��Ϸ����",JOptionPane.PLAIN_MESSAGE);
			//��ʼ�������������
			
		}
		else if(judge()==2)
		{
			gf.end=1;
			JOptionPane.showMessageDialog(null,"�ϻ���ʤ��", "��Ϸ����",JOptionPane.PLAIN_MESSAGE);
		}
		if(gf.end==1)
		{
			//��ʼ�������������
			gf.tiger_time_left=300; //��Ϸʱ��
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
			gf.turn=1;//1Ϊ�� 2Ϊ��
			gf.pitchup = 0 ;//0Ϊδѡ�У�1Ϊѡ��
			gf.number_of_dog=16; //��������
			gf.tiger_x=4;//�ϻ�������
			gf.tiger_y=2;
			gf.end=0;
			gf.ChessPositonList.clear();
			
			gf.repaint();
			gf.tx.setText("��ʼ����Ϸ");
		}
	}
	public void pick(int x,int y,Graphics g)//ѡ�����Ӻ�������Ȯ����֮������л����ϻ�ֻ��һ��
	{
		int countx=boardsize*x+60;
		int county=boardsize*y+60;
		if(gf.turn==2)//turn=1�ϻ���ѡ��
		{
			if(gf.visit[x][y]==1) //���ָ��λ������Ȯ��
			{
				gf.tx.setText("��ѡ�л��壡");
			}
			else
			{
				//��������ɫ
				g.setColor(Color.red);
				
				g.drawRect(county-chesssize/2, countx-chesssize/2, chesssize, chesssize);//���߿�
				
				//���õ�ǰλ���Ѿ���������,����Ϊ�ϻ�

				gf.pitchup=1;
				gf.tx.setText("ѡ���ϻ���");
				gf.tempi=x;
				gf.tempj=y;
			}
		}
		
		
		else  //��Ȯ��ѡ��
		{
			
			if(gf.visit[x][y]==2) //��ǰ��Ӧ���ǻ���ѡ������
			{
				gf.tx.setText("��ѡ����Ȯ��");
			}
			else if(gf.pitchup==1) 
			{
				//gf.repaint();
				/*g.setColor(Color.white);
				g.drawRect(gf.tempi*boardsize+60-chesssize/2,gf.tempj*boardsize+60-chesssize/2,chesssize,chesssize);
				*/
				//gf.tx.setText("��������");
				g.setColor(Color.red);
				g.drawRect(county-chesssize/2,countx-chesssize/2, chesssize, chesssize);//���߿�
				gf.pitchup=1;//ѡ������
				gf.tx.setText("ѡ����Ȯ��");
				gf.tempi=x;
				gf.tempj=y;
				
			}
			else
			{
				g.setColor(Color.red);
				
				g.drawRect(county-chesssize/2,countx-chesssize/2, chesssize, chesssize);//���߿�
				gf.pitchup=1;//ѡ������
				gf.tx.setText("ѡ����Ȯ��");

				gf.tempi=x;
				gf.tempj=y;
			}
		}
	}
	public void set(int x,int y,Graphics g)//�������Ӻ���
	{
		if(gf.pitchup==1)
		{
			if(gf.turn==1) //��Ȯ��
			{
				

				
				gf.ChessPositonList.add(new ChessPosition(gf.tempi,gf.tempj,x,y,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1));//tempi,tempjΪԭ����λ��
				gf.visit[gf.tempi][gf.tempj]=0;
				gf.visit[x][y]=1;
				gf.turn++;
				gf.pitchup=0;
				gf.tx.setText("�ֵ��ϻ�����");

			}
			else //�ϻ���
			{

				int chizix1=-1,chiziy1=-1,chizix2=-1,chiziy2=-1,chizix3=-1,chiziy3=-1,chizix4=-1,chiziy4=-1,
					chizix5=-1,chiziy5=-1,chizix6=-1,chiziy6=-1,chizix7=-1,chiziy7=-1,chizix8=-1,chiziy8=-1;
				gf.visit[gf.tempi][gf.tempj]=0;
				gf.visit[x][y]=2;
				gf.turn--;
				gf.pitchup=0;
				
				int eat=0;
				//�����ж�
				  if(gf.eatshu(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro+1][colu]=0;
					  gf.visit[ro-1][colu]=0;
					  gf.tx.setText("�ϻ��Ե�Ȯ��");
					  gf.number_of_dog-=2;
					  chizix1=ro-1;chiziy1=colu;
					  
					  chizix2=ro+1;chiziy2=colu;
				  }
				  if(gf.eatheng(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro][colu+1]=0;
					  gf.visit[ro][colu-1]=0;
					  gf.tx.setText("�ϻ��Ե�Ȯ��");
					  gf.number_of_dog-=2;
					  chizix3=ro;chiziy3=colu+1;
					  chizix4=ro;chiziy4=colu-1;
				  }
				  if(gf.eatxiezuoxia(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro+1][colu-1]=0;
					  gf.visit[ro-1][colu+1]=0;
					  gf.tx.setText("�ϻ��Ե�Ȯ��");
					  gf.number_of_dog-=2;
					  chizix5=ro+1;chiziy5=colu-1;
					  chizix6=ro-1;chiziy6=colu+1;
				  }
				  if(gf.eatxieyouxia(ro,colu)==1 )
				  {
					  eat=1;
					  gf.visit[ro+1][colu+1]=0;
					  gf.visit[ro-1][colu-1]=0;
					  gf.tx.setText("�ϻ��Ե�Ȯ��");
					  gf.number_of_dog-=2;
					  chizix7=ro+1;chiziy7=colu+1;
					  chizix8=ro-1;chiziy8=colu-1;
				  }
				  gf.ChessPositonList.add(new ChessPosition(gf.tempi,gf.tempj,x,y,chizix1,chiziy1,chizix2,chiziy2,chizix3,chiziy3,chizix4,chiziy4,chizix5,chiziy5,chizix6,chiziy6,chizix7,chiziy7,chizix8,chiziy8));//tempi,tempjΪԭ����λ��
				  if(eat==0)
				  {
						gf.tx.setText("�ֵ���Ȯ����");
				  }
				  System.out.println("��Ȯ��������"+gf.number_of_dog);
			}
			gf.repaint();
			gf.repaint();
			//������ʾ
		}
	}


	public int canmove(int i1,int j1,int i2,int j2)//�ж���һ���ܲ����ߣ�������߾ͷ���1�������߾ͷ���0
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
	public int judge()//������Ӯ�ж�,0��ʾδ������1��ʾ��ʤ��2��ʾ��ʤ
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
							break;//�ϻ���������
						}
						System.out.println("\n�ϻ�������·���жϣ�");
						System.out.println("��ǰ·��"+x+" "+y);
						System.out.println(dir[i][0]+" "+dir[i][1]);
						System.out.println("����·��"+tempx+" "+tempy);
						alive=1;
						System.out.println("�ϻ��Ƿ񻹻��ţ�0������1����    "+alive);
						break;
					}
					
				}
				if(tempx==0 && tempy==2)
				{
					if(gf.visit[tempx][tempy]==0)
					{
						System.out.println("\n�ϻ�������·���жϣ�");
						System.out.println("��ǰ·��"+x+" "+y);
						System.out.println(dir[i][0]+" "+dir[i][1]);
						System.out.println("����·��"+tempx+" "+tempy);
						alive=1;
						System.out.println("�ϻ��Ƿ񻹻��ţ�0������1����    "+alive);
						break;
					}
				}
				if(tempx>=2 && tempx<=6 && tempy>=0 && tempy<=4)
				{
					if(gf.visit[tempx][tempy]==0)
					{
						System.out.println("\n�ϻ�������·���жϣ�");
						System.out.println("��ǰ·��"+x+" "+y);
						System.out.println(dir[i][0]+" "+dir[i][1]);
						System.out.println("����·��"+tempx+" "+tempy);
						alive=1;
						System.out.println("�ϻ��Ƿ񻹻��ţ�0������1����    "+alive);
						break;
					}
				}
			}
			if(alive==0)
			{
				return 1;//��Ȯ��ʤ
			}
			
			
		}

		if(gf.number_of_dog<=2)
		{
			return 2;//�ϻ���ʤ
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
