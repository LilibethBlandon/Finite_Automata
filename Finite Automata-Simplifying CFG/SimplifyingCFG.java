import java.util.ArrayList;

public class SimplifyingCFG {
	protected ArrayList<ProductionUnit> p;
	protected int totalNumberOfLHS;
	protected ArrayList<String> setOfNullables;
	
	SimplifyingCFG()
	{
		p = new ArrayList<>();
		setOfNullables = new ArrayList<>();
	}
	void sampleNull()
	{
		p.add(new ProductionUnit("S"));
		p.add(new ProductionUnit("A"));
		p.add(new ProductionUnit("B"));
		p.add(new ProductionUnit("B"));
		p.add(new ProductionUnit("C"));
		p.add(new ProductionUnit("C"));
		p.add(new ProductionUnit("D"));

		p.get(0).setRHS("ABaC");
		p.get(1).setRHS("BC");
		p.get(2).setRHS("b");
		p.get(3).setRHS("E");
		p.get(4).setRHS("D");
		p.get(5).setRHS("E");
		p.get(6).setRHS("d");
		
		display();
		totalNumberOfLHS = p.size();
	}

	void nullableSet()
	{
		//System.out.println("FIRST SET Of NULLS");
		
		for(int i=0; i<totalNumberOfLHS; i++)
		{	
			if(p.get(i).rhsCharactersLength() == 0)
			{
				//System.out.println(p.get(i).getLHS());
				setOfNullables.add(p.get(i).getLHS());
			}
		}
		
		//System.out.println("ADD SECOND ROUND OF NULLS");
		for(int i=0; i<totalNumberOfLHS; i++)
		{
			int counter=0;
			if(p.get(i).rhsCharactersLength() != 0)
			{
				for(int j=0; j<p.get(i).rhsCharactersLength(); j++)
				{
					boolean isIt = isItInSet(p.get(i).getRHSCharacter(j));
					if(isIt == true)
					{
						counter++;
					}
				}
				boolean anotherBool = isItInSet(p.get(i).getLHS());
				if(counter == p.get(i).rhsCharactersLength() && anotherBool!=true)
				{
					setOfNullables.add(p.get(i).getLHS());
				}
			}
				
		}
		
		System.out.println("NULLS");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for(int i=0; i<setOfNullables.size(); i++)
		{	
			System.out.println(setOfNullables.get(i));
		}
		
		removingLambdas();
	}


	void removingLambdas()
	{
		//System.out.println("TOTAL NUMBER OF LHS = " + p.size());
		ArrayList<ProductionUnit> temp = new ArrayList<>();
		
		for(int i=0; i<p.size(); i++)
		{
			if(p.get(i).rhsCharactersLength() != 0)
			{
				temp.add(new ProductionUnit(p.get(i).getLHS()));
				String rhs = p.get(i).getRHS();
				temp.get(temp.size()-1).setRHS(rhs);
			}
		}
		p = temp;
		totalNumberOfLHS = temp.size();
		System.out.println("TOTAL NUMBER OF LHS = " + p.size());
		display();
	}
	
