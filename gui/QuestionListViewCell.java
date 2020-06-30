/* game.c - xmain, prntr */

#include <conf.h>
#include <kernel.h>
#include <io.h>
#include <bios.h>
#include <dos.h>
#include <conio.h>
#include <stdio.h> 
#include <stdlib.h> 
#include <time.h>


//לחיצה על המקשים a w d x עבור שחקן 1
#define LEFT_PLAYER1 1
#define RIGHT_PLAYER1 2
#define UP_PLAYER1 3
#define DOWN_SHORT_PLAYER1 4
#define DOWN_LONG_PLAYER1 5
//לחיצה על החיצים עבור שחקן 2
#define LEFT_PLAYER2 6
#define RIGHT_PLAYER2 7
#define UP_PLAYER2 8
#define DOWN_SHORT_PLAYER2 9
#define DOWN_LONG_PLAYER2 10

#define START_SCREEN 1
#define GAME_SCREEN 2




extern SYSCALL  sleept(int);
extern struct intmap far *sys_imp;
/*------------------------------------------------------------------------
 *  xmain  --  example of 2 processes executing the same code concurrently
 *------------------------------------------------------------------------
 */
 

int receiver_pid;
int is_long_down_press=0;
int colors[6]={0x19,0x2a,0x3b,0x4c,0x5d,0x6e};//מכיל צבעים עבור החלקים שיורדים. מגרילים מספר באמצעות הרוטינה get_rand_color ולוקחים את הצבע מהמערך הזה במיקם שהחזירה הרוטינה

int input_arr[2048];
int front = -1;
int rear = -1;
int isStopped1=0;
int isStopped2=0;
int point_in_cycle;
int gcycle_length;
int gno_of_pids;

char game_board[25][80];

typedef struct position
{
  int x;
  int y;

}  POSITION;



typedef struct player
{
	int board[15][6];
	int score;
	int speed;
	int curPiece[3];
	char curOrientation;
	int nextPiece[3];
	int curPiece_x;
	int curPiece_y;
}PLAYER;

typedef struct game_info
{
	int selectedDifficultyLevel;
	int screen;
	int curLevel;
	int secondsToLevelEnd;
}GAME;
void initialGameBoard();
void initializer();
void start_game();
void change_only_color_on_screen(int row,int col,int color);
void change_screen(int row,int col, char c,int color);
int get_rand_color();
void display_next(int positionX,int positionY,int nextColors[],char orientation);
void delete_previou_location(int positionX,int positionY, char orientation);
void fill_next_colors(int player,int nextColors[]);
void clearScreen();
void initialGameBoard();	
void generate_welcome_screen();
GAME game;

PLAYER player1;
PLAYER player2;

void initializer()
{
	int i,j;
	game.screen=START_SCREEN;
	game.curLevel=0;
	for(i=0;i<15;i++)
		for(j=0;j<6;j++)
		{
			player1.board[i][j]=0;
			player2.board[i][j]=0;
		}
	player1.score=0;
	player2.score=0;
	player1.speed=1;
	player2.speed=1;
}

void start_game()
{
	change_screen(0,0,'%',0xEE);
	game.screen=GAME_SCREEN;
	game.curLevel=1;
	game.secondsToLevelEnd=240;
	initialGameBoard();
}

