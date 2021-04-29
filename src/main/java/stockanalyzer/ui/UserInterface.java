package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import stockanalyzer.YahooDataRetrievalException;
import stockanalyzer.ctrl.Controller;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		try{
			System.out.println(ctrl.process("MSFT"));
		}
		catch(YahooDataRetrievalException ydre){
			ydre.printStackTrace();
			System.out.println(ydre.getMessage());
		}
	}

	public void getDataFromCtrl2(){
		try{
			System.out.println(ctrl.process("NFLX"));
		}
		catch(YahooDataRetrievalException ydre){
			ydre.printStackTrace();
			System.out.println(ydre.getMessage());
		}
	}

	public void getDataFromCtrl3(){
		try{
			System.out.println(ctrl.process("NOK"));
		}
		catch(YahooDataRetrievalException ydre){
			ydre.printStackTrace();
			System.out.println(ydre.getMessage());
		}
	}


	public void getDataForCustomInput() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Stock short name: ");
		String ticker = scan.nextLine();

		try{
			System.out.println(ctrl.process(ticker));
		}
		catch(YahooDataRetrievalException ydre){
			ydre.printStackTrace();
			System.out.println(ydre.getMessage());
		}
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Microsoft Corporation", this::getDataFromCtrl1);
		menu.insert("b", "Netflix, Inc.", this::getDataFromCtrl2);
		menu.insert("c", "Nokia Corporation", this::getDataFromCtrl3);
		menu.insert("d", "User Choice",this::getDataForCustomInput);

		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


	protected String readLine()
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
