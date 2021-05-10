package stockanalyzer.ctrl;

import download.Downloader;
import stockanalyzer.YahooDataRetrievalException;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Controller{
	public String process(String ticker) throws YahooDataRetrievalException {
		System.out.println("Start process");
		return analyseData(ticker);
		// highestLast52Days(ticker); // implemented with original YahooResponse class
	}

	public String analyseData(String ticker) throws YahooDataRetrievalException{
		Stock stock = null;
		try {
			stock = yahoofinance.YahooFinance.get(ticker);
			double highest = stock.getHistory().stream()
					.mapToDouble(quote -> quote.getClose().doubleValue())
					.max()
					.orElse(0);
			double average = stock.getHistory().stream()
					.mapToDouble(quote -> quote.getClose().doubleValue())
					.average()
					.orElse(0);
			long count = stock.getHistory().stream()
					.count();

			String str = "\n" + stock.getName() + "\n" +
					"Highest Quote: " + highest + "\n" +
					"Average Quote: " + average + "\n" +
					"Number of records: " + count + "\n";
			return str;

		} catch (IOException e) {
			e.printStackTrace();
			throw new YahooDataRetrievalException("Something went wrong with data retrieval.. Oopsie..");
		}
	}


	public Object getData(String searchString) throws YahooDataRetrievalException {
		YahooFinance yahoofinance = new YahooFinance();
		YahooResponse yahooresponse = yahoofinance.getCurrentData(Arrays.asList(searchString.split(",")));
		return yahooresponse.getQuoteResponse();
	}

	public void highestLast52Days(String ticker) throws YahooDataRetrievalException{
		QuoteResponse data = (QuoteResponse) getData(ticker);
		data.getResult().stream()
				.forEach(high -> System.out.println(high.getLongName() + ": " + high.getFiftyTwoWeekHigh()));
	}

	public void downloadTickers(List<String> ticker, Downloader downloader) throws YahooDataRetrievalException {
		downloader.process(ticker);
	}
}
