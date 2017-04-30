import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	private static Scanner scanner;				// obiekt typu Scanner pozwalaj¹cy na pobieranie wartoœci wpisywanej przez u¿ytkownika
	private static int option_number;			// zmienna przechowuj¹ca wybór opcji
	private static int function_degree;			// zmienna przechowuj¹ca stopieñ funkcji
	private static float[] coefficients_table;	// tablica przechowuj¹ca wszystkie wspó³czynniki funkcji
	private static float first_part,second_part;// zmienne ograniczaj¹ce przedzia³ w którym ma byæ szukane miejsce zerowe
	private static float eps;					// zmienna oznaczaj¹ca dok³adnoœæ obliczanej wartoœci pierwiastka funkcji
	private static int max;						// zmienna okreœlaj¹ca maksymaln¹ liczbê iteracji w poszukiwaniu miejsca zerowego
	
	//Funkcja menu zwracaj¹ca opcjê wybran¹ przez u¿ytkownika
	public static int menu()
	{
		System.out.println("Program do wyznaczenia miejsc zerowych");
		System.out.println("Aby rozpocz¹æ wybierz 1");
		System.out.println("Aby zakoñczyæ pracê programu wybierz 9");
			
		scanner = new Scanner(System.in);
		try{
			option_number = scanner.nextInt();
		}catch(InputMismatchException exc){
			System.out.println("Podano nie poprawny format opcji!");
		}
		return option_number;
	}
	
	//Funkcja wypisuj¹ca wzór funkcji po wpisaniu wszystkich wspó³czynników
	public static void showFunction()
	{
		coefficients_table = new float[function_degree+1];
		String function = "";
		boolean flag = true;
		for(int i = 0 ; i<=function_degree; i++)
		{
			while(flag)
			{
				System.out.println("Podaj wartoœæ " + (i+1)+ " wspó³czynnika: " );
				scanner = new Scanner (System.in);
					if (scanner.hasNextFloat())
					{
						coefficients_table[i] = scanner.nextFloat();
						flag = false;
						if(i<function_degree)
							function += "("+coefficients_table[i]+") "+"*x^"+(function_degree-i)+" + ";
					}
					else
					{
						System.out.println("Podano nie poprawny format wspó³czynnika");
					}
			}
			flag = true;
		}
		
		System.out.println("Wzór funkcji : " + function + "("+coefficients_table[function_degree]+")" );
	}
	
	//Funkcja wyzaczaj¹ca miejsce zerowe w okreœlonym przedziale
	public static void check(float first, float second, float eps, int max)
	{
		// Sprawdzenie czy pierwsza liczba wprowadzona przez u¿ytkownika jest mniejsza ni¿ druga, 
		// jeœli nie nastêpuje zmiana liczb tak aby zawsze przedzia³ by³ w pastaci: <first, second>
		if(first>second)
		{
			float temp = second;
			second = first;
			first = temp;
		}
		
		float first_value = function_value(first);		// zmienna przyjmuj¹ca wartoœæ funkcji dla pocz¹tkowej wartoœci przedzia³u 
		float second_value = function_value(second);	// zmienna przyjmuj¹ca wartoœæ funkcji dla koñcowej wartoœci przedzia³u

		// Sprawdzenie czy wartoœci funkcji na krañcach przedzia³u s¹ ró¿ne,
		// w przeciwnym wypadku nie ma mo¿liwoœci wyznaczenia miejsca zerowego
		if(first_value!=second_value)
		{
			float temp_value = 0;
			float temp = 0;
			while(max>0)
			{
				// Sprawdzenie czy przedzia³ przeszukiwania jest mniejszy od eps
				// jeœli jest wiêkszy wykonujemy kolejn¹ iteracjê algorytmu
				if(Math.abs(first-second)> eps) 
				{
					temp = first - function_value(first)*(first-second)/(function_value(first)-function_value(second));
					temp_value = function_value(temp);
					
					second = first; second_value = first_value;
					first = temp;  first_value = temp_value;			
				}
				else
				{		
					System.out.println("Miejsce zerowe funkcji w tym przedziale to: x = " + temp);
					break;		
				}
					max = max-1;
			}
			
			if(max==0)
			{
				System.out.println("Nie uda³o siê wyznaczyæ miejsca zerowego, osi¹gniêto maksymaln¹ liczbê iteracji");	
			}
		}
		else
		{
			System.out.println("Nie da siê ustaliæ miejsca zerowego, gdy¿ wartoœci funkcji dla krañców przedzia³u s¹ równe sobie.");
			System.out.println("Nale¿y zmieniæ jedn¹ z wartoœci okreœlaj¹cych przedzia³ ");
			return;
		}
	}
	//Funkcja zwracaj¹ca wartoœæ funkcji dla konkretengo parametru x
	static public float function_value(float x_parameter)
	{
		float result = 0;
		Float float_degree = new Float(function_degree);
		for(int j = 0 ; j<=function_degree; j++)
		{
			result += coefficients_table[j]*Math.pow(x_parameter,float_degree-j);
		}
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		while(true)
		{
			menu();
			switch(option_number)
			{
				case 1:
				{
					System.out.println("Podaj stopieñ funkcji wielomianowej oraz wspó³czynniki funkcji f(x)");
					System.out.println("(Pamiêtaj aby podaæ liczbê naturaln¹ wiêksz¹ od 0)");
					function_degree=0;
					while(function_degree<=0)
					{
						scanner = new Scanner(System.in);
						if(scanner.hasNextInt())
						{
							function_degree = scanner.nextInt();
							if(function_degree<=0)
							{
								System.out.println("Podana liczba nie jest dodatnia, pamiêtaj aby podaæ liczbê naturaln¹!");
							}
						}
						else
						{
							System.out.println("Podano nie poprawny format liczby, pamiêtaj aby podaæ liczbê naturaln¹!");
						}
					}
						
					showFunction();
					
					System.out.println("Podaj przedzia³ w którym chcesz znaleŸæ miejsce zerowe funkcji");
					
					
					boolean flag = true; 		// flaga pozwalaj¹ca okreœliæ kiedy u¿ytkownik wpisa³ prawid³ow¹ pierwsz¹ liczbê
					
					while(flag)
					{
						System.out.println("Pierwsza liczba: ");
						scanner = new Scanner(System.in);
						if(scanner.hasNextFloat())
						{
							first_part = scanner.nextFloat();
							flag = false;
						}
						else
						{
							System.out.println("Podano nie poprawny format liczby, podaj liczbê jeszcze raz");
						}
					}
					
					flag = true;
					
					while(flag)
					{
						System.out.println("Druga liczba: ");
						scanner = new Scanner(System.in);
						if(scanner.hasNextFloat())
						{
							second_part = scanner.nextFloat();
							flag = false;
						}
						else
						{
							System.out.println("Podano nie poprawny format liczby, podaj liczbê jeszcze raz");
						}
					}
					
					System.out.println("Podaj dok³adnoœæ obliczanej wartoœci pierwiastka funkcji: ");
					
					flag = true;
					
					while(flag)
					{
						System.out.println("Epsilon : ");
						scanner = new Scanner(System.in);
						if(scanner.hasNextFloat())
						{
							eps = scanner.nextFloat();
							flag = false;
						}
						else
						{
							System.out.println("Podano nie poprawny format liczby, podaj liczbê jeszcze raz");
						}
					}
										
					System.out.println("Podaj maksymaln¹ liczbê iteracji: ");
					max = 0;
					while(max<=0)
					{
						scanner = new Scanner(System.in);
						if(scanner.hasNextInt())
						{
							max = scanner.nextInt();
							if(max<=0)
							{
								System.out.println("Podana liczba nie jest dodatnia, pamiêtaj aby podaæ liczbê naturaln¹!");
							}
						}
						else
						{
							System.out.println("Podano nie poprawny format liczby, pamiêtaj aby podaæ liczbê naturaln¹!");
						}
					}

					check(first_part, second_part, eps, max);
					break;
				}
				case 9:
				{
					System.out.println("Zakoñczono pracê programu");
					System.exit(0);
					break;
				}
				default:
				{
					System.out.println("Podana opcja nie istnieje, podaj liczbê jeszcze raz");
				}
			}
		}
	}
}