void change_only_color_on_screen(int row,int col,int color)
{
	
	int place=(row*80+col)*2;

	asm{
		
		MOV              AX,0B800h     
		MOV              ES,AX         
		MOV              DI,place
		MOV              BX,color
		MOV              AH,BL
		INC              DI
		MOV              BYTE PTR ES:[DI],AH
	}
}

	INTPROC new_int9(int mdevno)
{
  int result;
 int scan = 0;
  int ascii = 0;
  int i,j;

asm {
  MOV AH,1
  INT 16h
  JZ Skip1
  MOV AH,0
  INT 16h
  MOV BYTE PTR scan,AH
  MOV BYTE PTR ascii,AL
 } //asm
 if(game.screen==GAME_SCREEN)
 {
	 if (scan == 75)
	 result=LEFT_PLAYER2;
  if (scan == 72)
     result=UP_PLAYER2;
    if (scan == 77);	
      result=RIGHT_PLAYER2;
   if(scan == 30)
	  result=LEFT_PLAYER1;
   if (scan==32)
	  result=RIGHT_PLAYER1;
  if(scan== 17)
	 result=UP_PLAYER1;
 if ((scan == 46)&& (ascii == 3)) // Ctrl-C?
   asm INT 27; // terminate xinu
   send(receiver_pid, result);
 }
 else if(game.screen==START_SCREEN)
 {
	for(i=19;i<22;i++)
		for(j=5;j<43;j++)
			change_only_color_on_screen(i,j,0x0d);
	 if(scan==2)
	 {game.selectedDifficultyLevel=1;
		change_only_color_on_screen(0,0,0x0C);}
	 else if(scan==3)
	 {game.selectedDifficultyLevel=2;
		change_only_color_on_screen(0,0,0x0C);}
	 else if(scan==4)
		game.selectedDifficultyLevel=3;
	 else if (scan==31)
		start_game(); 
	 else if ((scan == 46)&& (ascii == 3)) // Ctrl-C?
   asm INT 27; // terminate xinu
	 else if (scan==72)
		 i=0;
	else;   
 }
 
//עוד לא עשיתי פה את החץ למטה (לחיצה קצרה וארוכה) ואת המקש x (לחיצה קצרה וארוכה עבור השחקן השני)
  

Skip1:

} // new_int9
void set_new_int9_newisr()
{
  int i;
  for(i=0; i < 32; i++)
    if (sys_imp[i].ivec == 9)
    {
     sys_imp[i].newisr = new_int9;
     return;
    }

} // set_new_int9_newisr




/*------------------------------------------------------------------------
 *  prntr  --  print a character indefinitely
 *------------------------------------------------------------------------
 */



void displayer( void )
{

	while (1)
    {
               receive();
			   /** here we send the updated matrix to the screen**/
			//   printf(display);
              
    } //while
} // prntr

void receiver()
{
  while(1)
  {
	  
    int temp;
    temp = receive();
	rear++;
	input_arr[rear] = temp;
    if (front == -1)
       front = 0;
    
  } // while

} //  receiver



void change_screen(int row,int col, char c,int color)
//משנה את כרטיס המסך במיקום הנתון ושם את תו c ואטריביוט color
{
	
	int place=(row*80+col)*2;

	asm{
		
		MOV              AX,0B800h     
		MOV              ES,AX         
		MOV              DI,place
		MOV              AL, c
		MOV              BX,color
		MOV              AH,BL
		MOV              WORD PTR ES:[DI],AX
	}
}

int get_rand_color()//מגריל מספר רנדומלי בין 0-5 
{
	
	return rand() % 6;
	
}
	
void display_next(int positionX,int positionY,int nextColors[],char orientation)//מציג את החלק שיורד במיקום הנתון ועם הצבעים הנתונים, בכיוון orientation
{
	if(orientation=='v')
	{
		change_screen(positionY,positionX,176,nextColors[0]);
		change_screen(positionY,positionX+1,176,nextColors[0]);
		change_screen(positionY,positionX+2,176,nextColors[0]);
		
		change_screen(positionY+1,positionX,176,nextColors[1]);
		change_screen(positionY+1,positionX+1,176,nextColors[1]);
		change_screen(positionY+1,positionX+2,176,nextColors[1]);
		
		change_screen(positionY+2,positionX,176,nextColors[2]);
		change_screen(positionY+2,positionX+1,176,nextColors[2]);
		change_screen(positionY+2,positionX+2,176,nextColors[2]);
	}
	if(orientation=='h')
	{
		change_screen(positionY,positionX,176,nextColors[0]);
		change_screen(positionY,positionX+1,176,nextColors[0]);
		change_screen(positionY,positionX+2,176,nextColors[0]);
		
		change_screen(positionY,positionX+4,176,nextColors[1]);
		change_screen(positionY,positionX+5,176,nextColors[1]);
		change_screen(positionY,positionX+6,176,nextColors[1]);
		
		change_screen(positionY,positionX+8,176,nextColors[2]);
		change_screen(positionY,positionX+9,176,nextColors[2]);
		change_screen(positionY,positionX+10,176,nextColors[2]);
	}
}

