/*
 * update 8Mar14
 * 
 * what i want:
 * 	delete getRandomPos();
 * 	stop checking for traps DISCRETELY, make a universal trap checker
 * 	evolve getProperPos() into getBestPos()
 * 	
 * 	
 * 	
 * -------------------------------------------------------------
 * 
 * may or may not
 * 	use commutative property of mathematical operators to check for in a row
 * 	improve coding, replace ifs by ternary operator
 *  
 * *****************************************************************
 * 
 * update 6Mar14
 * 
 * -------------------------------------------------------------
 * 
 * added:
 * 	very dramatic changes, useless if else conditions removed based upon "no other choice"
 * 	added Horse trap
 * 	by cleverly using counter and random pos, getProperPos() was born!
 * 
 * ------------------------------------------------------------
 * 
 * removed:
 * 	isPosValid(), its just a 2 line function
 * 	onCorners(), onPlusWhere(), etc
 * 	the previous conditions of p==corners
 * 
 * ----------------------------------------------------------------
 * 
 * may or may not
 * 	
 * 	
 * 
 * *****************************************************
 * 
 * update 4Mar14
 * 
 * -------------------------------------------------------------
 * 
 * added:
 * 	the numbering system of board
 * 
 * ----------------------------------------------------------------
 * 
 * may or may not
 * 	add choice for user for symbol X or O
 * 	if bored then option to quit
 * 	add difficulty level
 * 	add 2 player
 * 	
 * 
 * *****************************************************
 * 
 * update 3Mar14 9:17 PM
 * 
 * ----------------------------------------------------------
 * 
 * added:
 * 	modified counter to work even as 'favour'
 * 	added temp variables pU and pC
 * 	removed previous winner check renaming it to posEmpty()
 * 	added winnerCheck()
 * 	now the Comp first checks for its favour and then applies counter
 * 	added the draw condition
 * 	random start implemented
 * 
 * -------------------------------------------------------		
 * 
 * left out things:
 * 	implement the L trap
 * 	remove the InputMismatchException, caused when a non-numeric value is given as input for user's position 
 * 	implement the right angled triangle trap (horse).
 * 		 U |   |   
		---+---+---
   		   |   | X 
		---+---+---
		   | X |   		and their 'rotations'
 * 	refer the rough notebook and implement (never make here) traps
 * 
 * -------------------------------------------------------------
 * 
 * may or may not: 
 * 	let the user start and then plan each and every possibility
 * 
 * *******************************
 * 
 * update 27Feb14 10:58PM
 * 
 * ---------------------------------------------------------------
 * 
 * added:
 * 	comments
 * 	compintel()
 * 	counter(,)
 * 	now pos itself indicates index, no need to put pos-1
 * 	changed the format of board, looks decent now!
 * 	the concept of types:
 * 		1. first row
 * 		2. second row
 * 		3. third row
 * 		4. first column
 * 		5. second column
 * 		6. third column
 * 		7. back slash
 * 		8. forward slash
 * 	changed the 'boardfull' condition as count<5
 * 	whostarted
 * 	
 * ----------------------------------------------------------------	
 * 
 * left out things:
 * 	change the winnerCheck()'s functioning according to its name!
 * 	do something about count==1 and p==2,4,6,8
 * 	implement the L trap
 * 	change starting to random
 * 	remove the InputMismatchException, caused when a non-numeric value is given as input for user's position
 * 
 * ---------------------------------------------------------
 * 
 * may or may not:
 * 	the conv2to1 and vice versa function need to be removed along with its array
 * 	.5 and 0.33 prob makers
 * 	getRandomPos() in a specified 'type'
 * 	the draw triangle
 * 	apply OOP model
 * 
 * ***********************************************************
 */


import java.util.*;
public class game 
{
	int countC,countU,whoStarted;
	//whoStarted is one if comp started and zero if user did
	
	int[] cch=new int[5], uch=new int[5];
	
	int[] avail=new int[9];
	
	char[] A=new char[10];
	//A[0] is one for comp chance and zero for user's chance
	
