package Gobang.gfl.one;

import java.awt.BorderLayout; //�߽�ʽ����
import java.awt.FlowLayout;//��ʽ����
import java.awt.Font;//����
import java.awt.Color;//��ɫ
import java.awt.Dimension;

import java.awt.Graphics; //��ͼ

import java.awt.Image;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat; //���ڸ�ʽ
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame; //����������
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; //���������
import javax.swing.JTextField;
import javax.swing.Timer; //ʱ�����



/*�൱��JFrame��һ���������JPanel�����ӣ��������Ҫ�Բ���JFrame�ϵ�
 * �������й������ȣ����Ǿ���Ҫʹ����ЩJPanel����
 */


public class GoBangFrame extends JPanel implements GoBangInterface,Runnable
{
    Thread t = new Thread(this);//�߳�

	public Graphics g;//����һ֧����
	public int[][] visit=new int[7][5];////��ά���鴢�����̵������������Ϊ0����Ϊ1����Ϊ2��ѡ�й�Ϊ3��ѡ�л�Ϊ4
	public String[] butname= {"��ʼ��Ϸ","��ʼ����Ϸ","����","����"};
	public JButton[] button=new JButton[butname.length];
	public ArrayList<ChessPosition>ChessPositonList=new ArrayList<ChessPosition>();//����ÿһ�����������
	public int turn=1;//1Ϊ�� 2Ϊ��
	public int pitchup = 0 ;//0Ϊδѡ�У�1Ϊѡ��
	
	public Timer time;
	public JLabel timelabel;//�������
	
	
	public int tempi,tempj ;//���ڴ����һ������
	
	public int number_of_dog=16; //��������
	int tiger_x=4;//�ϻ�������
	int tiger_y=2;

	public int end=0;//��Ϸ������־
	
	public int tiger_time_left=300; //��Ϸʱ��
	public int dog_time_left=300;
	
	public String tigermessage="������";
	public String dogmessage="������";
	
	JTextField tx=new JTextField();//��ʾ��
	
	Image tiger = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Images/�ϻ�.png"));
	Image dog = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Images/����.png"));
	//Image tiger=Toolkit.getDefaultToolkit().getImage("images/�ϻ�.png");
	
	
	//Image dog=Toolkit.getDefaultToolkit().getImage("images/����.png");
	
	
	public GoBangFrame() {

        this.repaint();
        // �����������
        //addMouseListener((MouseListener) this);
        // addMouseMotionListener((MouseMotionListener) this);
        // this.requestFocus();
        t.start();
        t.suspend();// �̹߳���


    }
	
