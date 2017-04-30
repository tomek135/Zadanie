import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	private static Scanner scanner;				// obiekt typu Scanner pozwalaj�cy na pobieranie warto�ci wpisywanej przez u�ytkownika
	private static int option_number;			// zmienna przechowuj�ca wyb�r opcji
	private static int function_degree;			// zmienna przechowuj�ca stopie� funkcji
	private static float[] coefficients_table;	// tablica przechowuj�ca wszystkie wsp�czynniki funkcji
	private static float first_part,second_part;// zmienne ograniczaj�ce przedzia� w kt�rym ma by� szukane miejsce zerowe
	private static float eps;					// zmienna oznaczaj�ca dok�adno�� obliczanej warto�ci pierwiastka funkcji
	private static int max;						// zmienna okre�laj�ca maksymaln� liczb� iteracji w poszukiwaniu miejsca zerowego
	
	//Funkcja menu zwracaj�ca opcj� wybran� przez u�ytkownika
	public static int menu()
	{
		System.out.println("Program do wyznaczenia miejsc zerowych");
		System.out.println("Aby rozpocz�� wybierz 1");
		System.out.println("Aby zako�czy� prac� programu wybierz 9");
			
		scanner = new Scanner(System.in);
		try{
			option_number = scanner.nextInt();
		}catch(InputMismatchException exc){
			System.out.println("Podano nie poprawny format opcji!");
		}
		return option_number;
	}
	
	//Funkcja wypisuj�ca wz�r funkcji po wpisaniu wszystkich wsp�czynnik�w
	public static void showFunction()
	{
		coefficients_table = new float[function_degree+1];
		String function = "";
		boolean flag = true;
		for(int i = 0 ; i<=function_degree; i++)
		{
			while(flag)
			{
				System.out.println("Podaj warto�� " + (i+1)+ " wsp�czynnika: " );
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
						System.out.println("Podano nie poprawny format wsp�czynnika");
					}
			}
			flag = true;
		}
		
		System.out.println("Wz�r funkcji : " + function + "("+coefficients_table[function_degree]+")" );
	}
	
	//Funkcja wyzaczaj�ca miejsce zerowe w okre�lonym przedziale
	public static void check(float first, float second, float eps, int max)
	{
		// Sprawdzenie czy pierwsza liczba wprowadzona przez u�ytkownika jest mniejsza ni� druga, 
		// je�li nie nast�puje zmiana liczb tak aby zawsze przedzia� by� w pastaci: <first, second>
		if(first>second)
		{
			float temp = second;
			second = first;
			first = temp;
		}
		
		float first_value = function_value(first);		// zmienna przyjmuj�ca warto�� funkcji dla pocz�tkowej warto�ci przedzia�u 
		float second_value = function_value(second);	// zmienna przyjmuj�ca warto�� funkcji dla ko�cowej warto�ci przedzia�u

		// Sprawdzenie czy warto�ci funkcji na kra�cach przedzia�u s� r�ne,
		// w przeciwnym wypadku nie ma mo�liwo�ci wyznaczenia miejsca zerowego
		if(first_value!=second_value)
		{
			float temp_value = 0;
			float temp = 0;
			while(max>0)
			{
				// Sprawdzenie czy przedzia� przeszukiwania jest mniejszy od eps
				// je�li jest wi�kszy wykonujemy kolejn� iteracj� algorytmu
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
				System.out.println("Nie uda�o si� wyznaczy� miejsca zerowego, osi�gni�to maksymaln� liczb� iteracji");	
			}
		}
		else
		{
			System.out.println("Nie da si� ustali� miejsca zerowego, gdy� warto�ci funkcji dla kra�c�w przedzia�u s� r�wne sobie.");
			System.out.println("Nale�y zmieni� jedn� z warto�ci okre�laj�cych przedzia� ");
			return;
		}
	}
	//Funkcja zwracaj�ca warto�� funkcji dla konkretengo parametru x
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
					System.out.println("Podaj stopie� funkcji wielomianowej oraz wsp�czynniki funkcji f(x)");
					System.out.println("(Pami�taj aby poda� liczb� naturaln� wi�ksz� od 0)");
					function_degree=0;
					while(function_degree<=0)
					{
						scanner = new Scanner(System.in);
						if(scanner.hasNextInt())
						{
							function_degree = scanner.nextInt();
							if(function_degree<=0)
							{
								System.out.println("Podana liczba nie jest dodatnia, pami�taj aby poda� liczb� naturaln�!");
							}
						}
						else
						{
							System.out.println("Podano nie poprawny format liczby, pami�taj aby poda� liczb� naturaln�!");
						}
					}
						
					showFunction();
					
					System.out.println("Podaj przedzia� w kt�rym chcesz znale�� miejsce zerowe funkcji");
					
					
					boolean flag = true; 		// flaga pozwalaj�ca okre�li� kiedy u�ytkownik wpisa� prawid�ow� pierwsz� liczb�
					
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
							System.out.println("Podano nie poprawny format liczby, podaj liczb� jeszcze raz");
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
							System.out.println("Podano nie poprawny format liczby, podaj liczb� jeszcze raz");
						}
					}
					
					System.out.println("Podaj dok�adno�� obliczanej warto�ci pierwiastka funkcji: ");
					
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
							System.out.println("Podano nie poprawny format liczby, podaj liczb� jeszcze raz");
						}
					}
										
					System.out.println("Podaj maksymaln� liczb� iteracji: ");
					max = 0;
					while(max<=0)
					{
						scanner = new Scanner(System.in);
						if(scanner.hasNextInt())
						{
							max = scanner.nextInt();
							if(max<=0)
							{
								System.out.println("Podana liczba nie jest dodatnia, pami�taj aby poda� liczb� naturaln�!");
							}
						}
						else
						{
							System.out.println("Podano nie poprawny format liczby, pami�taj aby poda� liczb� naturaln�!");
						}
					}

					check(first_part, second_part, eps, max);
					break;
				}
				case 9:
				{
					System.out.println("Zako�czono prac� programu");
					System.exit(0);
					break;
				}
				default:
				{
					System.out.println("Podana opcja nie istnieje, podaj liczb� jeszcze raz");
				}
			}
		}
	}
}