	void display()
	{
		System.out.println("~~~DISPLAYING~~~~");
		for(int i=0;i<p.size();i++)
		{
			System.out.println(p.get(i).getLHS() + "->" + p.get(i).getRHS());
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	//replaces lambdas with its combinations
	void replacingLambdas()
	{
		int counter;
		for(int i=0; i<totalNumberOfLHS; i++)
		{
			counter = 0;
			for(int j=0; j<p.get(i).rhsCharactersLength(); j++)
			{
				boolean isItInSet = isItInSet(p.get(i).getRHSCharacter(j));
				if(isItInSet == true)
				{
					counter++;
				}
			}
			ArrayList<String> rhsComboArray = new ArrayList<>();
			if(counter >= 0)
			{
				rhsComboArray = differentCombos(p.get(i).getRHSArrayCharacter(), counter);
			}
			p.get(i).setRHSCombos(rhsComboArray);
			//System.out.println("WHAT IS COUNTER? " + counter);
		}
		
		for(int i=0; i<p.size(); i++)
		{
			System.out.println(p.get(i).getLHS() + "->" + p.get(i).getRHSCombos());
		}
	}
	
	ArrayList<String> differentCombos(String[] rhs, int n)
	{
		int max = (int) Math.pow(2, n);
		
		//MASK MADE HERE VVV
		boolean [] booleanMask = new boolean[rhs.length];
		
		for(int i=0; i<rhs.length; i++)
		{
			boolean f = isItInSet(rhs[i]);
			if(f==true)
			{
				booleanMask[i] = true;
			}
			else
			{
				booleanMask[i] = false;
			}
		}
		
		/*for(int i=0; i<booleanMask.length; i++)
		{
			System.out.print(booleanMask[i] + ", ");
		}
			System.out.println();
		
		*/
		
		ArrayList<String> binSequence;
		String combo = "";
		
		ArrayList<String> comboArray = new ArrayList<>();
		for(int i=0; i<max; i++)
		{
			binSequence = convertingToBinary(i, n, booleanMask); //IMPROVED BINARY SEQUENCE 0's and 1's and maybe F
			//System.out.println(binSequence);
			for(int j=0; j<rhs.length; j++)
			{
				//System.out.println("WHAT IS RHS SIZE = " + rhs.length);
				if(binSequence.get(j).equals("1"))
				{
					combo = combo + rhs[j];
				}
				else if(binSequence.get(j).equals("0"));
				{
					combo = combo + "";
				}
			}
			comboArray.add(combo);
			combo = "";
		}
		return comboArray;
		
	}
	
	ArrayList<String> convertingToBinary(int m, int positions, boolean [] b)
	{	
		String s = new String();
		String t = "";
		ArrayList<String> zeroList = new ArrayList<>();
		
		if(m == 0)
		{
			for(int i=0; i<positions; i++)
			{
				s = s + "0";
				
			}
			String [] temp = s.split("");
			
			for(int i=0; i<temp.length; i++)
			{
				zeroList.add(temp[i]);
				//System.out.print(zeroList.get(i));
			}
			//System.out.println();
			
			
			
			/********************/
			
			ArrayList<String> newZeroBinaryRepresentation= new ArrayList<>();
			
			int counter=0;
			for(int i=0; i<b.length; i++)
			{
				if(counter <= zeroList.size() && b[i] == false)
				{
					newZeroBinaryRepresentation.add("1");
				}
				else if(counter <= zeroList.size() && b[i] == true)
				{
					String getIt = zeroList.get(counter);
					newZeroBinaryRepresentation.add(getIt);
					counter++;
				}
			}
			
			
			return newZeroBinaryRepresentation;
		}
		
		String binary = "";
		while (m>0)
		{
			int rem = m%2;
			binary = rem + binary;
			m = m/2;
		}
		//System.out.println("What is positions?" + positions);
		//System.out.println("What is binary.length " + binary.length());
		if(binary.length() < positions)
		{
			int numberOfZeros = positions - binary.length();
			//System.out.println("What is numberofzeros" + numberOfZeros);
			for(int i=0; i<numberOfZeros; i++)
			{
				t = t + "0";
			}
			//System.out.println("What is s?" + t);
		}
		binary = t + binary;
		
		//System.out.println("SIZE= " + binary.length());
		
		//System.out.println("What is binaryy" + binary);
		
	
		
		String [] bin = binary.split("");
		
		
		ArrayList<String> binaryRepresentation = new ArrayList<>();
		
		//System.out.println("binary representation arraylist size " + binaryRepresentation.size());
		
		for(int i=0; i<bin.length; i++)
		{
				binaryRepresentation.add(bin[i]);
				//System.out.print(binaryRepresentation.get(i));
		}
		
		ArrayList<String> newRepresentationOfBits= new ArrayList<>();
		
		int counter=0;
		for(int i=0; i<b.length; i++)
		{
			if(counter <= binaryRepresentation.size() && b[i] == false)
			{
				newRepresentationOfBits.add("1");
			}
			else if(counter <= binaryRepresentation.size() && b[i] == true)
			{
				String getIt = binaryRepresentation.get(counter);
				newRepresentationOfBits.add(getIt);
				counter++;
			}
		}
		
		return newRepresentationOfBits;
		
	}

	//HELPER METHOD FOR nullableSet and replacingLambdas
	boolean isItInSet(String character)
	{
		for(int i=0; i<setOfNullables.size(); i++)
		{
			if(setOfNullables.get(i).equals(character))
			{
				return true;
			}
		}
		return false;
	}
}