	public static void main(String[] args)//----------------------------------------------���������
	{
		
		GoBangFrame gf=new GoBangFrame();
		gf.initUI();
		gf.BoundListener();
	}
	
	
	public void initUI()//��ʼ��һ������,�����ñ����С������
	{
		
		JFrame jf=new JFrame();
		jf.setTitle("��������Ϸ");
		jf.setSize(1000, 900);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf.setLayout(new BorderLayout());//���ö�������JFrameΪ�߽�ʽ����
		jf.setVisible(true);//���ô������ʾ״̬��������
		
		Dimension dim1=new Dimension(150,0);//�Ұ벿�ִ�С
		Dimension dim2=new Dimension(550,0);//��벿�ִ�С
		Dimension dim3=new Dimension(140,40); //���ð�ť�����С
		
		//ʵ����ߵĽ��棬��GoBangframe�Ķ�����ӵ���ܲ��ֵ��м䲿��
		this.setPreferredSize(dim2);//�����������Ĵ�С
		this.setBackground(Color.orange);//��������������ɫ
		jf.add(this, BorderLayout.CENTER);//��ӵ���ܲ��ֵ��м䲿��
		

		//ʵ���ұߵ�JPanel��������
		JPanel jp=new JPanel();
		jp.setPreferredSize(dim1);
		jp.setBackground(Color.LIGHT_GRAY);
		jp.setLayout(new FlowLayout());
		
		//������������Ҫ�Ѱ�ť��������μӵ��Ǹ�JPanel����
		//���ð�ť����
		
		
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
		
		
		//jf.setResizable(false); //���ɸı��С
		
		//��ʼ�������������
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
	
	public void BoundListener()//�󶨰�ť������
	{
		ButtonListener ButListener=new ButtonListener(this);//ButtonListenerʵ����ActionListener����Ϊһ��������
		for(int i=0;i<butname.length;i++)
		{
			button[i].addActionListener(ButListener);//Ϊÿ����ť��Ӽ��������������а������¼�������
		}
	}

	public void paint(Graphics g) //��������
	{
		super.paint(g);
		
		g.setColor(Color.red);
        //����ֱ��
		for(int i=0;i<row-2;i++) 
		{
			g.drawLine(x, y+boardsize*i+2*boardsize, x+boardsize*(column-1), y+boardsize*i+2*boardsize);//����
		}
		
		for(int j=0;j<column;j++) 
		 
		{
			g.drawLine(x+boardsize*j, y+2*boardsize, x+boardsize*j, y+boardsize*(row-1));//����
		}
		
		//����б��
        //���ε�����
           g.drawLine(x+boardsize,y+boardsize, x+boardsize*2, y+boardsize*2);
           g.drawLine(x+boardsize,y+boardsize, x+2*boardsize, y);
           g.drawLine(x+3*boardsize,y+boardsize, x+2*boardsize, y);
           g.drawLine(x+3*boardsize,y+boardsize, x+boardsize*2, y+boardsize*2);
           g.drawLine(x+2*boardsize, y,x+boardsize*2, y+boardsize*2);
           g.drawLine(x+boardsize,y+boardsize,x+3*boardsize,y+boardsize);
        //������б��
           g.drawLine(x,y+2*boardsize,x+4*boardsize,y+6*boardsize);
           g.drawLine(x+2*boardsize,y+2*boardsize,x+4*boardsize,y+4*boardsize); 
           g.drawLine(x,y+4*boardsize,x+2*boardsize,y+6*boardsize);
        //������б��    
           g.drawLine(x,y+6*boardsize,x+4*boardsize,y+2*boardsize);
           g.drawLine(x,y+4*boardsize,x+2*boardsize,y+2*boardsize);
           g.drawLine(x+2*boardsize,y+6*boardsize,x+4*boardsize,y+4*boardsize);
		
		
           //����ԲȦ
           g.setColor(Color.RED);//�Ȼ���ԲȦ
           g.fillArc(x+2*boardsize-20,y-20,40,40,0,360);
           g.setColor(Color.white); //�ٻ���ԲȦ�����ǲ��ֺ�ԲȦ
           g.fillArc(x+2*boardsize-17,y-17,34,34,0,360);
            
           g.setColor(Color.RED);//�Ȼ���ԲȦ
           g.fillArc(x+2*boardsize-50,y+4*boardsize-50,100,100,0,360);
           g.setColor(Color.white); //�ٻ���ԲȦ�����ǲ��ֺ�ԲȦ
           g.fillArc(x+2*boardsize-47,y+4*boardsize-47,94,94,0,360);
            
           g.setColor(Color.RED);
           for(int k=0;k<row-3;k++)//���»�ȦȦ�����������ػ�
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
           for(int t=0;t<column;t++)//���һ�ȦȦ�����������ػ�
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

           
		
           //�ַ�
           	g.setColor(Color.RED);
		    g.drawString("������",x-10,y-5);
		    g.drawString("����",x+2*boardsize-10,y+5);
		    g.drawString("��Ϸ����1�����幩�����棬���ܰ���Ȯ���м�ڻ���",x+4*boardsize,y+20);
		    g.drawString("                   2������Ȯ���ߣ�˫��ÿ��ֻ��һ������Ȯ����",x+4*boardsize,y+40);
		    g.drawString("                   �Ի���ֻ�����ƻ��������������ߵ���Ȯ��ʣ��",x+4*boardsize,y+60);
		    g.drawString("                   ֻʱ�ѻ�Χ���κν����޷��߶���������Ȯ����",x+4*boardsize,y+100);
		    g.drawString("                   �����߶���",x+4*boardsize,y+80);
		    g.drawString("                   3������ֻ��Ȯ��һ�����ϣ��м��λʱ���ϻ�",x+4*boardsize,y+120);
		    g.drawString("                   �����м䣬���ԳԵ�����һ����Ȯ���������Ȯ ",x+4*boardsize,y+140);
		    g.drawString("                   �߶��γ����־���ʱ�������ܳԵ�������Ȯ),��" ,  x+4*boardsize,y+160);     
		    g.drawString("                   ���Ե�ֻʣ��ֻ��Ȯʱ��ʤ��",x+4*boardsize,y+180);
		            

		g.setColor(Color.black);
		//�ػ������
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
					g.setFont(new Font("����",Font.BOLD,20));    //�ı������С
					g.drawString("��", county, countx);
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
					g.setFont(new Font("����",Font.BOLD,20));    //�ı������С
					g.drawString("��", county, countx);
					*/
					g.drawImage(tiger,county-chesssize/2-5, countx-chesssize/2-5,100,100, null);
				}
			}
		}
        g.setColor(Color.white);
        g.setFont(new Font("����", Font.BOLD, 19));
        g.drawString("�ϻ�ʣ��ʱ�䣺" + this.tiger_time_left+"��", 550, 380);
        g.drawString("��Ȯʣ��ʱ�䣺" + this.dog_time_left+"��",550, 400);
		
	}
	@Override
	public void run() 
	{
		
        // �ж��Ƿ���ʱ������
            while (true) 
            {
                if (this.turn==1)//������ 
                {
	                this.dog_time_left--;
	                if (this.dog_time_left == 0) 
	                {
	                    JOptionPane.showMessageDialog(this, "��Ȯ��ʱ����Ϸ����!");
	                }
                } 
	            else 
	            {
	                this.tiger_time_left--;
	                if (this.tiger_time_left == 0) 
	                {
	                    JOptionPane.showMessageDialog(this, "�ϻ���ʱ����Ϸ����!");
	                    
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
		//����
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
	public int eatxieyouxia(int i,int j )//������б
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
	public int eatxiezuoxia(int i,int j )//������б
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
		this.timelabel.setFont(new Font("΢���ź�", Font.BOLD, 12));
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


