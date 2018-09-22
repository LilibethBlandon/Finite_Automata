//import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;

public class ProductionUnit {

	protected Scanner keyboard = new Scanner(System.in);
	
	protected String rhs;
	protected String lhsVariable;
	protected boolean isItNullable=false;
	String [] arrayCharacter;
	protected ArrayList<String> rhsCombos;
	
	ProductionUnit(String lhs){
		rhs = new String();
		lhsVariable = lhs;
	}
	void setRHS(String rhs)
	{
		this.rhs = rhs;
	}
	
	/*void setRHSSample(String text)
	{
		this.rhs = rhs;
	}
	*/
	
	String getLHS()
	{
		return lhsVariable;
	}
	
	String getRHS()
	{
		return rhs;
	}
	int rhsCharactersLength()
	{
		arrayCharacter = rhs.split("");
		/*for(int i=0;i<arrayCharacter.length;i++)
		{
			(arrayCharacter[i];
		}
		*/
		if(arrayCharacter[0].equals("E"))
		{
			return 0;
		}
		return arrayCharacter.length;
	}
	//
	String getRHSCharacter(int index)
	{
		return arrayCharacter[index];
	}
	String[] getRHSArrayCharacter()
	{
		return arrayCharacter;
	}
	
	void setRHSCombos(ArrayList<String> combos)
	{
		rhsCombos = combos;
	}
	
	ArrayList<String> getRHSCombos()
	{
		return rhsCombos;
	}
	
	/*int rhsArraySize()
	{
		return rhs.size();
	}
	*/
	
	void setNullable()
	{
		isItNullable = true;
	}
	boolean getNullable()
	{
		return isItNullable;
	} 
}