	Scanner sc=new Scanner(System.in);
	
	public static void main(String args[])
	{
		game g=new game();
		g.gameplay();
		//g.test();
	}
	
	//it starts the game and restarts it as per user's choice
	void gameplay()
	{
		init();
		System.out.println();
		for(int i=1; i<=9; i++)
		{
			System.out.print(" "+i+" ");
			if(i%3!=0)
				System.out.print("|");
			if(i==3 || i==6)
			{
				System.out.println("\n---+---+---");
				
			}
			
		}
		System.out.println("\n");
		
		whoStarted=whoStarts();
		System.out.println("Randomly deciding starting player...");
		if(whoStarted==0)
			userChance();
		else
			compChance();
		
		//this block is reached when the game is almost over
		if(winnerCheck('U'))
			System.out.println("\nUser Wins!");
		else if(winnerCheck('C'))
			System.out.println("\nComputer Wins!");
		else
			System.out.println("\nThe Game Draws!");
		System.out.print("\nGame Ends!\nDo you want to quit (Y) ? ");
		char c = sc.next().trim().charAt(0);
		if(c!='y' && c!='Y')
			gameplay();
		else
			System.out.print("\nGood Bye and Thank you!\n");
	}
	
	
	void userChance()
	{
		System.out.print("User(U) , Enter your mark's positon: ");
		int pos=sc.nextInt();
		while(! (A[pos]==' ' && pos<10 && pos>0) )
		{
			System.out.print("Invalid position! Enter new value: ");
			pos=sc.nextInt();
		}
		shift(pos);
		countU++;
		uch[countU-1]=pos;
		A[pos]='U';
		printBoard();
		if(countU<5 && !winnerCheck('U'))
		{
			A[0]=1;			
			compChance();
		}
	}
	
	
	void compChance()
	{
		System.out.print("Computer's Chance\n");
		int pos=compIntel();
		shift(pos);
		countC++;
		cch[countC-1]=pos;
		System.out.print("The computer chose position "+pos);
		A[pos]='C';
		printBoard();
		if(countC<5 && !winnerCheck('C'))
		{
			A[0]=0;
			userChance();
		}
		
	}
	
	
	/*
	 * this function is the brain of the pc for ttt
	 * it is divided into scenarios based on count(chances of pc) 
	 * computes the appropriate position and returns it
	*/
	int compIntel()
	{
		int pC=counter(0,'C',true), pU=counter(0,'U',true); //this is a temporary variable
		//stores counter value or previous value
		if(pC!=0)
			return pC;
		else if(pU!=0)
			return pU;
		else if(countC==0) //for the very first chance
		{
			if(whoStarted==0)
			{
				if(uch[0]%2==1 && uch[0]!=5) //has user occupied any corners?
					return 5;
				else if(A[5]=='U')
					return getRandomCorner();
				else //has user occupied on plus?
					return counterForPlus(uch[0]);
			}
			else
			{
				/*
				Random rand=new Random();
				int r=rand.nextInt(3);
				return r==0? getRandomCorner(): (r==1?getRandomEven():5) ;
				*/
				return getRandomCorner();
			}
		}
		
		else if(countC==1) //for comp's second chance
		{
			if(whoStarted==0)
			{
				// if user chose 5, then...
				if(uch[0]==5)				
					return getRandomCorner();					
					
					//there is no need of all this diagonal checking
					//because control reaches here, implicitly implies
					//that user is going for this very diagonal cross
					//and comp has to select a random corner to be
					//on the safe side
					
					//if user would not have gone for diagonal cross,
					//the control would not have reached here!
				
				
				//as user started, and selected even, to make a draw
				//C must go for 1st counterforplus() and then pos=5
				//there are lots of other draw possibilities but those
				//need to be calculated. 5 is common to all those
				else if(uch[0]%2==0)
					return 5;
				
				//in this case, user is trying to double cross, which can
				//be easily evaded if pos is even
				else if( (A[1]=='U' && A[9]=='U') || (A[3]=='U' && A[7]=='U') )
					return getRandomEven();
				
				
				//checking for horse cross
				//actually there is no need to do that, if everything fails,
				//then definitely  horse counter is under effect
				else //if(counterForHorse()!=0)  <--- no need!
					return counterForHorse();
			} //user started, comp's 2nd chance ends here
			
			
			// if the computer started...
			else
			{
				
				//and user's choice is not 5, then...TRAPPED!
				if(uch[0]!=5)
				{
					int trap=not5Trap();
					if(trap!=0)
						return trap;
				}
				return getProperPos('C');
			}
								
		}
		
		//this mainly is for those 'tricky' traps
		else if(countC==2)
		{
			//is it necessary to add condition of who started here?
			if(cch[2]!=0)
				return cch[2];
			return getProperPos('C');
				
		}
		
		//this implies that comp started and there is no counter, etc
		//and the game is clearly draw
		else if(countC==4)
			return getRandomPos();
		
		else
			return getProperPos('C');
	}
	
