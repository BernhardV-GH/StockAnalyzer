package stockanalyzer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import download.Downloader;
import download.ParallelDownloader;
import download.SequentialDownloader;
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

	private void getDataForDownload1() {
		long time1, time2;
		Downloader sequentialDownloader = new SequentialDownloader();
		Downloader parallelDownloader = new ParallelDownloader();
		List<String> tickers = Arrays.asList("MSFT","NFLX","NOK","GOOG","GME","AAPL","BTC-USD","DOGE-USD","ETH-USD",
				"OMV.VI","EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
				"WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI");

		try{
			time1 = System.currentTimeMillis();
			ctrl.downloadTickers(tickers,sequentialDownloader);
			time2 = System.currentTimeMillis();
			System.out.printf("Sequential Download Timer: %dms\n",time2-time1);

			time1 = System.currentTimeMillis();
			ctrl.downloadTickers(tickers, parallelDownloader);
			time2 = System.currentTimeMillis();
			System.out.printf("Parallel Download Timer: %dms\n",time2-time1);
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
		menu.insert("e","Download Tickerlist", this::getDataForDownload1);

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
