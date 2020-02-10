package Gobang.gfl.one;

public  interface GoBangInterface 
{
	//定义与棋盘数据相关的接口，保存棋盘的起点，格子大小，行数列数等信息
	
	int x=60,y=60;//初始坐标，画棋盘时需要用到
	
	int boardsize=100;//棋盘每一格的大小
	
	int chesssize=90;//棋子的直径大小
	
	int row=7,column=5;//棋盘的行数与列数

}