	int counter(int type, char c, boolean all)
	{
		if(type<=3 || all)
		{
			for(int i=1; i<=7; i+=3) //i assumes 1,4,7
			{
				if(A[i]==c)
				{
					if(A[i+1]==c && A[i+2]==' ')
						return i+2;
					if(A[i+2]==c && A[i+1]==' ')
						return i+1;
				}
				if(A[i+1]==c && A[i+2]==c && A[i]==' ')
					return i;
			}
		}
		if(type>3 && type<=6 || all)
		{
			for(int i=1; i<=3; i++) //i assumes 1,2,3
			{
				if(A[i]==c)
				{
					if(A[i+3]==c && A[i+6]==' ')
						return i+6;
					if(A[i+6]==c && A[i+3]==' ')
						return i+3;
				}
				if(A[i+3]==c && A[i+6]==c && A[i]==' ')
					return i;
			}
		}
		if(type==7 || all)
		{
			if(A[1]==c)
			{
				if(A[5]==c && A[9]==' ')
					return 9;
				if(A[9]==c && A[5]==' ')
					return 5;
			}
			if(A[5]==c && A[9]==c && A[1]==' ')
				return 1;
		}
		if(type==8 || all)
		{
			if(A[3]==c)
			{
				if(A[5]==c && A[7]==' ')
					return 7;
				if(A[7]==c && A[5]==' ')
					return 5;
			}
			if(A[5]==c && A[7]==c && A[3]==' ')
				return 3;
		}
		return 0;
	}
	// has c won?
	boolean winnerCheck(char c)
	{
		int i, k;
		boolean flag=false;
		for( i=1,k=1; i<=7; i+=3,k++)
		{
			if( (A[i]==c && A[i+1]==c && A[i+2]==c) || (A[k]==c && A[k+3]==c && A[k+6]==c) )
			{
				flag=true;
				break;
			}
		}
		if( (A[1]==c && A[5]==c && A[9]==c) || (A[3]==c && A[5]==c && A[7]==c) )
			flag=true;
		return flag;
	}
	
	int whoStarts()
	{
		Random r=new Random();
		return r.nextInt(2);
	}
			
	// it returns a random available position, does not check for board full
	int getRandomPos()
	{
		Random rand=new Random();
		int pos=0;
		do
		{
			pos=rand.nextInt(9)+1;
		}while(A[pos]!=' ');		
		return pos;
		
	}
	int getProperPos(char c)
	{
		int pos=getRandomPos(),p;
		for( p=1; p<=10; p++ )
		{
			A[pos]=c;
			if(counter(0,c,true)!=0)
			{
				A[pos]=' ';
				break;
			}
			A[pos]=' ';
			pos=getRandomPos();
		}
		//System.out.print("\n***"+p+"***\n");
		return pos;
	}

	int getRandomCorner()
	{
		int Corner[]={1,3,7,9};
		int r=0;
		Random rand= new Random();
		do
		{
			r=rand.nextInt(4);
		}while(A[Corner[r]]!=' ');
		return Corner[r];
	}
	