void delete_previou_location(int positionX,int positionY, char orientation)//מוחק את החלק שירד מהלוח כדי שנוכל להדפיס אותו מחדש במקום החדשץ מקבל מיקום של הנקודה בה הוא מתחיל וכיוון (אופקי או אנכי)
{
	if(orientation=='v')
	{
		change_screen(positionY,positionX,' ',0x00);
		change_screen(positionY,positionX+1,' ',0x00);
		change_screen(positionY,positionX+2,' ',0x00);
		
		change_screen(positionY+1,positionX,' ',0x00);
		change_screen(positionY+1,positionX+1,' ',0x00);
		change_screen(positionY+1,positionX+2,' ',0x00);
		
		change_screen(positionY+2,positionX,' ',0x00);
		change_screen(positionY+2,positionX+1,' ',0x00);
		change_screen(positionY+2,positionX+2,' ',0x00);
	}
	if(orientation=='h')
	{
		change_screen(positionY,positionX,' ',0x00);
		change_screen(positionY,positionX+1,' ',0x00);
		change_screen(positionY,positionX+2,' ',0x00);
		
		change_screen(positionY,positionX+4,' ',0x00);
		change_screen(positionY,positionX+5,' ',0x00);
		change_screen(positionY,positionX+6,' ',0x00);
		
		change_screen(positionY,positionX+8,' ',0x00);
		change_screen(positionY,positionX+9,' ',0x00);
		change_screen(positionY,positionX+10,' ',0x00);
	}
}

void fill_next_colors(int player,int nextColors[])//עדכון המקום בצד שמציג את הצבעים הבאים שצריכים לרדתת מקבל מספר שחקן (1 או 2) ומערך המכיל את 3 הצבעים הבאים
{
	int i;
	if(player==1)
	{
		for(i=0;i<3;i++)
		{
			change_screen(12+i,33,176,nextColors[i]);
			change_screen(12+i,34,176,nextColors[i]);
			change_screen(12+i,35,176,nextColors[i]);
		}
	}
	if(player==2)
	{
		for(i=0;i<3;i++)
		{
			change_screen(12+i,44,176,nextColors[i]);
			change_screen(12+i,45,176,nextColors[i]);
			change_screen(12+i,46,176,nextColors[i]);
		}
	}
}	

void clearScreen()
{
	asm{
		MOV              AH,0      
		MOV              AL,3          
		INT              10h           

		MOV              AX,0B800h     

		MOV              ES,AX         
		MOV              DI,0          
		MOV              AL,' '        
		MOV              AH,0Eh        
		MOV              CX,1000       
		CLD                            
		REP              STOSW         

	}
}

