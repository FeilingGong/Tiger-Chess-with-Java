package Gobang.gfl.one;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
/**
 * ��ť������
 */
public class ButtonListener implements ActionListener
{
	public GoBangFrame gf; 
	public ButtonListener(GoBangFrame gf)
	{
		this.gf=gf;//���̣�����벿��
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("��ʼ��Ϸ"))	//����ǿ�ʼ����Ϸ�İ�ť����Ϊ��벿�����ü�������
		{
			gf.t.resume();
			gf.tx.setText("��Ϸ��ʼ����Ȯ����");

			//System.out.println("��ʼ��");
			BoardListener boardListener=new BoardListener();//BoardListenerʵ����MouseListener��������
			boardListener.setGraphics(gf);//��ȡ���ʶ���
			gf.addMouseListener((MouseListener) boardListener);//Ϊ�����������������
		}
		if(e.getActionCommand().equals("��ʼ����Ϸ"))
		{
			//��ʼ�������������
			for(int i=0;i<7;i++)
			{
				for(int j=0;j<5;j++)
				{
					gf.visit[i][j]=0;
				}
			}
			gf.tiger_time_left=300; //��Ϸʱ��
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
			gf.turn=1;//1Ϊ�� 2Ϊ��
			gf.pitchup = 0 ;//0Ϊδѡ�У�1Ϊѡ��
			gf.number_of_dog=16; //��������
			gf.tiger_x=4;//�ϻ�������
			gf.tiger_y=2;
			gf.repaint();
			gf.tx.setText("��ʼ����Ϸ");
		}
		else if(e.getActionCommand().equals("����"))
		{
			if(gf.ChessPositonList.size()>=1) {
				//������������Ӧ��λ����Ϊ0��
				ChessPosition l=new ChessPosition();
				
				//��ȡ���һ�����ӵĶ�����Ϣ
				l=gf.ChessPositonList.remove(gf.ChessPositonList.size()-1);
				

				gf.visit[l.Listx1][l.Listy1]=gf.visit[l.Listx2][l.Listy2];
				gf.visit[l.Listx2][l.Listy2]=0;
				
				if(l.chizix1!=-1)
				{
					gf.visit[l.chizix1][l.chiziy1]=1;
					gf.number_of_dog++;//��Ȯ������
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
				
				
				
				//����һ�ԭΪ��һ�������
				if(gf.turn==1) gf.turn++;
				else gf.turn--;
				
				//ֱ�ӵ���gf���ػ淽�����ػ淽���Ļ���Ӧ����������ҳ�滹û���ɵ�ʱ���Ҫ��ȡ
				//����repaint���Զ�����paint���������Ҳ��ø�����
				gf.repaint();
				//gf.paint(gf.getGraphics());
 
			}
			else {
				gf.tx.setText("���ܻ��壡");
			}

		}
		else if(e.getActionCommand().equals("����")) 
		{
			if(gf.turn==1) 
				{
					JOptionPane.showMessageDialog(null,"�ϻ�Ӯ��", "��Ϸ����",JOptionPane.PLAIN_MESSAGE);
				}
			else 
				{
					JOptionPane.showMessageDialog(null,"��ȮӮ��", "��Ϸ����",JOptionPane.PLAIN_MESSAGE);
				}
		}
		
	}
	
}