	int getRandomEven()
	{
		int pos=0;
		Random r= new Random();
		do
		{
			pos=2 * (r.nextInt(4)+1);
		}while(A[pos]!=' ');
		return pos;
	}
	
	int counterForHorse()
	{
		int p=uch[1];
		switch(uch[0])
		{
		case 1:
			if(p==8 || p==6)
				return 9;
		case 3:
			if(p==8 || p==4)
				return 7;
		case 7:
			if(p==2 || p==6)
				return 3;
		case 9: 
			if(p==2 || p==4)
				return 1;
		}
		return 0;
	}
	
	//this function returns position of flanks corresponding to user's choice
	int counterForPlus(int p)
	{
		switch(p)
		{
		case 2:
		{
			if(whoStarts()==0)
				return 1;
			else return 3;
		}
		case 4:
		{
			if(whoStarts()==0)
				return 1;
			else
				return 7;
		}
		case 6:
			if(whoStarts()==0)
				return 3;
			else
				return 9;
			
		case 8:
			if(whoStarts()==0)
				return 7;
			else
				return 9;
			
		default:
			return 0;
		}
		
		
	}
	
	
	int not5Trap()
	{
		
		for(int i=0; avail[i]!=0; i++)
		{
			int availl=avail[i];
			A[avail[i]]='C';
			int cC=counter(0,'C',true);
			if(cC!=0)
			{
				A[cC]='U';
				int cU=counter(0,'U',true);
				if(cU!=0)
				{
					A[cU]='C';
					if(trappedBy('C'))
					{
						//System.out.print("\n***Self Trap Detected***\n");
						A[avail[i]]=A[cU]=A[cC]=' ';
						return avail[i];
					}
					A[cU]=' ';
				}
				else
				{
					for(int k=0; avail[k]!=0; k++)
					{
						if(avail[k]==cC || k==i)
							continue;
						A[avail[k]]='C';
						if(trappedBy('C'))
						{
							//System.out.print("\n***Tricky Trap Detected***\n");
							A[avail[i]]=A[cC]=A[avail[k]]=' ';
							cch[2]=avail[k];
							return avail[i];
						}
						A[avail[k]]=' ';
					}
				}
				A[cC]=' ';
			}
			A[avail[i]]=' ';
		}
		//System.out.print("\n***NO Self Trap Detected***\n");
		return 0;
	}
	
	boolean trappedBy(char c)
	{
		int C=0;
		if(counter(3,c,false)!=0)
			C++;
		if(counter(6,c,false)!=0)
			C++;
		if(counter(7,c,false)!=0)
			C++;
		if(counter(8,c,false)!=0)
			C++;
		if(C>1)
			return true;
		else
			return false;
	}
	
	void shift(int pos)
	{
		int i,k;
		for(i=0; i<9; i++)
		{
			if(avail[i]==pos)
			{
				for(k=i+1; k<9; k++)
				{
					avail[k-1]=avail[k];
				}
				avail[k-1]=0;
				break;
			}
		}
	}

	//it initializes the 'game array' 
	void init()
	{
		System.out.print("The game is initializing...\n");
		int i;
		for(i=1; i<=9; i++)
		{			
				A[i]=' ';
		}
		for(i=0; i<=4; i++)
		{			
				uch[i]=cch[i]=0;
		}
		countU=0;
		countC=0;
		Random random=new Random();
		int r=random.nextInt(9)+1;
		for(i=0; i<9; i++)
		{
			avail[i]=r;
			if(r==9)
				r=0;
			r++;
		}
	}

	//displays current 'game array' with formatting
	void printBoard()
	{
		System.out.println();
		for(int i=1; i<=9; i++)
		{
			System.out.print(" "+A[i]+" ");
			if(i%3!=0)
				System.out.print("|");
			if(i==3 || i==6)
				System.out.println("\n---+---+---");						
		}
		System.out.println("\n");
	}
	