void initialGameBoard()
{
	 int i,j;
  time_t t;
		//אתחול המערך המכיל את הטיוטה עבור הדפסה לכרטיס המסך של הלוח הבסיסי של המשחק
	for(i=0;i<25;i++)
		for(j=0;j<80;j++)
		{
			game_board[i][j]=' ';
			change_screen(i,j,' ',0x00);
		}
	
	
	//מפה מתחילה ההדפסה הראשונית של כל הקווים למסך
	for(i=4;i<24;i++)
	{
		game_board[i][5]='*';
		game_board[i][6]='*';
		game_board[i][25]='*';
		game_board[i][26]='*';
		game_board[i][53]='*';
		game_board[i][54]='*';
		game_board[i][73]='*';
		game_board[i][74]='*';
	}
	
	for(i=5;i<27;i++)
	{
		game_board[23][i]='*';
		
	}
	
	
	for(i=53;i<75;i++)
	{
		game_board[23][i]='*';
	}
	
	for(i=0;i<10;i++)
	{
		game_board[i][32]='*';
		game_board[i][31]='*';
		game_board[i][32]='*';
		game_board[i][48]='*';
		game_board[i][47]='*';
	}
	
	for(i=10;i<25;i++)
	{
		game_board[i][39]='*';
		game_board[i][40]='*';
	}
	
	for(i=32;i<48;i++)
	{
		game_board[9][i]='*';
		
	}
	
	for(i=12;i<15;i++)
	{
		game_board[i][32]='*';
		game_board[i][36]='*';
		game_board[i][43]='*';
		game_board[i][47]='*';
	}
	
	for(i=32;i<37;i++)
	{
		game_board[11][i]='*';
		game_board[15][i]='*';
	}
	
	for(i=43;i<48;i++)
	{
		game_board[11][i]='*';
		game_board[15][i]='*';
	}
	
	game_board[1][35]='T';
	game_board[1][36]='i';
	game_board[1][37]='m';
	game_board[1][38]='e';
	game_board[1][40]='l';
	game_board[1][41]='e';
	game_board[1][42]='f';
	game_board[1][43]='t';
	game_board[1][44]=':';
	
	game_board[1][10]='P';
	game_board[1][11]='L';
	game_board[1][12]='A';
	game_board[1][13]='Y';
	game_board[1][14]='E';
	game_board[1][15]='R';
	game_board[1][17]='1';
	
	game_board[1][58]='P';
	game_board[1][59]='L';
	game_board[1][60]='A';
	game_board[1][61]='Y';
	game_board[1][62]='E';
	game_board[1][63]='R';
	game_board[1][65]='2';
	
	game_board[5][38]='W';
	game_board[5][39]='i';
	game_board[5][40]='n';
	game_board[5][41]='s';
	game_board[5][42]=':';
	
	game_board[7][38]='0';
	game_board[7][39]=' ';
	game_board[7][40]=':';
	game_board[7][41]=' ';
	game_board[7][42]='0';
	
	
	game_board[19][32]='S';
	game_board[19][33]='p';
	game_board[19][34]='e';
	game_board[19][35]='e';
	game_board[19][36]='d';
	game_board[19][37]=':';
	
	game_board[19][43]='S';
	game_board[19][44]='p';
	game_board[19][45]='e';
	game_board[19][46]='e';
	game_board[19][47]='d';
	game_board[19][48]=':';
	
		srand((unsigned) time(&t));
	
	for(i=0;i<25;i++)
		for(j=0;j<80;j++)
			if(game_board[i][j]=='*')
				change_screen(i,j,' ',0x0FF);
			else if((game_board[i][j]>='a' && game_board[i][j]<='z')||game_board[i][j]==':'||(game_board[i][j]>='A' && game_board[i][j]<='Z')||(game_board[i][j]>='0' && game_board[i][j]<='9')||game_board[i][j]=='-')
				change_screen(i,j,game_board[i][j],0x0E);
	get_rand_color();
	player1.curPiece[0]=colors[get_rand_color()];
    player1.curPiece[1]=colors[get_rand_color()];
    player1.curPiece[2]=colors[get_rand_color()];
    player2.curPiece[0]=colors[get_rand_color()];
    player2.curPiece[1]=colors[get_rand_color()];
    player2.curPiece[2]=colors[get_rand_color()];
	
	player1.nextPiece[0]=colors[get_rand_color()];
    player1.nextPiece[1]=colors[get_rand_color()];
    player1.nextPiece[2]=colors[get_rand_color()];
    player2.nextPiece[0]=colors[get_rand_color()];
    player2.nextPiece[1]=colors[get_rand_color()];
    player2.nextPiece[2]=colors[get_rand_color()];
}

