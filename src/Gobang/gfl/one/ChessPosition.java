package Gobang.gfl.one;


public class ChessPosition 
{
	//若无吃子，则设为-1,吃子顺序：竖横左下右下
	public int Listx1,Listy1,Listx2,Listy2,chizix1,chiziy1,chizix2,chiziy2,chizix3,chiziy3,chizix4,chiziy4,chizix5,chiziy5,chizix6,chiziy6,chizix7,chiziy7,chizix8,chiziy8;

	
	
	public ChessPosition() 
	{
	}
	public ChessPosition(int Listi1,int Listj1,int Listi2,int Listj2,int i1,int j1,int i2,int j2,int i3,int j3,int i4,int j4,int i5,int j5,int i6,int j6,int i7,int j7,int i8,int j8) {
		this.Listx1=Listi1;
		this.Listy1=Listj1;
		
		this.Listx2=Listi2;
		this.Listy2=Listj2;				
		
		this.chizix1=i1;//上
		this.chiziy1=j1;
		
		this.chizix2=i2;//下
		this.chiziy2=j2;
		
		
		this.chizix3=i3;//左
		this.chiziy3=j3;
		
		this.chizix4=i4;//右
		this.chiziy4=j4;
		
		this.chizix5=i5;//右上
		this.chiziy5=j5;
		
		this.chizix6=i6;//左下
		this.chiziy6=j6;
		
		this.chizix7=i7;//左上
		this.chiziy7=j7;
		
		this.chizix8=i8;//右下
		this.chiziy8=j8;

	}
}