	void test()
	{
		init();
		int i;
		for(i=0; i<9; i++)
			System.out.print(avail[i]+" ");
		System.out.println("\nshifting 3rd pos");
		shift(3);
		for(i=0; i<9; i++)
			System.out.print(avail[i]+" ");
		System.out.println("\nshifting 7th pos");
		shift(7);
		for(i=0; i<9; i++)
			System.out.print(avail[i]+" ");
		System.out.println("\nshifting 1st pos");
		shift(1);
		for(i=0; i<9; i++)
			System.out.print(avail[i]+" ");
		System.out.println("\nshifting 9th pos");
		shift(9);
		for(i=0; i<9; i++)
			System.out.print(avail[i]+" ");
		
	}
	
	void getBestPos()
	{
		int i,j,k;
		for(i=0; avail[i]!=0; i++)
		{
			int availl=avail[i];
			A[availl]='C';
			if(counter(0,'C',true)!=0)
			{
				
			}
		}
	}
	
	boolean recursive(int available)
	{
		if(avail[available]==0)
			return false;
		else
		{
			for(int i=available; avail[i]!=0; i++)
			{
				
			}
		}
		
		return false;	
	}
}





/*

//check whether all positions are taken, if yes return false
boolean posEmpty()
{
	for(int i=1; i<=9; i++)
	{
		if(A[i]==' ')
			return true;
	}
	return false;
	
}


//it returns false when pos is valid
boolean isPosValid(int pos)
{
	if(A[pos]==' ' && pos<10 && pos>0)
		return true;
	else
		return false;
}


void initPairs()
{
	int i,j;
	for(i=0; i<10; i++)
	{
		for(j=0; j<10; j++)
			pairs[i][j]=false;
	}
	for(i=2; i<=9; i++)
	{
		if(i==6 || i==8)
			continue;
		pairs[1][i]=true;
		pairs[i][1]=true;
	}
}


int onPlusWhere(char c)
{
	for(int i=2; i<=8; i+=2)
	{
		if(A[i]==c)
			return i;
	}
	return 0;
}

// is c on corners?
boolean onCorners(char c)
{
	if(A[1]==c || A[3]==c || A[7]==c || A[9]==c)
		return true;
	else
		return false;
}
void conv1to2(int pos)
{
	pos--;
	conv[0]=pos/3;
	conv[1]=pos%3;
	
}

int conv2to1()
{
	int pos=0;
	for(int i=0; i<2; i++)
	{
		for(int j=0; j<2; j++)
		{
			pos++;
			if(i==conv[0] && j==conv[1])
				break;
		}
	}
	return pos;
}
else
		{
			p=cch[0]; //stores previous position here
			
			if(p==5)
			{
				if(whoStarted==0 && ( (A[1]=='U' && A[9]=='U') || (A[3]=='U' && A[7]=='U')   ) )
				{
					pos=getRandomEven();
				}
				else
					pos=getRandomPos();
			}
			else if( p==2 || p==4 || p==6 || p==8)
				pos=getRandomPos();
			else if(p==1)
			{
				if(isPosValid(4) && isPosValid(7))
					pos=4;
				if(isPosValid(5) && isPosValid(9))
					pos=5;
				if(isPosValid(2) && isPosValid(3))
					pos=2;
			}
			
			else if(p==9)
			{
				if(isPosValid(6) && isPosValid(3))
					pos=6;
				if(isPosValid(5) && isPosValid(1))
					pos=5;
				if(isPosValid(8) && isPosValid(7))
					pos=8;
			}
			
			else if(p==3)
			{
				if(isPosValid(2) && isPosValid(1))
					pos=2;
				if(isPosValid(5) && isPosValid(7))
					pos=5;
				if(isPosValid(6) && isPosValid(9))
					pos=6;
			}
			
			else if(p==7)
			{
				if(isPosValid(4) && isPosValid(1))
					pos=4;
				if(isPosValid(5) && isPosValid(3))
					pos=5;
				if(isPosValid(8) && isPosValid(9))
					pos=8;
			}
		}

*/