void generate_welcome_screen()
{
	int i,j,k=0;
	char welcomeMsg[320];
	char colomnsMsg[538];
	char difficultyMsg[34];
	char difficultyMsgL1[37];
	char difficultyMsgL2[39];
	char difficultyMsgL3[37];
	char startMsg[24];
	strcpy(colomnsMsg," _______    _______    ___        __   __    __   __    __    _    _______ |       |  |       |  |   |      |  | |  |  |  |_|  |  |  |  | |  |       ||       |  |   _   |  |   |      |  | |  |  |       |  |   |_| |  |  _____||       |  |  | |  |  |   |      |  |_|  |  |       |  |       |  | |_____ |      _|  |  |_|  |  |   |___   |       |  |       |  |  _    |  |_____  ||     |_   |       |  |       |  |       |  | ||_|| |  | | |   |   _____| ||_______|  |_______|  |_______|  |_______|  |_|   |_|  |_|  |__|  |_______|");
	strcpy(welcomeMsg,"               _                            _                      | |                          | |        __      _____| | ___ ___  _ __ ___   ___  | |_ ___   \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\   \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) |   \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/  ");
	strcpy(difficultyMsg,"Please select a difficulty level:");
	strcpy(difficultyMsgL1,"For easy level with 4 colors press 1");
	strcpy(difficultyMsgL2,"For medium level with 5 colors press 2");
	strcpy(difficultyMsgL3,"-For hard level with 6 colors press 3");
	strcpy(startMsg,"To start- please press S");
	for(i=0;i<25;i++)
		for(j=0;j<80;j++)
			game_board[i][j]=' ';
	
	for(i=0;i<7;i++)
		for(j=0;j<75;j++)
		{
			game_board[7+i][3+j]=colomnsMsg[k++];
			change_screen(7+i,3+j,game_board[7+i][3+j],0x0E);
		}
		k=0;
	for(i=0;i<6;i++)
		for(j=0;j<53;j++)
		{
			game_board[1+i][3+j]=welcomeMsg[k++];
			change_screen(1+i,3+j,game_board[1+i][3+j],0x0A);
		}
	k=0;
	for(i=0;i<33;i++)
	{
		game_board[17][5+i]=difficultyMsg[k++];
		change_screen(17,5+i,game_board[17][5+i],0x0C);
	}	
	
	k=0;
	for(i=0;i<36;i++)
	{
		game_board[19][5+i]=difficultyMsgL1[k++];
		change_screen(19,5+i,game_board[19][5+i],0x0D);
	}	
	k=0;
	for(i=0;i<38;i++)
	{
		game_board[20][5+i]=difficultyMsgL2[k++];
		change_screen(20,5+i,game_board[20][5+i],0x0D);
	}
	k=0;
	for(i=0;i<37;i++)
	{
		game_board[21][4+i]=difficultyMsgL3[k++];
		change_screen(21,4+i,game_board[21][4+i],0x0D);
	}
	k=0;
	for(i=0;i<24;i++)
	{
		game_board[20][50+i]=startMsg[k++];
		change_screen(20,50+i,game_board[20][50+i],0x0C);
	}
}

void updateter()
{

	
	int i,j;
	time_t t;
	char ch;
	int action;
 
	int temp;

	//מיקום בצורה שבידיוק צריכה ליפול
    player1.curPiece_x=13;
	player1.curPiece_y=4;
	player2.curPiece_x=60;
	player2.curPiece_y=4;
	
//	display_next(player1.curPiece_x,player1.curPiece_y,player1.curPiece,'v');
//	display_next(player2.curPiece_x,player2.curPiece_y,player2.curPiece,'v');
	
//	fill_next_colors(1,player1.nextPiece);
//	fill_next_colors(2,player2.nextPiece);
isStopped1 = 0;
   isStopped2 = 0;
 while(1)
  {

   receive();
   
	if(game.screen==START_SCREEN)
	{
		if(game.selectedDifficultyLevel==1)
			for(i=0;i<36;i++)
				change_only_color_on_screen(19,5+i,0xEE);
		if(game.selectedDifficultyLevel==2)
			for(i=0;i<38;i++)
				change_only_color_on_screen(20,5+i,0xEE);
		if(game.selectedDifficultyLevel==3)
			for(i=0;i<36;i++)
				change_only_color_on_screen(21,5+i,0xEE);
			continue;
	}
	
   while(front != -1)
   {
	   //ממה שהבנתי, פה מתבצעים עדכונים בעקבות לחיצות של השחקנים
     action = input_arr[front];
     if(front != rear)
       front++;
     else
       front = rear = -1;
     if ( (action == LEFT_PLAYER1 && isStopped1 == 0) )
	 {
		 
       if (player1.curPiece_x>7 )
	   {
		   delete_previou_location(player1.curPiece_x,player1.curPiece_y,'v');
			player1.curPiece_x-=3;
			display_next(player1.curPiece_x,player1.curPiece_y,player1.curPiece,'v');	
	   }
	 }
    else if ( action==RIGHT_PLAYER1 && isStopped1 == 0)
   if (player1.curPiece_x<22 )
	   {
		   delete_previou_location(player1.curPiece_x,player1.curPiece_y,'v');
			player1.curPiece_x+=3;
			display_next(player1.curPiece_x,player1.curPiece_y,player1.curPiece,'v');	
	}
	else if ( (action == LEFT_PLAYER2 && isStopped2 == 0) )
	 {
       if (player2.curPiece_x>55)
	   {
		   delete_previou_location(player2.curPiece_x,player2.curPiece_y,'v');
			player2.curPiece_x-=3;
			display_next(player2.curPiece_x,player2.curPiece_y,player2.curPiece,'v');	
	   }
	 }
	 else if ( action==RIGHT_PLAYER2 && isStopped2 == 0)
		if (player2.curPiece_x<72 )
	   {
		   delete_previou_location(player2.curPiece_x,player2.curPiece_y,'v');
			player2.curPiece_x+=3;
			display_next(player2.curPiece_x,player2.curPiece_y,player2.curPiece,'v');	
		}
    } // while(front != -1)7
	// לאחר שנגמרת הלולאה הפנימית מבצעים את השינויים בעקבות זמן שעובר(כגון ירידת החלק עוד שורה למטה)
    action= -1;
    
	if(player1.curPiece_y<20)
	{
		delete_previou_location(player1.curPiece_x,player1.curPiece_y,'v');
		player1.curPiece_y++;
		display_next(player1.curPiece_x,player1.curPiece_y,player1.curPiece,'v');
	}
	else isStopped1=1;
	
	
	if(player2.curPiece_y<20)
	{
		delete_previou_location(player2.curPiece_x,player2.curPiece_y,'v');
		player2.curPiece_y++;
		display_next(player2.curPiece_x,player2.curPiece_y,player2.curPiece,'v');
	}
	else isStopped2=1;
}	

} // updater 

int sched_arr_pid[5] = {-1};
int sched_arr_int[5] = {-1};


SYSCALL schedule(int no_of_pids, int cycle_length, int pid1, ...)
{
  int i;
  int ps;
  int *iptr;

  disable(ps);

  gcycle_length = cycle_length;
  point_in_cycle = 0;
  gno_of_pids = no_of_pids;

  iptr = &pid1;
  for(i=0; i < no_of_pids; i++)
  {
    sched_arr_pid[i] = *iptr;
    iptr++;
    sched_arr_int[i] = *iptr;
    iptr++;
  } // for
  restore(ps);

} // schedule 

xmain()
{
	
        int uppid, dispid, recvpid;
initializer();	
	clearScreen();
		//initialGameBoard();
        resume( dispid = create(displayer, INITSTK, INITPRIO, "DISPLAYER", 0) );
        resume( recvpid = create(receiver, INITSTK, INITPRIO+3, "RECIVEVER", 0) );
        resume( uppid = create(updateter, INITSTK, INITPRIO, "UPDATER", 0) );
        receiver_pid =recvpid;  
		generate_welcome_screen();
        set_new_int9_newisr();
    schedule(2,6, dispid, 0,  uppid, 3);
} // xmain
